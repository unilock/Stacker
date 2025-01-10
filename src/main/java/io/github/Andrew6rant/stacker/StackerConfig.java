package io.github.Andrew6rant.stacker;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class StackerConfig extends ReflectiveConfig {
    @Comment("Maximum Stack Size\nRecommended maximum value is 1,073,741,823")
    @IntegerRange(min = 1, max = 2_147_483_647)
    public final TrackedValue<Integer> maxStacker = value(64);

    @Comment("Variable Font Scale Override\nSet this to true if you want font size to scale with amount")
    public final TrackedValue<Boolean> fontOverride = value(true);

    @Comment("Item Text Size\nOnly applies if Variable Font Scale Override is set to false")
    @IntegerRange(min = 0, max = 50)
    public final TrackedValue<Integer> itemCountScaleInt = value(25);

    @Comment("Stack Size Overrides\nFormat: \"mod:name:stack_size\" or \"#tag:name:stack_size\"")
    public final TrackedValue<ValueList<String>> itemOverride = list("",
            "#stacker:not_stackable:1",
            "#c:potions:16",
            "minecraft:totem_of_undying:16"
    );

    @Comment("Disallow \"Unstackable\" Items\nSet this to true to prevent changing the maximum stack size of all items that have a maximum stack size of 1 by default\nUseful as a last resort if Stack Size Overrides aren't enough")
    public final TrackedValue<Boolean> disallowUnstackableItems = value(false);
}
