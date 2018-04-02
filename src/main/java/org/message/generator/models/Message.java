package org.message.generator.models;

public class Message {
    private int id;
    private String clientIP;
    private int companyId;
    private String sentTime;
    private String uuid;
    private String requestURL;
    private String responseCode;
    private int fileSize;
    private ClientLocation client_location;
    private String browser;

    public Message(int id, String clientIP, int companyId, String sentTime, String uuid, String requestURL,
                   String responseCode, int fileSize, ClientLocation coordinates, String browser) {
        this.id = id;
        this.clientIP = clientIP;
        this.companyId = companyId;
        this.sentTime = sentTime;
        this.uuid = uuid;
        this.requestURL = requestURL;
        this.responseCode = responseCode;
        this.fileSize = fileSize;
        this.client_location = coordinates;
        this.browser = browser;
    }

    public int getId() {
        return id;
    }

    public Message setId(int id) {
        this.id = id;
        return this;
    }

    public String getClientIP() {
        return clientIP;
    }

    public Message setClientIP(String clientIP) {
        this.clientIP = clientIP;
        return this;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Message setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getSentTime() {
        return sentTime;
    }

    public Message setSentTime(String sentTime) {
        this.sentTime = sentTime;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Message setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public Message setRequestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public Message setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public int getFileSize() {
        return fileSize;
    }

    public Message setFileSize(int fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public ClientLocation getClient_location() {
        return client_location;
    }

    public Message setClient_location(ClientLocation client_location) {
        this.client_location = client_location;
        return this;
    }

    public String getBrowser() {
        return browser;
    }

    public Message setBrowser(String browser) {
        this.browser = browser;
        return this;
    }
}
