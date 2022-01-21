package moe.plushie.armourers_workshop.common.init.items;

import moe.plushie.armourers_workshop.api.common.painting.IPantableBlock;
import moe.plushie.armourers_workshop.common.init.blocks.BlockBoundingBox;
import moe.plushie.armourers_workshop.common.init.blocks.ModBlocks;
import moe.plushie.armourers_workshop.common.init.sounds.ModSounds;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.common.painting.PaintTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSoap extends AbstractModItem {

    public ItemSoap() {
        super(LibItemNames.SOAP);
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() instanceof IPantableBlock) {
            IPantableBlock paintableBlock = (IPantableBlock) state.getBlock();
            // TODO This may make block sides transparent.
        }
        if (state.getBlock() == ModBlocks.BOUNDING_BOX) {
            BlockBoundingBox bb = (BlockBoundingBox) state.getBlock();
            if (!worldIn.isRemote) {
                bb.setColour(worldIn, pos, 0x00FFFFFF, facing);
                bb.setPaintType(worldIn, pos, PaintTypeRegistry.PAINT_TYPE_NONE, facing);
                worldIn.playSound(null, pos, ModSounds.PAINT, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.2F + 0.9F);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
