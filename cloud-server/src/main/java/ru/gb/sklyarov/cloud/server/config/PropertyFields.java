package ru.gb.sklyarov.cloud.server.config;

public enum PropertyFields {
    DB_URL("db.url"), DB_USER("db.user"), DB_PASSWORD("db.password"), SERVER_PORT("server_port");

    private final String field;

    PropertyFields(String field){
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
