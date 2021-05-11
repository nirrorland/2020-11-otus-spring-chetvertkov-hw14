package ru.otus.spring.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishErrorEvent(final String message) {
        publisher.publishEvent(new ErrorEvent(this, message));
    }
}
