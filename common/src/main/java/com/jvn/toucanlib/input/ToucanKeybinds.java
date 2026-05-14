package com.jvn.toucanlib.input;

import com.jvn.toucanlib.client.toucanClientOnly;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.KeyMapping;

public final class toucanKeybinds {
    private final String modId;
    private final String category;
    private final List<toucanKeybind> keybinds = new ArrayList<>();

    private toucanKeybinds(String modId, String category) {
        this.modId = requireIdentifierPart(modId, "modId");
        this.category = requireTranslationKey(category, "category");
    }

    /**
     * Creates a keybind collection with category key.categories.&lt;modid&gt;.
     */
    public static toucanKeybinds create(String modId) {
        String cleanModId = requireIdentifierPart(modId, "modId");
        return new toucanKeybinds(cleanModId, "key.categories." + cleanModId);
    }

    /**
     * Creates a keybind collection with a custom category translation key.
     */
    public static toucanKeybinds create(String modId, String category) {
        return new toucanKeybinds(modId, category);
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
        toucanClientOnly.requireClient("Key mappings");
        String cleanName = requireIdentifierPart(name, "name");
        KeyMapping mapping = new KeyMapping("key." + modId + "." + cleanName, inputType, keyCode, category);
        keybinds.add(new toucanKeybind(cleanName, mapping));
        return mapping;
    }

    /**
     * Registers all remembered mappings through Architectury.
     */
    public void register() {
        toucanClientOnly.requireClient("Key mapping registration");
        for (toucanKeybind keybind : keybinds) {
            KeyMappingRegistry.register(keybind.mapping());
        }
    }

    /**
     * Registers all remembered mappings through Architectury.
     *
     * @deprecated Use {@link #register()} instead. The argument is ignored.
     */
    @Deprecated(forRemoval = false)
    public void register(Object ignoredEvent) {
        register();
    }

    /**
     * Returns the remembered mappings in registration order.
     */
    public List<toucanKeybind> keybinds() {
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
