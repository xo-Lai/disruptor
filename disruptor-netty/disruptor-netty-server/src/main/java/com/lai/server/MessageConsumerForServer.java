package com.lai.server;

import com.lai.disruptor.MessageConsumer;
import com.lai.entity.TranslatorData;
import com.lai.entity.TranslatorDataWrapper;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Weifeng.Lai
 * @Title: MessageConsumerForServer
 * @Package com.lai.server
 * @Description: ${todo}
 * @date 2019/5/6 21:34
 */
public class MessageConsumerForServer extends MessageConsumer {

    public MessageConsumerForServer(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData requestData = event.getData();
        ChannelHandlerContext ctx = event.getCtx();

        System.out.println("服务端数据:"
                + "ID:" + requestData.getId()
                + "NAME:" + requestData.getName()
                + "MSG:" + requestData.getMessage());

        //写出Response响应数据
        TranslatorData responseData = new TranslatorData();
        responseData.setId("resp:" + requestData.getId());
        responseData.setName("resp:" + requestData.getName());
        responseData.setMessage("resp:" + requestData.getMessage());
        ctx.writeAndFlush(responseData);
    }
}
