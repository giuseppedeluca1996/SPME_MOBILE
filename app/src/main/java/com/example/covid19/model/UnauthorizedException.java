package com.example.covid19.model;

public class UnauthorizedException extends  RuntimeException {
    public UnauthorizedException() {
        super("You do not have the necessary permissions");
    }
}
