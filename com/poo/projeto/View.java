package com.poo.projeto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class View {
    private Controller controller;
    private Map<String, Menu> menus;

    public View(Controller controller){
        this.setController(controller);
        this.menus = new HashMap<>();

        this.addMenu(createStartMenu());
        this.addMenu(createSimulationMenu());
        this.addMenu(createAutomaticSimulationMenu());
    }

    public View(View view){
        this.setController(view.controller);
        this.setMenus(view.menus);
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Map<String, Menu> getMenus() {
        return new HashMap<>(this.menus);
    }

    public void setMenus(Map<String, Menu> menus) {
        this.menus = new HashMap<>(menus);
    }
    public void addMenu(Menu menu) {
        this.menus.put(menu.getName(), menu);
    }

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

    public void executeMenuByName(String name) {
        Menu menu = this.menus.get(name);
        if (menu != null)
            menu.execute();
    }

    public Menu createStartMenu() {
        return  new Menu(
                "startMenu",
                new String[]{"Carregar Ficheiro Log (Texto)", "Carregar Ficheiro Log (Objetos)", "Começar Simulação sem Ficheiro", "Exit"}
                );
    }

    public Menu createSimulationMenu() {
        return  new Menu("simulationMenu",
                new String[]{"Carregar Ficheiro das Ações Automáticas", "Menu: Alterar detalhes da simulação", "Avançar dias", "Menu: Impressão",
                        "Gravar estado", "Sair da simulação"},
                new Menu.Handler[]{
                    () -> {
                        this.controller.loadAutomaticActions();
                        this.executeMenuByName("simulationMenu");
                    },
                    () -> this.executeMenuByName("alterSimulationDetails"),
                    () -> {
                        int dias;
                        do {
                            System.out.println("Quantos dias pretende avançar?");
                            dias = Menu.is.nextInt();
                        }while(!Menu.is.hasNextInt());
                        this.controller.advanceDias(dias);
                    },
                    () -> this.executeMenuByName("printMenu"),
                    () -> this.controller.saveState(),
                    () -> System.out.println("Goodbye!")
                },
                new Menu.PreCondition[]{});
    }

    public Menu createAutomaticSimulationMenu() {
        return  new Menu("automaticSimulationMenu",
                new String[]{"Avançar X Ciclos de Faturação", "Avançar X Ações", "Avançar Fim Simulação Automática", "Menu: Impressão"});
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
