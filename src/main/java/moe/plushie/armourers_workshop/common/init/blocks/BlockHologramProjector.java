package moe.plushie.armourers_workshop.common.init.blocks;

import java.util.Random;

import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityHologramProjector;
import moe.plushie.armourers_workshop.utils.BlockUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockHologramProjector extends AbstractModBlockContainer {

    public static final PropertyDirection STATE_FACING = PropertyDirection.create("facing");
    
    public BlockHologramProjector() {
        super(LibBlockNames.HOLOGRAM_PROJECTOR);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STATE_FACING, Direction.NORTH));
        setSortPriority(120);
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { STATE_FACING });
    }
    
    @Override
    public BlockState getStateFromMeta(int meta) {
        Direction facing = Direction.byIndex(meta);
        return this.getDefaultState().withProperty(STATE_FACING, facing);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(STATE_FACING).ordinal();
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        BlockUtils.dropInventoryBlocks(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public boolean isOpaqueCube(BlockState state) {
        return true;
    }
    
    @Override
    public boolean isBlockNormalCube(BlockState state) {
        return true;
    }
    
    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
        int dir = BlockUtils.determineOrientation(pos, placer);
        Direction enumfacing = Direction.byIndex(dir);
        return getDefaultState().withProperty(STATE_FACING, enumfacing);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        updatePoweredState(worldIn, pos);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        openGui(playerIn, EnumGuiId.HOLOGRAM_PROJECTOR, worldIn, pos, state, facing);
        return true;
    }
    
    @Override
    public void onNeighborChange(IBlockReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        updatePoweredState(world, pos);
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        updatePoweredState(worldIn, pos);
    }
    
    private void updatePoweredState(IBlockReader world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof TileEntityHologramProjector) {
            ((TileEntityHologramProjector)tileEntity).updatePoweredState();
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityHologramProjector();
    }
}
