package com.lai.disruptor;

import com.lai.entity.TranslatorDataWrapper;
import com.lmax.disruptor.WorkHandler;

/**
 * @author Weifeng.Lai
 * @Title: MessageConsumer
 * @Package com.lai.disruptor
 * @Description: ${todo}
 * @date 2019/5/6 20:45
 */
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWrapper> {
    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    private String consumerId;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
