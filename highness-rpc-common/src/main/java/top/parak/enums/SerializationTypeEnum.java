package top.parak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KHighness
 * @since 2021-08-19
 * @apiNote 序列化类型
 */
@Getter
@AllArgsConstructor
public enum SerializationTypeEnum {

    KRYO((byte) 0X0, "kryo"),
    PROTOSTUFF((byte) 0X0, "protostuff");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (SerializationTypeEnum c : SerializationTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
