package com.poo.projeto.SmartHouse.Exceptions;

public class DeviceDoesntExistException extends Exception{
    public DeviceDoesntExistException(String message) {
        super(message);
    }
}
