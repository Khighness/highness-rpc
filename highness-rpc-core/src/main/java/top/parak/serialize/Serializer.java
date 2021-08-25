package top.parak.serialize;

import top.parak.extension.SPI;

/**
 * @author KHighness
 * @since 2021-08-23
 * @apiNote 序列化
 */
@SPI
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj 需要序列号的对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param bytes 序列化后的字节数组
     * @param clazz 目标类
     * @param <T>   泛型
     * @return 反序列化的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
