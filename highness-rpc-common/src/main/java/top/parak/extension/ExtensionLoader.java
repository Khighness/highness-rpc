package top.parak.extension;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KHighness
 * @since 2021-08-20
 * @apiNote 一个SPI接口对应一个ExtensionLoader
 */
@Slf4j
public class ExtensionLoader<T> {
    /**
     * SPI加载路径
     */
    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";
    /**
     * 类加载器缓存，KEY: SPI接口Class，VALUE：该接口的ExtensionClassLoader
     */
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    /**
     * 目标类实例缓存，KEY：SPI接口Class，VALUE：SPI接口实现类的对象实例
     */
    private static final Map<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    /**
     * SPI接口Class
     */
    private final Class<?> type;
    /**
     * 缓存创建好的extensionClass实例
     */
    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    /**
     * 存放文件的SPI key-value pair
     */
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    public ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type should not be null.");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface.");
        }
        if (!type.isAssignableFrom(SPI.class)) {
            throw new IllegalArgumentException("Extension type must be annotated by @SPI");
        }
        // 先从缓存中获取，没有命中则创建
        ExtensionLoader<S> extensionLoader = (ExtensionLoader<S>) EXTENSION_INSTANCES.get(type);
        if (extensionLoader == null) {
            EXTENSION_INSTANCES.putIfAbsent(type, new ExtensionLoader<S>(type));
            extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        }
        return extensionLoader;
    }

    /**
     * 通过name获取对应的类的对象实例
     *
     * @param name SPI文件中的key
     * @return 对象实例
     */
    public T getExtension(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Extension name should not be null or empty.");
        }
        // 先从缓存中获取，没有命中则创建
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        // 双重检查锁保证单例和线程安全
        Object instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    /**
     * 创建name对应类的对象实例
     *
     * @param name SPI文件中的key
     * @return 对象实例
     */
    private T createExtension(String name) {
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new RuntimeException("No such extension of name " + name);
        }
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            try {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return instance;
    }

    /**
     * 获取SPI文件中的所有name-class键值对
     *
     * @return map key:name, value:class
     */
    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        // 双重检查锁
        if (classes == null) {
            synchronized (cachedClasses) {
                if (classes == null) {
                    classes = new HashMap<>();
                    // 从SPI加载路径中加载所有SPI接口实现类
                    loadDirectory(classes);
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    /**
     * 用类加载器加载SPI路径下的type对应的SPI文件
     *
     * @param extensionClasses  key:name, value:class
     */
    private void loadDirectory(Map<String, Class<?>> extensionClasses) {
        String fileName = ExtensionLoader.SERVICE_DIRECTORY + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            urls = classLoader.getResources(fileName);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourceUrl = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceUrl);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 读取指定路径的SPI文件，将每一行解析成name-class键值对，
     * 并使用类加载器加载对应class文件，加入extensionClasses。
     *
     * @param extensionClasses key:name, value:class
     * @param classLoader      类加载器
     * @param resourceUrl      资源路径
     */
    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, URL resourceUrl) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceUrl.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final int ci = line.indexOf("#");
                // 注释
                if (ci >= 0) {
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        final int ei = line.indexOf("=");
                        String name = line.substring(0, ei).trim();
                        String clazzName = line.substring(ei + 1).trim();
                        if (name.length() > 0 && clazzName.length() > 0) {
                            Class<?> clazz = classLoader.loadClass(clazzName);
                            extensionClasses.put(name, clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
