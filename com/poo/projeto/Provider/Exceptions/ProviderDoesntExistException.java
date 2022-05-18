package com.poo.projeto.Provider.Exceptions;

public class ProviderDoesntExistException extends  Exception{
    public ProviderDoesntExistException(String msg){
        super(msg);
    }
}
