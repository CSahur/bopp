package pocketutils;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static pocketutils.GemItem.*;

public class GemEvents {
    public static void initialize(){
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            if (source.is(DamageTypes.FALL) && (entity instanceof Player player) && isGemActive(player, "gravity")!=null && player.level() instanceof ServerLevel level) {
                level.playSound(null,  entity.getX(), entity.getY(), entity.getZ(), SoundEvents.MACE_SMASH_AIR, SoundSource.PLAYERS,1.0F,1.0F);
                level.sendParticles(ParticleTypes.GUST_EMITTER_SMALL, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.EXPLOSION, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);

                List<LivingEntity> nearbyList = level.getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(3.0, 0.0, 3.0)
                );
                for (LivingEntity e : nearbyList) {
                    float smashDamage = (amount * 0.8f < 100)? amount * 0.8f : 100;
                    if (e != player){
                        e.hurtServer(level, level.damageSources().mace(player), smashDamage);
                        circularKnockback(e, player, smashDamage*0.04, smashDamage*0.02, smashDamage*0.04);
                    }
                }
                return false;
            }

            if ((source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MACE_SMASH)) && source.getEntity() instanceof Player attacker) {
                Random random = new Random();

                if (entity.level() instanceof ServerLevel level){
                    if (random.nextInt(100) < 10 && isGemActive(attacker, "magic") != null && amount >= 10) {
                        LightningBolt lightning = new LightningBolt(EntityTypes.LIGHTNING_BOLT, level);
                        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                        level.addFreshEntity(lightning);
                    }
                    else if(random.nextInt(100) < 20 && isGemActive(attacker, "health")!=null) {
                        Objects.requireNonNull(source.getEntity().asLivingEntity()).heal((float) (amount*0.3));
                        level.playSound(null,  entity.getX(), entity.getY(), entity.getZ(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.PLAYERS);
                    }
                }
            }

            return ((!source.is(DamageTypes.LIGHTNING_BOLT) && !source.is(DamageTypes.INDIRECT_MAGIC)) && !source.is(DamageTypes.MAGIC)) || !(entity instanceof Player player) || isGemActive(player, "magic") == null;
        });
        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (level.isClientSide()) return InteractionResult.PASS;
            ItemStack stack = player.getMainHandItem();
            ItemStack stackOff = player.getOffhandItem();
            if (player.isShiftKeyDown()){
                if (stackOff.getItem() instanceof GemItem itemOff && !player.getCooldowns().isOnCooldown(stackOff)) {
                    player.getCooldowns().addCooldown(stackOff, itemOff.cooldown);
                    System.out.println(itemOff.cooldown);
                    List<String> abilities = areUsingAbility.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
                    abilities.add(itemOff.name);
                    return InteractionResult.FAIL;
                }

            }else if (stack.getItem() instanceof GemItem item && !player.getCooldowns().isOnCooldown(stack)){
                player.getCooldowns().addCooldown(stack, item.cooldown);
                System.out.println(item.cooldown);
                List<String> abilities = areUsingAbility.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
                abilities.add(item.name);
                return InteractionResult.FAIL;
            }
            return  InteractionResult.PASS;
        });
    }
}
