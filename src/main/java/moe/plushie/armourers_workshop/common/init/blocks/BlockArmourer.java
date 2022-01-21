package moe.plushie.armourers_workshop.common.init.blocks;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.client.texture.PlayerTexture;
import moe.plushie.armourers_workshop.common.data.type.TextureType;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityArmourer;
import moe.plushie.armourers_workshop.utils.BlockUtils;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockArmourer extends AbstractModBlockContainer {

    public BlockArmourer() {
        super(LibBlockNames.ARMOURER);
        setCreativeTab(ArmourersWorkshop.TAB_PAINTING_TOOLS);
        setSortPriority(200);
    }
    
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (placer instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)placer;
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileEntityArmourer) {
                Direction direction = BlockUtils.determineDirectionSide(placer).getOpposite();
                ((TileEntityArmourer)te).setDirection(Direction.NORTH);
                if (!worldIn.isRemote) {
                    ((TileEntityArmourer)te).setTexture(new PlayerTexture(player.getName(), TextureType.USER));
                    ((TileEntityArmourer)te).onPlaced();
                }
            }
        }
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null & te instanceof TileEntityArmourer) {
            ((TileEntityArmourer)te).preRemove();
        }
        BlockUtils.dropInventoryBlocks(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null & te instanceof TileEntityArmourer) {
            ((TileEntityArmourer)te).preRemove();
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.canPlayerEdit(pos, facing, playerIn.getHeldItem(hand))) {
            return false;
        }
        openGui(playerIn, EnumGuiId.ARMOURER, worldIn, pos, state, facing);
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        return new TileEntityArmourer();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
