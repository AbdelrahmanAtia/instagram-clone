package com.javaworld.instagram.userinfoservice.caching;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InstaCache {

    private static final Map<UUID, Integer> POSTS_COUNT_CACHE = new ConcurrentHashMap<>();

    public static void putPostsCount(UUID key, Integer value) {
        POSTS_COUNT_CACHE.put(key, value);
    }

    public static Integer getPostsCount(UUID key) {
        return POSTS_COUNT_CACHE.get(key);
    }

    public static void removePostsCount(UUID key) {
        POSTS_COUNT_CACHE.remove(key);
    }

    public static void clearPostsCountCache() {
        POSTS_COUNT_CACHE.clear();
    }

    public static boolean postsCountCacheContainsKey(UUID key) {
        return POSTS_COUNT_CACHE.containsKey(key);
    }
}