package top.parak.utils;

/**
 * @author KHighness
 * @since 2021-08-21
 */
public class RuntimeUtils {

    /**
     * 获取CPU的核心数
     *
     * @return cpu的核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }

}
