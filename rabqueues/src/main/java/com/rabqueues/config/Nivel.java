package com.rabqueues.config;

import java.util.Arrays;
import java.util.Optional;

public enum Nivel {
    INFO,
    WARNING,
    ERROR;

    public static Optional<Nivel> desde(String texto){
        return Arrays.stream(values())
                .filter(nivel -> nivel.name().equalsIgnoreCase(texto))
                .findFirst();

    }

    public String routingKey() {
        return name();
    }

}
