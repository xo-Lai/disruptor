package com.lai.client;


import com.lai.disruptor.MessageProducer;
import com.lai.disruptor.RingBufferWorkerPoolFactory;
import com.lai.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


/**
 * @author Weifeng.Lai
 * @Title: ClientHandler
 * @Package com.lai.client
 * @Description: ${todo}
 * @date 2019/5/6 17:06
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            TranslatorData responseData = (TranslatorData) msg;
//            System.out.println("Client端:id = " + responseData.getId()
//                    + "Name:" + responseData.getName()
//                    + "Msg:" + responseData.getMessage());
//        } finally {
//            //用完缓存,进行释放
//            ReferenceCountUtil.release(msg);
//        }

        TranslatorData responseData = (TranslatorData) msg;
        String ConsumerClientId = "ClientForNetty:001";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(ConsumerClientId);
        messageProducer.onData(responseData, ctx);
    }
}
