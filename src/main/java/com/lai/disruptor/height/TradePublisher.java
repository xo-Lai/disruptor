package com.lai.disruptor.height;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Weifeng.Lai
 * @Title: TradePublisher
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 22:12
 */
public class TradePublisher implements Runnable {
    private Disruptor<Trade> disruptor;
    private CountDownLatch latch;
    private static int PUBLISH_COUNT = 10;

    public TradePublisher(Disruptor<Trade> disruptor,CountDownLatch countDownLatch) {
        this.disruptor = disruptor;
        this.latch = countDownLatch;
    }

    @Override
    public void run() {
        // 提交任务
//        for (int i = 0; i < PUBLISH_COUNT; i++) {
//
//        }
        disruptor.publishEvent(new TradeEventTranslator());
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade> {
    private Random random = new Random();


    private void generateTrade(Trade event) {
        event.setPrice(random.nextDouble() * 9999);
    }

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateTrade(event);
    }
}