package com.lai.disruptor.height;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author Weifeng.Lai
 * @Title: Main
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 21:10
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 构建一个线程池用户提交用于提交
        ExecutorService es = Executors.newFixedThreadPool(4);

        // 1.构建Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        }, 1024 * 1024, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BusySpinWaitStrategy());

        // 2. 把消费者关联到Disruptor 中 handlerEventWith

        // 2.1 串行操作
//        disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());

        // 2.2 并行操作：
//        disruptor.handleEventsWith(new Handler1());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());
//        disruptor.handleEventsWith(new Handler1(), new Handler2(), new Handler3(), new Handler4(), new Handler5());

        // 2.3.1 菱形操作 一，即并行加串行执行 此处是 handler3 和 handler2执行完之后再执行handler1
//        disruptor.handleEventsWith(new Handler3(), new Handler2())
//                .handleEventsWith(new Handler1());

        //2.3.2 菱形操作 二
//        EventHandlerGroup<Trade> ehGrop = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        ehGrop.then(new Handler3());

        //2.4 六边形操作 由于此处使用了5个Handler需要启动5个线程，此时需要修改disruptor的线程池es2的大小为5
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();
        Handler3 h3 = new Handler3();
        Handler4 h4 = new Handler4();
        Handler5 h5 = new Handler5();
        disruptor.handleEventsWith(h1, h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2, h5).handleEventsWith(h3);

        // 3. 启动disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);

        long beginTime = System.currentTimeMillis();
        es.submit(new TradePublisher(disruptor, latch));

        latch.await();
        disruptor.shutdown();
        es.shutdown();

        System.out.println("总耗时：" + (System.currentTimeMillis() - beginTime));
    }
}
