package net.mcreator.meaoworldcore.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class TestHoneyWineDangWanJiaWanChengShiYongWuPinShiProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (itemstack.getOrCreateTag().getDouble("sweetness") * 20), (int) (itemstack.getOrCreateTag().getDouble("sweetness") / 2)));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) (itemstack.getOrCreateTag().getDouble("wine_quality") * 10), (int) (itemstack.getOrCreateTag().getDouble("wine_quality") / 8)));
	}
}
