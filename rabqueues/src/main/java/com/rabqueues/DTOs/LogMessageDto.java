package com.rabqueues.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogMessageDto {


    private String level;
    private String message;
    private String timestamp;
    private String fullMessage; // Campo adicional para almacenar el mensaje completo con timestamp
    
    // Getters y Setters


}