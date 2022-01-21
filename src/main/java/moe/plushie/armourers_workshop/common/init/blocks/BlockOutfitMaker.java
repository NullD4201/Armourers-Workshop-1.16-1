package moe.plushie.armourers_workshop.common.init.blocks;

import moe.plushie.armourers_workshop.client.config.ConfigHandlerClient;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityOutfitMaker;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOutfitMaker extends AbstractModBlockContainer {

    public static final PropertyDirection STATE_FACING = HorizontalBlock.FACING;
    
    public BlockOutfitMaker() {
        super(LibBlockNames.OUTFIT_MAKER);
        setDefaultState(this.blockState.getBaseState().withProperty(STATE_FACING, Direction.NORTH));
        setSortPriority(150);
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {STATE_FACING});
    }
    
    @Override
    public BlockState getStateFromMeta(int meta) {
        boolean northSouthBit = getBitBool(meta, 0);
        boolean posNegBit = getBitBool(meta, 1);
        Direction facing = Direction.EAST;
        if (northSouthBit) {
            if (posNegBit) { facing = Direction.SOUTH; } else { facing = Direction.NORTH; }
        } else {
            if (posNegBit) { facing = Direction.EAST; } else { facing = Direction.WEST; }
        }
        return this.getDefaultState().withProperty(STATE_FACING, facing);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        Direction facing = state.getValue(STATE_FACING);
        int meta = 0;
        if (facing == Direction.NORTH | facing == Direction.SOUTH) {
            meta = setBit(meta, 0, true);
        }
        if (facing == Direction.EAST | facing == Direction.SOUTH) {
            meta = setBit(meta, 1, true);
        }
        return meta;
    }
    
    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
        Direction enumfacing = placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(STATE_FACING, enumfacing);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.canPlayerEdit(pos, facing, playerIn.getHeldItem(hand))) {
            return false;
        }
        openGui(playerIn, EnumGuiId.OUTFIT_MAKER, worldIn, pos, state, facing);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOutfitMaker();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        if (!ConfigHandlerClient.useClassicBlockModels) {
            return BlockRenderLayer.CUTOUT_MIPPED;
        } else {
            return BlockRenderLayer.SOLID;
        }
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }
    
    @Override
    public boolean isBlockNormalCube(BlockState state) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        if (!ConfigHandlerClient.useClassicBlockModels) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey()), "normal"));
        } else {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey() + "-classic"), "normal"));
        }
    }
}
