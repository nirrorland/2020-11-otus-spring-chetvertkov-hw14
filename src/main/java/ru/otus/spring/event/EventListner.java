package ru.otus.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.ConsoleIOService;

@Component
public class EventListner {

    private final ConsoleIOService consoleIOService;

    @Autowired
    public EventListner(ConsoleIOService consoleIOService) {
        this.consoleIOService = consoleIOService;
    }

    @EventListener
    public void handleErrorListener(ErrorEvent event) {
        consoleIOService.out(event.getMessage());
    }
}
