package com.poo.projeto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class View {
    private Controller controller;
    private List<Menu> menuMenuHandlerMap;

    //private String fileName;


    public View(Controller controller){
        this.setController(controller);

        //this.fileName = fileName;

    }

    public View(View view){
        this.setController(view.controller);

        //this.fileName = view.getFileName();

    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }



    //public void setFileName(String fileName){
    //    this.fileName = fileName;
    //}

    //public String getFileName(){
    //    return this.fileName;
    //}


    public List<String> readLog(String logFileName) {
        List<String> list;
        try {
            list = Files.readAllLines(Paths.get(logFileName), StandardCharsets.UTF_8);
        }catch (IOException exception){
            System.out.println("Error in IO");
            exception.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }

    public void run() {
        //Boot
        //Introduzir data inicial

        //. Menu Inicial
        //1. Carregar log text -> Menu Simulação
        //2. Carregar log objetos -> Menu Simulação
        //3. Começar Simulação sem Ficheiro -> Menu Simulação
        //4. Exit

        //. Menu Simulação
        //1. Carregar ficheiro ações automáticas -> Menu Simulação Automática
        //2. Menu Mudar Cenas
        //3. Avançar para dia -> Insira Dia para avançar -> Menu Simuçação
        //4. Save -> Insira nome do ficheiro -> Menu Simulação
        //5. Exit -> Save? -> Insira nome do Ficheiro -> byebye
        //6. Menu Print

        //. Menu Simulação Automática
        //1. Avançar X Ciclos de Faturação
        //2. Avançar X Ações
        //3. Avançar Fim Simulação Automática
        //4. Menu Print

        //. Menu "Print"
        //1. Print Total
        //2. Print Casa
        //3. Print Fornecedor
        //4. Return

        //. Menu Mudar Cenas
        //1. Menu Mudar Cena Casa -> Insira moradaCasa
        //2. Menu Mudar Cena Fornecedor -> Insira nomeFornecedor
        //3. Adicionar Fornecedor
        //4. Adicionar Casa
        //5. Return

        //. Menu Mudar Cena Casa
        //1. Adicionar SmartDevice -> Division?
        //2. Adicionar Divison
        //3. Ligar/Desligar SmartDevices
        //4. Ligar/Desligar Division
        //5. Mudar Fornecedor
        //6. Return

        //. Menu Mudar Cena Fornecedor
        //1. Mudar Algoritmo
        //2. Mudar DiscountFactor
        //3. Return
    }

}
