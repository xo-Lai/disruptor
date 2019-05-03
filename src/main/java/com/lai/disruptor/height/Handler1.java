package com.lai.disruptor.height;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;


/**
 * @author Weifeng.Lai
 * @Title: Handler1
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 23:11
 */
public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    /**
     * EventHandler
     *
     * @param event
     * @param sequence
     * @param endOfBatch
     * @throws Exception
     */
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    /**
     * WorkHandler
     *
     * @param event
     * @throws Exception
     */
    @Override
    public void onEvent(Trade event) throws Exception {
        System.out.println("handler 1: Set Name");
        Thread.sleep(1000);
        event.setName("H1");

    }
}
