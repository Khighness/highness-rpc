package top.parak.loadbalance.loadbalancer;

import top.parak.loadbalance.AbstractLoadBalance;
import top.parak.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @author KHighness
 * @since 2021-09-11
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
