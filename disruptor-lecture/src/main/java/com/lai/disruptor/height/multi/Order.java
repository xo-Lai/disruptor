package com.lai.disruptor.height.multi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Weifeng.Lai
 * @Title: Trade
 * @Package com.lai.disruptor.height
 * @Description: ${todo}
 * @date 2019/5/3 21:11
 */
public class Order {
    private String id;
    private String name;
    private double price;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
