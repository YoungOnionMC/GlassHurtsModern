package com.youngonion.glasshurts.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    public static DamageSource glass_damage(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key));
    }

    public static Map<UUID, Vec3> PreVelocities = new HashMap<>();

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        var entity = event.getEntity();
        if(entity.level().isClientSide) return;

        var level = entity.level();

        var velocity = entity.getDeltaMovement();
        AABB nextBoundingBox = entity.getBoundingBox().move(velocity);

        BlockPos.betweenClosedStream(nextBoundingBox)
                .forEach(pos -> {
                    BlockState state = level.getBlockState(pos);
                    if(!entity.horizontalCollision) {
                        if (state.is(Tags.Blocks.GLASS) || state.is(Tags.Blocks.GLASS_PANES)) {
                            collide(entity, pos, state, velocity);
                        }
                    }
                });

        var previousVelocity = PreVelocities.get(entity.getUUID());
        if(previousVelocity == null) return;

        PreVelocities.remove(entity.getUUID());
        //if(entity.horizontalCollision || entity.minorHorizontalCollision || entity.verticalCollision) {
            Vec3 restore = previousVelocity;

            if(entity.horizontalCollision && restore.length() >= 0.4f) {
                restore = new Vec3(restore.x, entity.getDeltaMovement().y, restore.z);
            }
            entity.setDeltaMovement(restore);
            entity.hurtMarked = true;
        //}
    }

    private static void collide(Entity entity, BlockPos pos, BlockState state, Vec3 velocity) {
        float velocityNeeded = 0.f;
        if(state.is(Tags.Blocks.GLASS)) {
            velocityNeeded = 0.1f;
        } else if(state.is(Tags.Blocks.GLASS_PANES)) {
            velocityNeeded = 0.1f;
        }
        double vel = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        if(velocityNeeded != 0 && vel >= velocityNeeded) {
            PreVelocities.put(entity.getUUID(), velocity);
            entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            entity.hurt(glass_damage(entity.level(), GlassDamageType.GLASS_BREAK), velocityNeeded * 10.f);
        }
    }

    @SubscribeEvent
    public static void breakGlassEvent(BlockEvent.BreakEvent event) {
        ItemStack mainStack = event.getPlayer().getMainHandItem();



        if(event.getState().is(Tags.Blocks.GLASS)) {
            if(mainStack.isCorrectToolForDrops(event.getState()) || EnchantmentHelper.hasSilkTouch(mainStack)) {
                return;
            }
            var player = event.getPlayer();
            if(player instanceof ServerPlayer serverPlayer) {
                if(canDamage(serverPlayer)) {
                    serverPlayer.hurt(glass_damage(serverPlayer.level(), GlassDamageType.GLASS_BREAK), 2.0f);
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 3));
                }
            }

        }
        if(event.getState().is(Tags.Blocks.GLASS_PANES)) {
            if(mainStack.isCorrectToolForDrops(event.getState()) || EnchantmentHelper.hasSilkTouch(mainStack)) {
                return;
            }
            var player = event.getPlayer();
            if(player instanceof ServerPlayer serverPlayer) {
                if(canDamage(serverPlayer)) {
                    serverPlayer.hurt(glass_damage(serverPlayer.level(), GlassDamageType.GLASS_BREAK), 1.0f);
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 3));
                }
            }

        }
    }

    private static boolean canDamage(ServerPlayer player) {

        ItemStack mainItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();

        boolean damage = true;

        if(mainItem.is(GlassHurtsItems.LEATHER_GLOVE.get())) {
            if(!player.getCooldowns().isOnCooldown(mainItem.getItem())) {
                player.getCooldowns().addCooldown(mainItem.getItem(), 35);
                damage = false;
            }
            mainItem.hurtAndBreak(1, player, (player1) -> {});
            return damage;
        }

        if (offhandItem.is(GlassHurtsItems.LEATHER_GLOVE.get())) {
            if(!player.getCooldowns().isOnCooldown(offhandItem.getItem())) {
                player.getCooldowns().addCooldown(offhandItem.getItem(), 35);
                damage = false;
            }
            offhandItem.hurtAndBreak(1, player, (player1) -> {});
        }
        return damage;
    }

}
