package com.lai.entity;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Weifeng.Lai
 * @Title: TranslatorDataWrapper
 * @Package com.lai.entity
 * @Description: ${todo}
 * @date 2019/5/6 15:59
 */
public class TranslatorDataWrapper {
    private TranslatorData data;

    private ChannelHandlerContext ctx;

    public TranslatorData getData() {
        return data;
    }

    public void setData(TranslatorData data) {
        this.data = data;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

}
