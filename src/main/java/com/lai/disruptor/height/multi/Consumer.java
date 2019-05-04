package com.lai.disruptor.height.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Weifeng.Lai
 * @Title: Consumer
 * @Package com.lai.disruptor.height.multi
 * @Description: ${todo}
 * @date 2019/5/4 10:42
 */
public class Consumer implements WorkHandler<Order> {
    private String consumeId;
    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String consumeId) {
        this.consumeId = consumeId;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.out.println("当前消费者" + this.consumeId + "消费信息Id：" + event.getId());
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
