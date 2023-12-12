package ru.courses2.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils <T> implements InvocationHandler {
    private Map <String, Object> cacheValue = new LinkedHashMap<>();
    private T obj;

    public Utils(T t) {
        this.obj = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object value = null;
        Method tmp = obj.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes());
        if(tmp.isAnnotationPresent(Mutator.class) || tmp.isAnnotationPresent(Cache.class)) {
            String keys = proxy.toString();
            if (tmp.isAnnotationPresent(Mutator.class)) {
                value = method.invoke(obj, args);
                cacheValue.remove(keys);
            } else if (tmp.isAnnotationPresent(Cache.class)) {
                if (cacheValue.isEmpty() || !cacheValue.containsKey(keys)) {
                    value = method.invoke(obj, args);
                    cacheValue.put(keys, value);
                } else {
                    value = cacheValue.get(keys);
                }
            }
        } else {
            value = method.invoke(obj, args);
        }
        return value;
    }
    public static <T> T cache(T t) {
        T t1 = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                new Utils<>(t));
        return t1;
    }
}