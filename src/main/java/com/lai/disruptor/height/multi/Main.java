package com.lai.disruptor.height.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;


/**
 * @author Weifeng.Lai
 * @Title: Main
 * @Package com.lai.disruptor.height.multi
 * @Description: ${todo}
 * @date 2019/5/4 10:37
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //1.创建ringBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, new EventFactory<Order>() {
            @Override
            public Order newInstance() {
                return new Order();
            }
        }, 1024 * 1024, new YieldingWaitStrategy());

        // 2. 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3. 创建多消费者
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < 10; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4. 构建消费工作池
        WorkerPool<Order> workerPool=new WorkerPool<Order>(ringBuffer,sequenceBarrier,new EventExceptionHandler(),consumers);

        // 5.设置多个消费者的sequence 序号用于单独统计消费进度，并且设置到ringbuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());


        //6.启动workerPool
        workerPool.start(Executors.newFixedThreadPool(10));

        //设置异步生产 100个生产者
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int j = 0; j < 100; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("--------线程创建完毕，开始生产数据----------");
        latch.countDown();

        Thread.sleep(10000);
        System.out.println("消费者处理的任务总数：" + consumers[0].getCount());

    }

    static  class  EventExceptionHandler implements ExceptionHandler<Order>{
        @Override
        public void handleEventException(Throwable ex, long sequence, Order event) {
            System.out.println("handleEventException");
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            System.out.println("handleOnStartException");
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            System.out.println("handleOnShutdownException");
        }
    }
}
