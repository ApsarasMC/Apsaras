package org.apsarasmc.sponge.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentUtil {
    private ComponentUtil() {
        //
    }

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR);

    public static Component deserialize(String context){
        return serializer.deserialize(context.replaceAll(
                String.valueOf(LegacyComponentSerializer.SECTION_CHAR),
                String.valueOf(LegacyComponentSerializer.AMPERSAND_CHAR))
        );
    }

    public static String serialize(Component context){
        return serializer.serialize(context);
    }
}
