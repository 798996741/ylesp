package com.yulun.entity;

public class Client {
    private String url;
    private String userAccount;
    private String password;
    private String ecName;
    private Client client;
    public Client() {
    }
    private static Client instance= new Client();
    public static Client client(){
        return instance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "url='" + url + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", password='" + password + '\'' +
                ", ecName='" + ecName + '\'' +
                ", client=" + client +
                '}';
    }

    public Client(String url, String userAccount, String password, String ecName, Client client) {
        this.url = url;
        this.userAccount = userAccount;
        this.password = password;
        this.ecName = ecName;
        this.client = client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static Client getInstance() {
        return instance;
    }

    public static void setInstance(Client instance) {
        Client.instance = instance;
    }
}
