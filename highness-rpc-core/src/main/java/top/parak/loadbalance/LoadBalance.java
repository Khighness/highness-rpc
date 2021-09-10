package top.parak.loadbalance;

import top.parak.extension.SPI;
import top.parak.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@SPI
public interface LoadBalance {
    /**
     * Choose one from the list of existing service addresses list
     *
     * @param serviceAddresses Service address list
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest);
}
