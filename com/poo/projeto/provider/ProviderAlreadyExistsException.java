package com.poo.projeto.provider;

public class ProviderAlreadyExistsException extends Exception{
    public ProviderAlreadyExistsException(String msg){
        super(msg);
    }
}
