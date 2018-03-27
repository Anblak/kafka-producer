package org.kafka.producer;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String ownersIP;
    private LocalDateTime sentTime;
    private String timestamp;

    public Message(int id, String ownersIP, LocalDateTime sentTime, String timestamp) {
        this.id = id;
        this.ownersIP = ownersIP;
        this.sentTime = sentTime;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnersIP() {
        return ownersIP;
    }

    public void setOwnersIP(String ownersIP) {
        this.ownersIP = ownersIP;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
