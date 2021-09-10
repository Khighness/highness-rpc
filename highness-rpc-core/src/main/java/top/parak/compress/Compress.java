package top.parak.compress;

import top.parak.extension.SPI;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
