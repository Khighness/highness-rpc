package top.parak.compress;

/**
 * @author KHighness
 * @since 2021-08-24
 * @apiNote 压缩数据包
 */
public interface Compress {

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);

}
