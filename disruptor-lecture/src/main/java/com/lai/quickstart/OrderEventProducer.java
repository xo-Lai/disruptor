package com.lai.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Weifeng.Lai
 * @Title: OrderEventProducer
 * @Package com.lai.quickstart
 * @Description: Legacy
 * @date 2019/5/1 16:48
 */

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer bb) {
        // 1. 在生产者发送消息的时候，首先需要从我们ringBugger获取一个可用序号
        long sequence = ringBuffer.next();
        try {

            // 2. 根据序号找到元素，找到具体的OrderEvent 元素，此时的元素获取的OrderEvent对象是一个空的对象
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 3. 进行实际赋值
            orderEvent.setValue(bb.getLong(0));
        } finally {
            // 4. 发布序号
            ringBuffer.publish(sequence);
        }


    }
}
