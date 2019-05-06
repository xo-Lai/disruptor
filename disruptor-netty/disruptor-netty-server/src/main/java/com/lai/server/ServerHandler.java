package com.lai.server;

import com.lai.disruptor.MessageProducer;
import com.lai.disruptor.RingBufferWorkerPoolFactory;
import com.lai.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author Weifeng.Lai
 * @Title: ServerHandler
 * @Package com.lai.server
 * @Description: ${todo}
 * @date 2019/5/6 16:38
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TranslatorData requestData = (TranslatorData) msg;
        System.out.println("服务端数据:"
                + "ID:" + requestData.getId()
                + "NAME:" + requestData.getName()
                + "MSG:" + requestData.getMessage());
//
//        //写出Response响应数据
//        TranslatorData responseData = new TranslatorData();
//        responseData.setId("resp:" + requestData.getId());
//        responseData.setName("resp:" + requestData.getName());
//        responseData.setMessage("resp:" + requestData.getMessage());
//        ctx.writeAndFlush(responseData);

        String messageId = "Server:001";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(messageId);
        messageProducer.onData((TranslatorData) msg, ctx);
    }
}
