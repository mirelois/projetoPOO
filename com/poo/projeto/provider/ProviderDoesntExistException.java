package com.poo.projeto.provider;

public class ProviderDoesntExistException extends  Exception{
    public ProviderDoesntExistException(String msg){
        super(msg);
    }
}
