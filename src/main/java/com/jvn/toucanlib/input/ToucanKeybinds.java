package com.jvn.toucanlib.input;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public final class ToucanKeybinds {
    private final String modId;
    private final String category;
    private final List<ToucanKeybind> keybinds = new ArrayList<>();

    private ToucanKeybinds(String modId, String category) {
        this.modId = requireIdentifierPart(modId, "modId");
        this.category = requireTranslationKey(category, "category");
    }

    /**
     * Creates a keybind collection with category key.categories.&lt;modid&gt;.
     */
    public static ToucanKeybinds create(String modId) {
        String cleanModId = requireIdentifierPart(modId, "modId");
        return new ToucanKeybinds(cleanModId, "key.categories." + cleanModId);
    }

    /**
     * Creates a keybind collection with a custom category translation key.
     */
    public static ToucanKeybinds create(String modId, String category) {
        return new ToucanKeybinds(modId, category);
    }

    /**
     * Creates and remembers a keyboard key mapping.
     */
    public KeyMapping key(String name, int keyCode) {
        return key(name, InputConstants.Type.KEYSYM, keyCode);
    }

    /**
     * Creates and remembers a key mapping using a caller-selected input type.
     */
    public KeyMapping key(String name, InputConstants.Type inputType, int keyCode) {
        String cleanName = requireIdentifierPart(name, "name");
        KeyMapping mapping = new KeyMapping("key." + modId + "." + cleanName, inputType, keyCode, category);
        keybinds.add(new ToucanKeybind(cleanName, mapping));
        return mapping;
    }

    /**
     * Registers all remembered mappings with NeoForge.
     */
    public void register(RegisterKeyMappingsEvent event) {
        for (ToucanKeybind keybind : keybinds) {
            event.register(keybind.mapping());
        }
    }

    /**
     * Returns the remembered mappings in registration order.
     */
    public List<ToucanKeybind> keybinds() {
        return Collections.unmodifiableList(keybinds);
    }

    /**
     * Returns the category translation key.
     */
    public String category() {
        return category;
    }

    private static String requireIdentifierPart(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }

    private static String requireTranslationKey(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}
