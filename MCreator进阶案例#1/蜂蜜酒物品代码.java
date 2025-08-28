
package net.mcreator.meaoworldcore.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.meaoworldcore.procedures.TestHoneyWineDangWanJiaWanChengShiYongWuPinShiProcedure;
import net.mcreator.meaoworldcore.procedures.SweetnessLoreDisplayProcedure;
import net.mcreator.meaoworldcore.procedures.WineQualityLoreDisplayProcedure;

import java.util.List;

public class TestHoneyWineItem extends Item {
	public TestHoneyWineItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.3f).alwaysEat().build()));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		list.add(Component.literal(SweetnessLoreDisplayProcedure.execute(itemstack)));
		list.add(Component.literal(WineQualityLoreDisplayProcedure.execute(itemstack)));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		TestHoneyWineDangWanJiaWanChengShiYongWuPinShiProcedure.execute(entity, itemstack);
		return retval;
	}
}
