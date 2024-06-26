package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.config.GeneralConfig;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class SkullOfChaosItem extends Item {

    public SkullOfChaosItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        AABB aabb = (new AABB(player.blockPosition())).inflate(16);
        List<ChaosSpawnerEntity> sleepingTargets;
        List<ChaosSpawnerEntity> targets = level.getEntitiesOfClass(ChaosSpawnerEntity.class, aabb);
        sleepingTargets = targets.stream().filter(chaosSpawnerEntity -> chaosSpawnerEntity.getState() == ChaosSpawnerEntity.State.SLEEPING).collect(Collectors.toList());
        if (!sleepingTargets.isEmpty()) {
            player.startUsingItem(hand);
            level.playSound(player, player, DNLSounds.CHAOS_SPAWNER_LAUGHTER.get(), SoundSource.RECORDS, 1.0F, 2.0F);
            player.getCooldowns().addCooldown(this, 7);
            player.awardStat(Stats.ITEM_USED.get(this));
            for (ChaosSpawnerEntity chaosSpawnerEntity : targets) {
                chaosSpawnerEntity.startBossFight();
            }
            if (player instanceof ServerPlayer) {
                itemStack.hurtAndBreak(1, player, (player1 -> player1.broadcastBreakEvent(hand)));
            }
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        if (GeneralConfig.TOGGLE_HELPFUL_ITEM_TOOLTIP.get()) {
            components.add(Component.translatable("item.dungeonnowloading.skull_of_chaos.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}
