package com.cern.exception;

public class TestException  extends RuntimeException{
    private int code;
    public TestException(int code,String msg){
        super(msg);
        this.code = code;
    }
    public int getCode(){return code;}
}
