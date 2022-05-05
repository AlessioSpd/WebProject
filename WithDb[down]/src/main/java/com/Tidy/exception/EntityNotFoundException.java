package com.Tidy.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String className, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(className, toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String className, Map<String, String> searchParams) {
        return className + " non trovato per i parametri " +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Voci non valide");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}
