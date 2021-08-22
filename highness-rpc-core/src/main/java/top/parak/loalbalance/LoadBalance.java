package top.parak.loalbalance;

import top.parak.extension.SPI;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-08-22
 */
@SPI
public interface LoadBalance {

    String selectServiceAddress(List<String> serviceAddress);

}
