package com.example.study.exception;

public class CustomException extends RuntimeException {

    public CustomException(String msg){
        super(msg);
    }

    public static CustomException create(String msg){
        return new CustomException(msg);
    }
}
