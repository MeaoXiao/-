package net.mcreator.meaoworldcore.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;

import net.mcreator.meaoworldcore.init.MeaoworldCoreModItems;

import java.util.concurrent.atomic.AtomicReference;

public class ScbtProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double ifempty = 0;
		boolean having_empty = false;
		ItemStack New_honey_bottle = ItemStack.EMPTY;
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
		if (having_empty == true) {
			if (entity instanceof Player _player) {
				ItemStack _stktoremove = new ItemStack(MeaoworldCoreModItems.UNKNOWNHONEYBOTTLE.get());
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			New_honey_bottle = new ItemStack(MeaoworldCoreModItems.HONEYBOTTLE.get());
			New_honey_bottle.getOrCreateTag().putBoolean("brewing_materials", true);
			New_honey_bottle.getOrCreateTag().putDouble("sweetness", (Mth.nextInt(RandomSource.create(), 3, 6)));
			if (entity instanceof Player _player) {
				ItemStack _setstack = New_honey_bottle.copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u60A8\u7684\u7269\u54C1\u680F\u6CA1\u6709\u8DB3\u591F\u7A7A\u95F4"), false);
		}
	}
}
