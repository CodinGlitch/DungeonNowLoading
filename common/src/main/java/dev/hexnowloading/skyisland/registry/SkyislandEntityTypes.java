package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.entity.ChaosSpawnerEntity;
import dev.hexnowloading.skyisland.registration.RegistrationProvider;
import dev.hexnowloading.skyisland.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class SkyislandEntityTypes {
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPE = RegistrationProvider.get(Registries.ENTITY_TYPE, Skyisland.MOD_ID);

    //public static final RegistryObject<EntityType<Entity>> WINDSTONE = ENTITY_TYPE.register("windstone", () -> EntityType.Builder.of(WindstoneEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build(new ResourceLocation(Skyisland.MOD_ID, "windstone").toString()));
    public static final RegistryObject<EntityType<ChaosSpawnerEntity>> CHAOS_SPAWNER = ENTITY_TYPE.register("chaos_spawner", () -> EntityType.Builder.of(ChaosSpawnerEntity::new, MobCategory.MONSTER).sized(2.8F, 2.8F).build(new ResourceLocation(Skyisland.MOD_ID, "chaos_spawner").toString()));

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAllAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> map = new HashMap<>();

        map.put(CHAOS_SPAWNER.get(), ChaosSpawnerEntity.createAttributes().build());

        return map;
    }

    public static void init() {}
}
