package com.lai.client;

import com.lai.disruptor.MessageConsumer;
import com.lai.entity.TranslatorData;
import com.lai.entity.TranslatorDataWrapper;
import io.netty.util.ReferenceCountUtil;

/**
 * @author Weifeng.Lai
 * @Title: MessageConsumerForClient
 * @Package com.lai.client
 * @Description: ${todo}
 * @date 2019/5/6 21:40
 */
public class MessageConsumerForClient extends MessageConsumer {
    public MessageConsumerForClient(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData responseData = event.getData();
        try {

            System.out.println("Client端:id = " + responseData.getId()
                    + "Name:" + responseData.getName()
                    + "Msg:" + responseData.getMessage());
        } finally {
            //用完缓存,进行释放
            ReferenceCountUtil.release(responseData);
        }
    }
}
