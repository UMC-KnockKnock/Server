package com.example.knockknock.exception;

public class NotFoundBoardException extends RuntimeException{
    public NotFoundBoardException(String message) {
        super(message);
    }
}
