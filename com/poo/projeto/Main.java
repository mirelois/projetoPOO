package com.poo.projeto;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        List<String> log = view.readLog();
        Controler controler = new Controler();
        controler.parser(log);
    }
}
