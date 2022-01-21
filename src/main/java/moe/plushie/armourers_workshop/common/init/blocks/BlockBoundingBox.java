package moe.plushie.armourers_workshop.common.init.blocks;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import moe.plushie.armourers_workshop.api.common.painting.IPaintType;
import moe.plushie.armourers_workshop.api.common.painting.IPantableBlock;
import moe.plushie.armourers_workshop.api.common.skin.cubes.ICubeColour;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinPartTypeTextured;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.client.texture.PlayerTexture;
import moe.plushie.armourers_workshop.common.TextureHelper;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.painting.PaintTypeRegistry;
import moe.plushie.armourers_workshop.common.permission.Permission;
import moe.plushie.armourers_workshop.common.skin.SkinTextureHelper;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityBoundingBox;
import moe.plushie.armourers_workshop.proxies.ClientProxy;
import moe.plushie.armourers_workshop.utils.BitwiseUtils;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockBoundingBox extends AbstractModBlockContainer implements IPantableBlock {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    
    protected BlockBoundingBox() {
        super(LibBlockNames.BOUNDING_BOX, Material.CIRCUITS, SoundType.CLOTH, false);
        setBlockUnbreakable();
        setResistance(6000000.0F);
        setLightOpacity(0);
    }
    
    @Override
    public void getSubBlocks(ItemGroup itemIn, NonNullList<ItemStack> items) {
    }
    
    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockToAir(pos);
    }
    
    
    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest) {
        if (world.isRemote) {
            return true;
        }
        
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof TileEntityBoundingBox) {
            if (((TileEntityBoundingBox)tileEntity).isParentValid()) {
                tileEntity.markDirty();
                //world.markBlockForUpdate(pos);
                return false;
            } else {
                world.setBlockToAir(pos);
                return true;
            }
        }
        
        world.setBlockToAir(pos);
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(BlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return true;
    }
    
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
    
    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }
    
    @Override
    public boolean isFullBlock(BlockState state) {
        return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }
    
    @Override
    public String getTranslationKey() {
        return getModdedUnlocalizedName(super.getTranslationKey());
    }

    protected String getModdedUnlocalizedName(String unlocalizedName) {
        String name = unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        return "tile." + LibModInfo.ID.toLowerCase() + ":" + name;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityBoundingBox();
    }
    
    @Override
    public boolean setColour(IBlockAccess world, BlockPos pos, int colour, Direction facing) {
        if (world.getBlockState(pos.offset(facing)).getBlock() == this) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityBoundingBox) {
            TileEntityArmourer parent = ((TileEntityBoundingBox)te).getParent();
            if (((TileEntityBoundingBox)te).getSkinPart() instanceof ISkinPartTypeTextured) {
                if (parent != null) {
                    ISkinType skinType = parent.getSkinType();
                    Point texturePoint = SkinTextureHelper.getTextureLocationFromWorldBlock((TileEntityBoundingBox)te, facing);
                    int oldColour = parent.getPaintData(texturePoint.x, texturePoint.y);
                    int paintType = BitwiseUtils.getUByteFromInt(oldColour, 0);
                    int newColour = BitwiseUtils.setUByteToInt(colour, 0, paintType);
                    parent.updatePaintData(texturePoint.x, texturePoint.y, newColour);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void registerItemBlock(IForgeRegistry<Item> registry) {}
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {}
    
    @Override
    public boolean setColour(IBlockAccess world, BlockPos pos, byte[] rgb, Direction facing) {
        int colour = new Color(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF).getRGB();
        return setColour(world, pos, colour, facing);
    }

    @Override
    public int getColour(IBlockAccess world, BlockPos pos, Direction facing) {
        if (world.getBlockState(pos.offset(facing)).getBlock() == this) {
            return 0x00FFFFFF;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityBoundingBox) {
            TileEntityArmourer parent = ((TileEntityBoundingBox)te).getParent();
            if (parent != null) {
                if (((TileEntityBoundingBox)te).getSkinPart() instanceof ISkinPartTypeTextured) {
                    Point texturePoint = SkinTextureHelper.getTextureLocationFromWorldBlock((TileEntityBoundingBox)te, facing);
                    int colour = parent.getPaintData(texturePoint.x, texturePoint.y);
                    IPaintType paintType = PaintTypeRegistry.getInstance().getPaintTypeFromColour(colour);
                    if (paintType != PaintTypeRegistry.PAINT_TYPE_NONE) {
                        return colour;
                    } else {
                        if (te.getWorld().isRemote) {
                            return getColourRemote(world, pos, facing, parent, texturePoint, colour);
                        }
                    }
                }
            }
        }
        return 0x00FFFFFF;
    }
    
    @SideOnly(Side.CLIENT)
    private int getColourRemote(IBlockAccess world, BlockPos pos, Direction facing, TileEntityArmourer parent, Point texturePoint, int colour) {
        PlayerTexture playerTexture = ClientProxy.playerTextureDownloader.getPlayerTexture(parent.getTexture());
        BufferedImage playerSkin = TextureHelper.getBufferedImageSkin(playerTexture.getResourceLocation());
        if (playerSkin != null) {
            colour = playerSkin.getRGB(texturePoint.x, texturePoint.y);
            return colour;
        }
        return 0x00FFFFFF;
    }
    
    @Override
    public boolean isRemoteOnly(IBlockAccess world, BlockPos pos, Direction facing) {
        if (world.getBlockState(pos.offset(facing)).getBlock() == this) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityBoundingBox) {
            TileEntityArmourer parent = ((TileEntityBoundingBox)te).getParent();
            if (parent != null) {
                if (((TileEntityBoundingBox)te).getSkinPart() instanceof ISkinPartTypeTextured) {
                    Point texturePoint = SkinTextureHelper.getTextureLocationFromWorldBlock((TileEntityBoundingBox)te, facing);
                    int colour = parent.getPaintData(texturePoint.x, texturePoint.y);
                    int paintType = BitwiseUtils.getUByteFromInt(colour, 0);
                    return paintType == 0;
                }
            }
        }
        return false;
    }
    
    @Override
    public void setPaintType(IBlockAccess world, BlockPos pos, IPaintType paintType, Direction facing) {
        if (world.getBlockState(pos.offset(facing)).getBlock() == this) {
            return;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityBoundingBox) {
            TileEntityArmourer parent = ((TileEntityBoundingBox)te).getParent();
            if (((TileEntityBoundingBox)te).getSkinPart() instanceof ISkinPartTypeTextured) {
                if (parent != null) {
                    ISkinType skinType = parent.getSkinType();
                    Point texturePoint = SkinTextureHelper.getTextureLocationFromWorldBlock((TileEntityBoundingBox)te, facing);
                    int oldColour = parent.getPaintData(texturePoint.x, texturePoint.y);
                    int newColour = PaintTypeRegistry.getInstance().setPaintTypeOnColour(paintType, oldColour);
                    parent.updatePaintData(texturePoint.x, texturePoint.y, newColour);
                }
            }
        }
    }
    
    @Override
    public IPaintType getPaintType(IBlockAccess world, BlockPos pos, Direction facing) {
        if (world.getBlockState(pos.offset(facing)).getBlock() == this) {
            return PaintTypeRegistry.PAINT_TYPE_NORMAL;
        }
        
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityBoundingBox) {
            TileEntityArmourer parent = ((TileEntityBoundingBox)te).getParent();
            if (parent != null) {
                if (((TileEntityBoundingBox)te).getSkinPart() instanceof ISkinPartTypeTextured) {
                    Point texturePoint = SkinTextureHelper.getTextureLocationFromWorldBlock((TileEntityBoundingBox)te, facing);
                    int colour = parent.getPaintData(texturePoint.x, texturePoint.y);
                    return PaintTypeRegistry.getInstance().getPaintTypeFromColour(colour);
                }
            }
        }
        return PaintTypeRegistry.PAINT_TYPE_NORMAL;
    }

    @Override
    public ICubeColour getColour(IBlockAccess world, BlockPos pos) {
        return null;
    }
    
    @Override
    public void getPermissions(ArrayList<Permission> permissions) {
    }
}
