package com.lai.exception;


import com.lai.entity.TranslatorDataWrapper;
import com.lmax.disruptor.ExceptionHandler;

/**
 * @author Weifeng.Lai
 * @Title: EventExceptionHandler
 * @Package com.lai.exception
 * @Description: ${todo}
 * @date 2019/5/6 21:05
 */
public class EventExceptionHandler implements ExceptionHandler<TranslatorDataWrapper> {
    @Override
    public void handleEventException(Throwable ex, long sequence, TranslatorDataWrapper event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
