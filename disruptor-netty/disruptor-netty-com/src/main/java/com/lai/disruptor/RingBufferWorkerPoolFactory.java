package com.lai.disruptor;

import com.lai.entity.TranslatorDataWrapper;
import com.lai.exception.EventExceptionHandler;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * @author Weifeng.Lai
 * @Title: RingBufferWorkerPoolFactory
 * @Package com.lai.disruptor
 * @Description: ${todo}
 * @date 2019/5/6 20:43
 */
public class RingBufferWorkerPoolFactory {
    private static class SingletonHolder {
        static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
    }

    private RingBufferWorkerPoolFactory() {

    }

    public static RingBufferWorkerPoolFactory getInstance() {
        return SingletonHolder.instance;
    }


    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<String, MessageProducer>();

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<String, MessageConsumer>();


    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataWrapper> workerPool;

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy strategy, MessageConsumer[] messageConsumers) {
        //1. 构建ringBuffer对象
        this.ringBuffer = RingBuffer.create(type, new EventFactory<TranslatorDataWrapper>() {
            @Override
            public TranslatorDataWrapper newInstance() {
                return new TranslatorDataWrapper();
            }
        }, bufferSize, strategy);
        //2.设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();
        //3.设置工作池
        this.workerPool = new WorkerPool<TranslatorDataWrapper>(this.ringBuffer,
                this.sequenceBarrier,
                new EventExceptionHandler(), messageConsumers);

        //4 把所构建的消费者置入池中
        for (MessageConsumer mc : messageConsumers) {
            consumers.put(mc.getConsumerId(), mc);
        }

        //5 添加我们的sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
        //6 启动我们的工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
    }
    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer messageProducer = producers.get(producerId);
        if (null == messageProducer) {
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }

}
