package com.github.twitch4j.common.util;

import java.util.function.Function;

import com.google.common.cache.Cache;

import lombok.SneakyThrows;

public class CacheUtils {
    @SneakyThrows
	public static <K, V> V getSafe(Cache<K, V> cache, K key, Function<K, V> compute) {
        return cache.get(key, () -> compute.apply(key));
	}
}
