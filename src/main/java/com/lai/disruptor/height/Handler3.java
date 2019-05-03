package com.lai.disruptor.height;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;


/**
 * @author Weifeng.Lai
 * @Title: Handler1
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 23:11
 */
public class Handler3 implements EventHandler<Trade> {

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

        System.out.println("handler 3 : Name:"+ event.getName() + ", ID:" + event.getId() + ",price:" + event.getPrice());
    }


}
