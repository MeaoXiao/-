package net.mcreator.meaoworldcore.procedures;

import top.theillusivec4.curios.api.CuriosApi;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.mcreator.meaoworldcore.network.MeaoworldCoreModVariables;
import net.mcreator.meaoworldcore.init.MeaoworldCoreModItems;

import java.util.concurrent.atomic.AtomicReference;

public class BrewingActionProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState wheatBlock = Blocks.AIR.defaultBlockState();
		BlockState clickedBlock = Blocks.AIR.defaultBlockState();
		boolean having_empty = false;
		ItemStack mainhand_item = ItemStack.EMPTY;
		ItemStack result_wine = ItemStack.EMPTY;
		ItemStack brewer_license = ItemStack.EMPTY;
		double wine_sweetness = 0;
		double ifempty = 0;
		wine_sweetness = 0;
		mainhand_item = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		ifempty = 0;
		having_empty = false;
		for (int index0 = 0; index0 < 36; index0++) {
			if ((new Object() {
				public ItemStack getItemStack(int sltid, Entity entity) {
					AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
					entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
					return _retval.get();
				}
			}.getItemStack((int) ifempty, entity)).getItem() == Blocks.AIR.asItem()) {
				having_empty = true;
				break;
			} else {
				ifempty = ifempty + 1;
			}
		}
		if (entity instanceof LivingEntity lv ? CuriosApi.getCuriosHelper().findEquippedCurio(MeaoworldCoreModItems.BREWER_BADGE.get(), lv).isPresent() : false) {
			if (having_empty == true) {
				if (mainhand_item.getOrCreateTag().getBoolean("brewing_materials") == true) {
					result_wine = new ItemStack(MeaoworldCoreModItems.TEST_HONEY_WINE.get());
					result_wine.getOrCreateTag().putDouble("sweetness", (mainhand_item.getOrCreateTag().getDouble("sweetness")));
					result_wine.getOrCreateTag().putDouble("wine_quality", ((mainhand_item.getOrCreateTag().getDouble("sweetness") + Mth.nextInt(RandomSource.create(), 1, 4))
							* (entity.getCapability(MeaoworldCoreModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new MeaoworldCoreModVariables.PlayerVariables())).brewer_license_level));
					if (entity instanceof Player _player) {
						ItemStack _setstack = result_wine.copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = mainhand_item.copy();
						_setstack.setCount((int) (mainhand_item.getCount() - 1));
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					{
						double _setval = (entity.getCapability(MeaoworldCoreModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new MeaoworldCoreModVariables.PlayerVariables())).brewer_license_level + 1;
						entity.getCapability(MeaoworldCoreModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.brewer_license_level = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("\u8BF7\u6301\u6709\u6B63\u786E\u7684\u917F\u9152\u539F\u6750\u6599"), false);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("\u60A8\u7684\u7269\u54C1\u680F\u6CA1\u6709\u8DB3\u591F\u7A7A\u95F4"), false);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u60A8\u6CA1\u6709\u88C5\u5907\u917F\u9152\u5E08\u6267\u7167"), false);
		}
	}
}
