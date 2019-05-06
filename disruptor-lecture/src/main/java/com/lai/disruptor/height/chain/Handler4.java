package com.lai.disruptor.height.chain;

import com.lmax.disruptor.EventHandler;


/**
 * @author Weifeng.Lai
 * @Title: Handler1
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 23:11
 */
public class Handler4 implements EventHandler<Trade> {

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

        System.out.println("handler 4 : SET PRICE");
        event.setPrice(17.0);
    }


}
