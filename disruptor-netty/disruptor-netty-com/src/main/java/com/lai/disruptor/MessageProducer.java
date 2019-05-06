package com.lai.disruptor;

import com.lai.entity.TranslatorData;
import com.lai.entity.TranslatorDataWrapper;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Weifeng.Lai
 * @Title: MessageProducer
 * @Package com.lai.disruptor
 * @Description: ${todo}
 * @date 2019/5/6 20:45
 */
public  class MessageProducer{
    private String producerId;

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWrapper> ringBuffer) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWrapper wrapper = ringBuffer.get(sequence);
            wrapper.setData(data);
            wrapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
