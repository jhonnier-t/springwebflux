package com.test.softka.utils.enums;

public enum EventEnum {
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT");

    private final String valor;

    EventEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}