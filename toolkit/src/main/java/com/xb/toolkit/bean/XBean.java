package com.xb.toolkit.bean;


/**
 * 所有父类的实体bean
 */
public class XBean {
    private String code;
    private String message;
    private String xCacheUrl;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "XBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
