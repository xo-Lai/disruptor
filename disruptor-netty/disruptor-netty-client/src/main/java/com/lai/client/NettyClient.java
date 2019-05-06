package com.lai.client;

import com.lai.codec.MarshallingCodecFactory;
import com.lai.entity.TranslatorData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * @author Weifeng.Lai
 * @Title: NettyClient
 * @Package com.lai.client
 * @Description: ${todo}
 * @date 2019/5/6 17:03
 */
public class NettyClient {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 18899;
    private Channel channel;
    //1.需要创建两个线程组：一个用于接受网络请求，一个用于处理实际业务逻辑
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture cf;

    public NettyClient() {
        this.connect(IP, PORT);
    }

    private void connect(String ip, int port) {

        //2 辅助类(注意Client 和 Server 不一样)
        Bootstrap bootstrap = new Bootstrap();
        try {

            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ClientHandler());
                        }
                    });
            //绑定端口，同步等等请求连接
            this.cf = bootstrap.connect(ip, port).sync();
            System.err.println("Client connected...");

            //接下来就进行数据的发送, 但是首先我们要获取channel:
            this.channel = cf.channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendData() {
        for (int i = 0; i < 100; i++) {
            TranslatorData translatorData = new TranslatorData();
            translatorData.setId("请求Id:" + i);
            translatorData.setName("请求Name:" + i);
            translatorData.setMessage("请求Msg:" + i);

            this.channel.writeAndFlush(translatorData);
        }
    }

    public void close() throws Exception {
        cf.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
        System.out.println("Client shutDown...");
    }
}
