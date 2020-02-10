package com.demo.transfer.domain.model;

/**
 * description: Notification <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class Notification {
    /**
     * 通知人姓名
     */
    private String name;
    /**
     * 通知人手机号
     */
    private String phoneNumber;
    /**
     * 通知类容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createdTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
