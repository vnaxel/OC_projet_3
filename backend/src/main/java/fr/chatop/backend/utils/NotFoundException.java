package fr.chatop.backend.utils;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
