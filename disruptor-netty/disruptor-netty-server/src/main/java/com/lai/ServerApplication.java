package com.lai;

import com.lai.disruptor.MessageConsumer;
import com.lai.disruptor.RingBufferWorkerPoolFactory;
import com.lai.server.MessageConsumerForServer;
import com.lai.server.NettyServer;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Weifeng.Lai
 * @Title: ServerApplication
 * @Package com.lai
 * @Description: ${todo}
 * @date 2019/5/6 15:54
 */
@SpringBootApplication
public class ServerApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MessageConsumer[] mc = new MessageConsumer[4];
        for (int i = 0; i < mc.length; i++) {
            mc[i] = new MessageConsumerForServer("consumerServerCode:" + i);
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024, new BlockingWaitStrategy(), mc);
        new NettyServer();
    }
}
