package com.lai.entity;

import java.io.Serializable;

/**
 * @author Weifeng.Lai
 * @Title: TranslatorData
 * @Package com.lai.entity
 * @Description: ${todo}
 * @date 2019/5/6 15:56
 */
public class TranslatorData implements Serializable {

    private String id;
    private String name;
    //传输消息体内容
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
