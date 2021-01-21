package com.li.api.core;

import com.li.api.pojo.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黎源
 * @date 2021/1/2 15:45
 */
public class UserLocal {

    private static ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<>();

    public static User getUser() {
        return (User)mapThreadLocal.get().get("user");
    }

    public static Integer getScope() {
        return (int)mapThreadLocal.get().get("scope");
    }

    public static void setUser(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        mapThreadLocal.set(map);
    }

    public static void clear() {
        mapThreadLocal.remove();
    }
}
