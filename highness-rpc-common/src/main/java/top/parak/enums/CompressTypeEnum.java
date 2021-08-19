package top.parak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KHighness
 * @since 2021-08-19
 * @apiNote 压缩类型
 */
@Getter
@AllArgsConstructor
public enum CompressTypeEnum {
    GZIP((byte) 0x01, "gzip");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (CompressTypeEnum c : CompressTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
