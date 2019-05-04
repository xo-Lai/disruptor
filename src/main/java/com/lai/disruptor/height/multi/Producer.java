package com.lai.disruptor.height.multi;

import com.lai.quickstart.OrderEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Weifeng.Lai
 * @Title: Producer
 * @Package com.lai.quickstart
 * @Description: ${todo}
 * @date 2019/5/1 17:10
 */
public class Producer {

    private final RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }



    public void sendData(String data) {
        long sequnce = ringBuffer.next();

        try {
            Order order = ringBuffer.get(sequnce);
            order.setId(data);
        } finally {
            ringBuffer.publish(sequnce);
        }
    }
}
