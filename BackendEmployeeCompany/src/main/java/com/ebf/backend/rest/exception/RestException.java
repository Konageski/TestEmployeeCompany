package com.ebf.backend.rest.exception;

public class RestNotFoundException extends RuntimeException {

    public RestNotFoundException(String name, Long id) {
        super("Could not find " + name + "   Id: " + id);
    }
}
