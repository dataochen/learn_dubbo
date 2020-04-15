package org.cdt.mySpi;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author dataochen
 * @Description 自定义 spi加载器
 * @date: 2020/4/15 10:37
 */
public class MySpiLoad {
    private static final String path = "cdtSpi/";

    /**
     * 获取所有实现类
     * 步骤：
     * 1.获取全限名
     * 2.通过全限名查找配置文件
     * 3.通过配置文件的value值加载类实例
     * 4.返回集合
     *
     * @param interfaces
     * @param <T>
     * @return
     */
    public static <T> List<T> loadAll(Class<T> interfaces) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        LinkedList<T> ts = new LinkedList<>();
        String canonicalName = interfaces.getCanonicalName();
        InputStream resourceAsStream = interfaces.getClassLoader().getResourceAsStream(path + canonicalName + ".properties");
        if (Objects.isNull(resourceAsStream)) {
            throw new IllegalArgumentException("can't find this file " + path + canonicalName + ".properties");
        }
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
            Object value = objectObjectEntry.getValue();
            Class<?> aClass = Class.forName(value.toString());
            Object o = aClass.newInstance();
            ts.add((T) o);
        }
        return ts;
    }

    public static <T> T load(Class<T> interfaces, String spiKey) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String canonicalName = interfaces.getCanonicalName();
        InputStream resourceAsStream = interfaces.getClassLoader().getResourceAsStream(path + canonicalName + ".properties");
        if (Objects.isNull(resourceAsStream)) {
            throw new IllegalArgumentException("can't find this file" + path + canonicalName + ".properties");
        }
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        Object value = properties.get(spiKey);
        Class<?> aClass = Class.forName(value.toString());
        Object o = aClass.newInstance();
        return (T) o;
    }

    public static <T> T loadFirst(Class<T> interfaces) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String canonicalName = interfaces.getCanonicalName();
        InputStream resourceAsStream = interfaces.getClassLoader().getResourceAsStream(path + canonicalName + ".properties");
        if (Objects.isNull(resourceAsStream)) {
            throw new IllegalArgumentException("can't find this file " + path + canonicalName + ".properties");
        }
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        Object value = properties.entrySet().stream().map(Map.Entry::getValue).findFirst().get();
        Class<?> aClass = Class.forName(value.toString());
        Object o = aClass.newInstance();
        return (T) o;
    }
}
