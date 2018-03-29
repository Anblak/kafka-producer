package org.kafka.producer.models;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String ownersIP;
    private LocalDateTime sentTime;
    private String uuid;
    private String requestURL;
    private String responseCode;
    private String fileSize;
    private Coordinates coordinates;
    private String browser;

    public Message(int id, String ownersIP, LocalDateTime sentTime, String uuid, String requestURL, String responseCode,
                   String fileSize, Coordinates coordinates, String browser) {
        this.id = id;
        this.ownersIP = ownersIP;
        this.sentTime = sentTime;
        this.uuid = uuid;
        this.requestURL = requestURL;
        this.responseCode = responseCode;
        this.fileSize = fileSize;
        this.coordinates = coordinates;
        this.browser = browser;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
