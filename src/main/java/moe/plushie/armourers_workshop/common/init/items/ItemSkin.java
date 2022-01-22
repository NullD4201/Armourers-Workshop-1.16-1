package moe.plushie.armourers_workshop.common.init.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import org.apache.commons.lang3.StringUtils;

import moe.plushie.armourers_workshop.api.common.capability.IEntitySkinCapability;
import moe.plushie.armourers_workshop.api.common.capability.IPlayerWardrobeCap;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinDescriptor;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinIdentifier;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinType;
import moe.plushie.armourers_workshop.client.config.ConfigHandlerClient;
import moe.plushie.armourers_workshop.client.settings.Keybindings;
import moe.plushie.armourers_workshop.client.skin.cache.ClientSkinCache;
import moe.plushie.armourers_workshop.common.capability.entityskin.EntitySkinCapability;
import moe.plushie.armourers_workshop.common.capability.wardrobe.player.PlayerWardrobeCap;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import moe.plushie.armourers_workshop.common.skin.cubes.CubeRegistry;
import moe.plushie.armourers_workshop.common.skin.data.Skin;
import moe.plushie.armourers_workshop.common.skin.data.SkinDescriptor;
import moe.plushie.armourers_workshop.common.skin.data.SkinProperties;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import moe.plushie.armourers_workshop.common.world.BlockSkinPlacementHelper;
import moe.plushie.armourers_workshop.utils.SkinNBTHelper;
import moe.plushie.armourers_workshop.utils.SkinUtils;
import moe.plushie.armourers_workshop.utils.TranslateUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSkin extends AbstractModItem {

    public ItemSkin() {
        super(LibItemNames.SKIN, false);
        DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, dispenserBehavior);
    }

    public ISkinType getSkinType(ItemStack stack) {
        return SkinNBTHelper.getSkinTypeFromStack(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Skin skin = SkinUtils.getSkinDetectSide(stack, true, false);
        if (skin != null) {
            if (!skin.getCustomName().trim().isEmpty()) {
                return skin.getCustomName();
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @LogicalSidedProvider(LogicalSide.CLIENT)
    public static void addTooltipToSkinItem(ItemStack stack, PlayerEntity player, List tooltip, ITooltipFlag flagIn) {
        String cRed = TextFormatting.RED.toString();

        boolean isEquipmentSkin = stack.getItem() == ModItems.SKIN;
        boolean isEquipmentContainer = stack.getItem() instanceof AbstractModItemArmour;

        if (SkinNBTHelper.stackHasSkinData(stack)) {
            SkinDescriptor skinData = SkinNBTHelper.getSkinDescriptorFromStack(stack);
            ISkinIdentifier identifier = skinData.getIdentifier();

            if (!isEquipmentSkin & ConfigHandlerClient.tooltipHasSkin) {
                tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.hasSkin"));
            }

            if (ClientSkinCache.INSTANCE.isSkinInCache(skinData)) {
                Skin data = ClientSkinCache.INSTANCE.getSkin(skinData);
                if (stack.getItem() != ModItems.SKIN & !data.getCustomName().trim().isEmpty() & ConfigHandlerClient.tooltipSkinName) {
                    tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinName", data.getCustomName()));
                }
                if (!data.getAuthorName().trim().isEmpty() & ConfigHandlerClient.tooltipSkinAuthor) {
                    tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinAuthor", data.getAuthorName()));
                }
                if (skinData.getIdentifier().getSkinType() != null & ConfigHandlerClient.tooltipSkinType) {
                    String localSkinName = SkinTypeRegistry.INSTANCE.getLocalizedSkinTypeName(skinData.getIdentifier().getSkinType());
                    tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinType", localSkinName));
                }
                if (ConfigHandlerClient.tooltipDebug) {
                    // Debug info.
                    if (Screen.isShiftKeyDown()) {
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinIdentifier"));
                        if (identifier.hasLocalId()) {
                            tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinId", identifier.getSkinLocalId()));
                        }
                        if (identifier.hasLibraryFile()) {
                            tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinLibraryFile", identifier.getSkinLibraryFile().getFullName()));
                        }
                        if (identifier.hasGlobalId()) {
                            tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinGlobalId", identifier.getSkinGlobalId()));
                        }
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinTotalCubes", data.getTotalCubes()));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinNumCubes", data.getTotalOfCubeType(CubeRegistry.INSTANCE.getCubeFormId((byte) 0))));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinNumCubesGlowing", data.getTotalOfCubeType(CubeRegistry.INSTANCE.getCubeFormId((byte) 1))));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinNumCubesGlass", data.getTotalOfCubeType(CubeRegistry.INSTANCE.getCubeFormId((byte) 2))));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinNumCubesGlassGlowing", data.getTotalOfCubeType(CubeRegistry.INSTANCE.getCubeFormId((byte) 3))));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinPaintData", data.hasPaintData()));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinMarkerCount", data.getMarkerCount()));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinDyeCount", skinData.getSkinDye().getNumberOfDyes()));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinProperties"));
                        ArrayList<String> props = data.getProperties().getPropertiesList();
                        for (int i = 0; i < props.size(); i++) {
                            tooltip.add("  " + props.get(i));
                        }
                    } else {
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinHoldShiftForInfo"));
                    }
                }
                String flavour = SkinProperties.PROP_ALL_FLAVOUR_TEXT.getValue(data.getProperties()).trim();
                if (!StringUtils.isEmpty(flavour) & ConfigHandlerClient.tooltipFlavour) {
                    tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.flavour", flavour));
                }

                // Skin ID error.
                if (identifier.hasLocalId()) {
                    if (identifier.getSkinLocalId() != data.lightHash()) {
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinIdError1"));
                        tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinIdError2"));
                        // tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinIdError3",
                        // data.requestId, data.lightHash()));
                    }
                }
            } else {
                // Skin not in cache.
                tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skindownloading", identifier.toString()));
                if (identifier.hasLocalId()) {
                    tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinId", identifier.getSkinLocalId()));
                }
                if (identifier.hasLibraryFile()) {
                    tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinLibraryFile", identifier.getSkinLibraryFile().getFullName()));
                }
                if (identifier.hasGlobalId()) {
                    tooltip.add("  " + TranslateUtils.translate("item.armourers_workshop:rollover.skinGlobalId", identifier.getSkinGlobalId()));
                }
            }
            if (isEquipmentSkin & ConfigHandlerClient.tooltipOpenWardrobe) {
                String keyName = Keybindings.OPEN_WARDROBE.getDisplayName();
                tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinOpenWardrobe", keyName));
            }
        } else {
            // No skin identifier on stack.
            if (isEquipmentSkin) {
                tooltip.add(TranslateUtils.translate("item.armourers_workshop:rollover.skinInvalidItem"));
            }
        }
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        BlockState state = worldIn.getBlockState(pos);
        ISkinDescriptor descriptor = SkinNBTHelper.getSkinDescriptorFromStack(stack);
        if (descriptor != null && descriptor.getIdentifier().getSkinType() == SkinTypeRegistry.skinBlock) {
            Skin skin = SkinUtils.getSkinDetectSide(descriptor, false, true);
            if (skin != null) {
                BlockState replaceBlock = worldIn.getBlockState(pos.offset(facing));
                if (replaceBlock.getBlock().isReplaceable(worldIn, pos.offset(facing))) {
                    BlockSkinPlacementHelper.placeSkinAtLocation(worldIn, player, facing, stack, pos.offset(facing), skin, descriptor);
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.FAIL;
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        IPlayerWardrobeCap wardrobeCap = PlayerWardrobeCap.get(playerIn);
        IEntitySkinCapability skinCapability = EntitySkinCapability.get(playerIn);
        ISkinDescriptor descriptor = SkinNBTHelper.getSkinDescriptorFromStack(itemStack);
        if (wardrobeCap == null | skinCapability == null | descriptor == null) {
            return new ActionResult<ItemStack>(ActionResultType.FAIL, itemStack);
        }
        ISkinType skinType = descriptor.getIdentifier().getSkinType();

        if (!worldIn.isRemote) {
            if (skinCapability.canHoldSkinType(descriptor.getIdentifier().getSkinType())) {
                if (skinCapability.setStackInNextFreeSlot(itemStack.copy())) {
                    skinCapability.syncToPlayer((ServerPlayerEntity) playerIn);
                    skinCapability.syncToAllTracking();
                    itemStack.shrink(1);
                }
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemStack);
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.FAIL, itemStack);
    }

    private static final IDispenseItemBehavior dispenserBehavior = new DefaultDispenseItemBehavior() {

        @Override
        protected ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {
            if (!SkinNBTHelper.stackHasSkinData(itemStack)) {
                return super.dispenseStack(blockSource, itemStack);
            }

            BlockState state = blockSource.getBlockState();
            Direction facing = state.getValue(DispenserBlock.FACING);
            BlockPos target = blockSource.getBlockPos().offset(facing);
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(target);
            List list = blockSource.getWorld().getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity entitylivingbase = (LivingEntity) list.get(i);
                if (entitylivingbase instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entitylivingbase;
                    IEntitySkinCapability skinCap = EntitySkinCapability.get(player);
                    if (skinCap.setStackInNextFreeSlot(itemStack.copy())) {
                        itemStack.shrink(1);
                        skinCap.syncToAllTracking();
                        skinCap.syncToPlayer((ServerPlayerEntity) player);
                        return itemStack;
                    }
                }
            }

            return super.dispenseStack(blockSource, itemStack);
        }
    };
}
