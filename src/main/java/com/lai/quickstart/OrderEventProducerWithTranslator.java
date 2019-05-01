package com.lai.quickstart;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Weifeng.Lai
 * @Title: OrderEventProducerWithTranslator
 * @Package com.lai.quickstart
 * @Description: ${todo}
 * @date 2019/5/1 17:10
 */
public class OrderEventProducerWithTranslator {

    private final RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducerWithTranslator(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<OrderEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<OrderEvent, ByteBuffer>() {

                /**
                 * Translate a data representation into fields set in given event
                 *
                 * @param event    into which the data should be translated.
                 * @param sequence that is assigned to event.
                 * @param arg0     The first user specified argument to the translator
                 */
                @Override
                public void translateTo(OrderEvent event, long sequence, ByteBuffer arg0) {
                    event.setValue(arg0.getLong(0));
                    System.out.println("sequence:"+sequence);
                }
            };

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
