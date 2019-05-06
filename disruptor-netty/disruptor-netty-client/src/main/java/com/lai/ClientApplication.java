package com.lai;

import com.lai.client.MessageConsumerForClient;
import com.lai.client.NettyClient;
import com.lai.disruptor.MessageConsumer;
import com.lai.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @author Weifeng.Lai
 * @Title: ClientApplication
 * @Package com.lai
 * @Description: ${todo}
 * @date 2019/5/6 17:02
 */

public class ClientApplication {
    public static void main(String[] args) throws Exception {

        MessageConsumer[] mc = new MessageConsumer[4];
        for (int i = 0; i < mc.length; i++) {
            mc[i] = new MessageConsumerForClient("consumerClientCode:" + i);
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024, new BlockingWaitStrategy(), mc);
        NettyClient nettyClient = new NettyClient();
        nettyClient.sendData();
        nettyClient.close();

    }
}
