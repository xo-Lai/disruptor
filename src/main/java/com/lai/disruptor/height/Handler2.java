package com.lai.disruptor.height;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;


/**
 * @author Weifeng.Lai
 * @Title: Handler1
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 23:11
 */
public class Handler2 implements EventHandler<Trade> {

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

        System.out.println("handler 2: Set Id");
        Thread.sleep(2000);
        event.setName("H2");
        event.setId(UUID.randomUUID().toString());

    }


}
