package earth.terrarium.heracles.api.tasks.defaults;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.predicates.NbtPredicate;
import earth.terrarium.heracles.Heracles;
import earth.terrarium.heracles.api.tasks.QuestTask;
import earth.terrarium.heracles.api.tasks.QuestTaskType;
import earth.terrarium.heracles.api.tasks.storage.defaults.BooleanTaskStorage;
import earth.terrarium.heracles.common.utils.RegistryValue;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ItemInteractionTask(
    String id, RegistryValue<Item, Item> item, NbtPredicate nbt
) implements QuestTask<ItemStack, ByteTag, ItemInteractionTask> {
    public static final QuestTaskType<ItemInteractionTask> TYPE = new Type();

    @Override
    public ByteTag test(QuestTaskType<?> type, ByteTag progress, ItemStack input) {
        return storage().of(progress, item.is(input.getItemHolder()) && nbt().matches(input));
    }

    @Override
    public float getProgress(ByteTag progress) {
        return storage().readBoolean(progress) ? 1 : 0;
    }

    @Override
    public BooleanTaskStorage storage() {
        return BooleanTaskStorage.INSTANCE;
    }

    @Override
    public QuestTaskType<ItemInteractionTask> type() {
        return TYPE;
    }

    private static class Type implements QuestTaskType<ItemInteractionTask> {
        @Override
        public ResourceLocation id() {
            return new ResourceLocation(Heracles.MOD_ID, "item_interaction");
        }

        @Override
        public Codec<ItemInteractionTask> codec(String id) {
            return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                RegistryValue.codec(BuiltInRegistries.ITEM).fieldOf("item").forGetter(ItemInteractionTask::item),
                NbtPredicate.CODEC.fieldOf("nbt").orElse(NbtPredicate.ANY).forGetter(ItemInteractionTask::nbt)
            ).apply(instance, ItemInteractionTask::new));
        }
    }
}
