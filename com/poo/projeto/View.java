package com.poo.projeto;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class View {

    private static final Scanner is = new Scanner(System.in);
    private Controller controller;
    private Map<String, Menu> menus;

    public View(Controller controller){
        this.setController(controller);
        this.menus = new HashMap<>();

        this.addMenu(createStartMenu());
        this.addMenu(createSimulationMenu());
        this.addMenu(createAutomaticSimulationMenu());
        this.addMenu(createPrintMenu());
        this.addMenu(createAlterSimulationDetailsMenu());
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
                new String[]{"Carregar Ficheiro Log (Texto)", "Carregar Ficheiro Log (Objetos)", "Começar Simulação sem Ficheiro", "Exit"},
                new Menu.Handler[]{
                        () -> {
                            System.out.println("Introduza nome do ficheiro de texto");
                            String filename = is.nextLine();
                            List<String> lines = readLog(filename);
                            this.controller.parser(lines);
                            this.executeMenuByName("simulationMenu");
                            return 0;
                        },
                        () -> {
                            System.out.println("Introduza nome do ficheiro de objetos");
                            String filename = is.nextLine();
                            this.controller.parseObjectFile(filename);
                            this.executeMenuByName("simulationMenu");
                            return 0;
                        },
                        () -> {
                            this.executeMenuByName("simulationMenu");
                            return 0;
                        },
                        () -> {
                            System.out.println("Bye bye");
                            return 0;
                        }
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true
                }
                );
    }

    public Menu createSimulationMenu() {
        return  new Menu("simulationMenu",
                    new String[]{"Carregar Ficheiro das Ações Automáticas", "Menu: Alterar detalhes da simulação",
                            "Avançar dias", "Menu: Impressão", "Gravar estado", "Sair da simulação"},
                    new Menu.Handler[]{
                            () -> {
                                this.controller.loadAutomaticActions();
                                this.executeMenuByName("automaticSimulationMenu");
                                return 1;
                            },
                            () -> {
                                this.executeMenuByName("alterSimulationDetailsMenu");
                                return 1;
                            },
                            () -> {
                                int dias;
                                do {
                                    System.out.println("Quantos dias pretende avançar?");
                                }while(!is.hasNextInt());
                                dias = is.nextInt();
                                this.controller.advanceDias(dias);
                                return 1;
                            },
                            () -> {
                                this.executeMenuByName("printMenu");
                                return 1;
                            },
                            () -> {
                                this.controller.saveState();
                                return 1;
                            },
                            () -> {
                                System.out.println("Goodbye!");
                                return 0;
                            }
                    },
                    new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true
                    }
                );
    }

    public Menu createAutomaticSimulationMenu() {
        return  new Menu("automaticSimulationMenu",
                new String[]{"Avançar X Ciclos de Faturação", "Avançar X Ações", "Avançar Fim Simulação Automática", "Menu: Impressão"},
                new Menu.Handler[]{
                        () -> {
                            do {
                                System.out.println("Quantos ciclos?");
                            }while(is.hasNextInt());
                            this.controller.advanceXCicles(is.nextInt());
                            //this.executeMenuByName("automaticSimulationMenu");
                            return this.controller.isSimulationOver() ? 0 : 1;
                        },
                        () -> {
                            do {
                                System.out.println("Quantos ciclos?");
                            }while(is.hasNextInt());
                            this.controller.advanceXActions(is.nextInt());
                            return this.controller.isSimulationOver() ? 0 : 1;
                        },
                        () -> {
                            this.controller.advanceFullSimulation();
                            return 0;
                        },
                        () -> {
                            this.executeMenuByName("printMenu");
                            return 1;
                        }
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> this.controller.isSimulationEmpty()
                });
    }

    public Menu createPrintMenu() {
        return  new Menu("printMenu",
                new String[]{"Imprime Tudo", "Imprime Casa", "Imprime Fornecedor", "Menu Anterior"},
                new Menu.Handler[]{
                        ()->{},
                        ()->{},
                        ()->{},
                        ()->{}
                },
                new Menu.PreCondition[]{
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true
                });
    }

    //TODO ainda inacabado
    public Menu createAlterSimulationDetailsMenu() {
        return  new Menu("alterSimulationDetails",
                new String[]{"Alterar Detalhes Casa", "Alterar Detalhes Fornecedor", "Adicionar Fornecedor", "Adicionar Casa"},
                new Menu.Handler[]{},
                new Menu.PreCondition[]{});
    }

    public Menu createAlterSimulationDetailsHouseMenu() {
        return  new Menu("alterSimulationDetailsHouse",
                new String[]{"Adiciona SmartDevice", "Adiciona Divisão", "Ligar/Desligar SmartDevice", "Ligar/Desligar Divisão", "Mudar de Fornecedor", "Menu Anterior"},
                new Menu.Handler[]{},
                new Menu.PreCondition[]{});
    }

    public Menu createAlterSimulationDetailsProviderMenu() {
        return  new Menu("alterSimulationDetailsProvider",
                new String[]{"Mudar de Algoritmo", "Mudar Valor de desconto", "Menu Anterior"},
                new Menu.Handler[]{},
                new Menu.PreCondition[]{});
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
