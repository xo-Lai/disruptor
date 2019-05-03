package com.lai.disruptor.height;

import com.lmax.disruptor.EventHandler;


/**
 * @author Weifeng.Lai
 * @Title: Handler1
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 23:11
 */
public class Handler5 implements EventHandler<Trade> {

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

        System.out.println("handler 5 : GET PRICE: " + event.getPrice());
        event.setPrice(event.getPrice() + 3.0);
    }


}
