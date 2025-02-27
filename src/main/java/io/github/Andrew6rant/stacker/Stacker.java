package io.github.Andrew6rant.stacker;

import io.github.Andrew6rant.stacker.mixin.ItemAccess;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stacker implements ModInitializer {
	private static final String MOD_ID = "stacker";
	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final StackerConfig CONFIG = StackerConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, StackerConfig.class);

	@Override
	public void onInitialize() {
		CONFIG.registerCallback(config -> loadStacker("save"));
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> loadStacker("reload"));
	}

	public static void loadStacker(String configMsg) {
		LOGGER.info("Stacker: Attempting to {} config...", configMsg);

		boolean disallowUnstackableItems = CONFIG.disallowUnstackableItems.value();
		Set<String> invalidSet = new HashSet<>();

		for (Item item : Registries.ITEM) {
			if (!(item.isDamageable() || (disallowUnstackableItems && item.getMaxCount() == 1))) {
				Stacker.setMax(item, CONFIG.maxStacker.value());
			}
			Stacker.setMax(item, Stacker.overrideItem(item, CONFIG.itemOverride.value(), invalidSet));
		}

		if (!invalidSet.isEmpty()) {
			LOGGER.error("Stacker: Invalid override entries!");
			LOGGER.warn("Stacker: The following entries were invalid:");
			for (String invalid : invalidSet) {
				LOGGER.warn("Stacker: \"{}\"", invalid);
			}
			LOGGER.warn("Stacker: Make sure to use the format \"mod:item:max_stack\" or \"#tag:item:max_stack\".");
		}

		LOGGER.info("save".equals(configMsg) ? "Stacker: Config saved!": "Stacker: Config "+configMsg+"ed!");
	}

	public static void setMax(Item item, int max) {
		if (max > 0) {
			((ItemAccess) item).setMaxCount(max);
		}
	}

	public static boolean isValid(String overrideEntry, String[] splitEntry, Set<String> invalidSet) {
		if (splitEntry.length != 3) {
			invalidSet.add(overrideEntry);
			return false;
		}

		try {
			Integer.parseInt(splitEntry[2]);
		} catch (NumberFormatException e) {
			invalidSet.add(overrideEntry);
			return false;
		}

		return true;
	}

	public static Integer overrideItem(Item item, List<String> overrideList, Set<String> invalidSet) {
		for (String overrideEntry : overrideList) {
			if (overrideEntry.startsWith("#")) {
				String[] splitEntry = overrideEntry.trim().substring(1).split(":"); // split into three parts: tag id, item name, max count

				if (isValid(overrideEntry, splitEntry, invalidSet)) {
					if (Registries.ITEM.getEntry(item).isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(splitEntry[0], splitEntry[1])))) {
						return Integer.parseInt(splitEntry[2]);
					}
				}
			} else {
				String[] splitEntry = overrideEntry.trim().split(":"); // split into three parts: tag id, item name, max count

				if (isValid(overrideEntry, splitEntry, invalidSet)) {
					if (Registries.ITEM.getId(item).toString().equalsIgnoreCase(splitEntry[0] + ":" + splitEntry[1])) {
						return Integer.parseInt(splitEntry[2]);
					}
				}
			}
		}

		return 0;
	}
}
