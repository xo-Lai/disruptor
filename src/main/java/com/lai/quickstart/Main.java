package com.lai.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;


import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Weifeng.Lai
 * @Title: Main
 * @Package com.lai.quickstart
 * @Description: ${todo}
 * @date 2019/5/1 15:33
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 1. 实例化disruptor 对象
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        /**
         * 1. verFactory :消息（event）工厂对象
         * 2. ringBufferSize: 容器长度
         * 3. threadFactory : 线程池
         * 4. ProducerType：消费类型
         * 5. WaitStrategy ：等待策略
         */
        Disruptor<OrderEvent> orderEventDisruptor =
                new Disruptor<OrderEvent>(orderEventFactory, ringBufferSize, threadFactory, ProducerType.SINGLE, waitStrategy);
//        Disruptor<OrderEvent> disruptor = new Disruptor<>(OrderEvent::new, ringBufferSize, DaemonThreadFactory.INSTANCE);


        // 2. Connect the handler(构建disruptor 与 消费者关联)
        orderEventDisruptor.handleEventsWith(new OrderEventHandler());

        // 3. Start the Disruptor, starts all threads running
        orderEventDisruptor.start();

        // 4. Get the ring buffer from the Disruptor to be used for publishing.
        // 获取实际存储数据的容器：RingBuffer
        RingBuffer<OrderEvent> ringBuffer = orderEventDisruptor.getRingBuffer();

//        OrderEventProducer orderEventProduce=new OrderEventProducer(ringBuffer);

        OrderEventProducerWithTranslator orderEventProducerWithTranslator = new OrderEventProducerWithTranslator(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (int i = 0; i < 100; i++) {
            bb.putLong(0,i);
            orderEventProducerWithTranslator.onData(bb);

        }

    }

    public static void translate(OrderEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
    }
}
