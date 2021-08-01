package ru.gb.sklyarov.cloud.client.config;

public enum PropertyFields {
    SERVER_HOST("server_host"), SERVER_PORT("server_port");

    private final String field;

    PropertyFields(String field){
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
