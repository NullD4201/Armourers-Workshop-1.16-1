package moe.plushie.armourers_workshop.common.init.blocks;

import moe.plushie.armourers_workshop.client.config.ConfigHandlerClient;
import moe.plushie.armourers_workshop.common.lib.LibBlockNames;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibModInfo;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityGlobalSkinLibrary;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
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

public class BlockGlobalSkinLibrary extends AbstractModBlockContainer {

    public static final PropertyDirection STATE_FACING = HorizontalBlock.FACING;
    
    public BlockGlobalSkinLibrary() {
        super(LibBlockNames.GLOBAL_SKIN_LIBRARY);
        setDefaultState(this.blockState.getBaseState().withProperty(STATE_FACING, Direction.NORTH));
        setSortPriority(197);
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {STATE_FACING});
    }
    
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        openGui(playerIn, EnumGuiId.GLOBAL_SKIN_LIBRARY, worldIn, pos, state, facing);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityGlobalSkinLibrary();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        if (!ConfigHandlerClient.useClassicBlockModels) {
            super.registerModels();
        } else {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ResourceLocation(LibModInfo.ID, getTranslationKey() + "-classic"), "normal"));
            ModelLoader.setCustomStateMapper(this, new StateMap.Builder().withSuffix("-classic").ignore(STATE_FACING).build());
        }
    }
}
