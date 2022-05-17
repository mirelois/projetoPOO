package com.poo.projeto.SmartHouse;

public class DeviceDoesntExistException extends Exception{
    public DeviceDoesntExistException(String message) {
        super(message);
    }
}
