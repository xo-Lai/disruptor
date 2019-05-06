package com.lai.quickstart;


import com.lmax.disruptor.EventHandler;

import java.nio.ByteBuffer;

/**
 * @author Weifeng.Lai
 * @Title: OrderEventHandler
 * @Package com.lai.quickstart
 * @Description: ${todo}
 * @date 2019/5/1 15:29
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者：" + orderEvent.getValue());
    }

}
