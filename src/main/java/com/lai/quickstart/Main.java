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

        /**
         * Sequence class: 解释一个序号
         *              1.通过顺序递增的需要来编号，管理进行交换的数据
         *              2.对数据（事件）的处理过程总是沿着朱哥递增处理
         *              3.一个Sequence 用于跟踪某个特定事件处理者（RingBuffer/Producer/Consumer）
         *              4. 可以看做一个AtomicLong用于标识进度
         *              5. 还有一个目的防止不同的Sequence 之间CPU 缓存伪共享（flase sharing）的问题
         * Sequencer  class: 真正的Disruptor 的真正核心
         *              1.实现两个接口
         *                  1.1 SingleProducerSequencer
         *                  1.2 MultiProducerSequencer
         *              2.主要实现生产者和消费者之间快速，正确地传递数据的并发算法
         *
         * SequenceBarrier class :用于保持RingBuffer 的Main Published Sequence(Producer)和Consumer 之间的平衡关系；
         *                        SequenceBarrier 还定义了决定Consumer是否还有可处理的事件逻辑gigit
         */


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
         *     BlockingWaitStrategy: 是最低效的策略，但是对CPU的消耗也是最小并且再不同部署环境中能提供更加一致性的性能表现
         *     SleepingWaitStrategy: 的性能表现和BlockingWaitStrategy差不多，对CPU消耗也类似，但是对生产线程影响最小，适合用户异步日志类似场景
         *     YieldingWaitStrategy：的性能是最好的，适合用户低延迟的系统，在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如：CPU开启超线程的特征
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
            bb.putLong(0, i);
            orderEventProducerWithTranslator.onData(bb);

        }

    }

    public static void translate(OrderEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
    }
}
