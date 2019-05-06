package com.lai.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * @author Weifeng.Lai
 * @Title: OrderEventFactory
 * @Package com.lai.quickstart
 * @Description: ${todo}
 * @date 2019/5/1 15:27
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        // 这个方法就是为了返回空的数据对象（Event）
        return new OrderEvent();
    }
}
