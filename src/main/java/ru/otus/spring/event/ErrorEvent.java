package ru.otus.spring.event;

import org.springframework.context.ApplicationEvent;

public class ErrorEvent extends ApplicationEvent {
    private String message;

    public ErrorEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    String getMessage() {
        return this.message;
    }
}
