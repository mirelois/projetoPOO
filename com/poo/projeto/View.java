package com.poo.projeto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    public void executeMenuByName(String name, String[] args) {
        Menu menu = this.menus.get(name);
        if (menu != null)
            menu.execute(args);
    }

    public Menu createStartMenu() {
        return  new Menu(
                "startMenu",
                new String[]{"Carregar Ficheiro Log (Texto)", "Carregar Ficheiro Log (Objetos)", "Começar Simulação sem Ficheiro", "Exit"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Introduza nome do ficheiro de texto");
                            String filename = is.nextLine();
                            List<String> lines = readLog(filename);
                            this.controller.parser(lines);
                            this.executeMenuByName("simulationMenu", new String[]{});
                            return 0;
                        },
                        (args) -> {
                            System.out.println("Introduza nome do ficheiro de objetos");
                            String filename = is.nextLine();
                            this.controller.parseObjectFile(filename);
                            this.executeMenuByName("simulationMenu", new String[]{});
                            return 0;
                        },
                        (args) -> {
                            this.executeMenuByName("simulationMenu", new String[]{});
                            return 0;
                        },
                        (args) -> {
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
                            "Avançar dias", "Menu: Impressão de Estatísticas", "Gravar estado", "Sair da simulação"},
                    new Menu.Handler[]{
                            (args) -> {
                                this.controller.loadAutomaticActions();
                                this.executeMenuByName("automaticSimulationMenu", new String[]{});
                                return 1;
                            },
                            (args) -> {
                                this.executeMenuByName("alterSimulationDetailsMenu", new String[]{});
                                return 1;
                            },
                            (args) -> {
                                int days;
                                do {
                                    System.out.println("Quantos dias pretende avançar?");
                                }while(!is.hasNextInt());
                                days = is.nextInt();
                                this.controller.advanceDays(days);
                                return 1;
                            },
                            (args) -> {
                                this.executeMenuByName("printMenu", new String[]{});
                                return 1;
                            },
                            (args) -> {
                                this.controller.saveState();
                                return 1;
                            },
                            (args) -> {
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
                        (args) -> {
                            System.out.println("Quantos ciclos?");
                            if (is.hasNextInt()) {
                                this.controller.advanceXCicles(is.nextInt());
                                return this.controller.isAutomaticSimulationOver() ? 0 : 1;
                            } else {
                                System.out.println("Número inválido.");
                                return 1;
                            }
                        },
                        (args) -> {
                            System.out.println("Quantos ciclos?");
                            if (is.hasNextInt()) {
                                this.controller.advanceXActions(is.nextInt());
                                return this.controller.isAutomaticSimulationOver() ? 0 : 1;
                            } else {
                                System.out.println("Número inválido.");
                                return 1;
                            }
                        },
                        (args) -> {
                            this.controller.advanceFullAutomaticSimulation();
                            return 0;
                        },
                        (args) -> {
                            this.executeMenuByName("printMenu", new String[]{});
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
                new String[]{"Imprime Tudo", "Imprime Casa", "Imprime Fornecedor", "Casa que mais gastou",
                        "Fornecedor com maior volume de faturação", "Faturas emitidas por um fornecedor",
                        "Oredenação dos maiores consumidores de energia", "Menu Anterior"},
                new Menu.Handler[]{
                        (args)->{
                            System.out.println(this.controller.printAll());
                            return 1;
                        },
                        (args)->{
                            System.out.println("Introduza nome da casa:");
                            String houseName = is.nextLine();
                            System.out.println(this.controller.printHouse(houseName));
                            return 1;
                        },
                        (args)->{
                            System.out.println("Introduza nome do fornecedor:");
                            String providerName = is.nextLine();
                            System.out.println(this.controller.printProvider(providerName));
                            return 1;
                        },
                        (args)->{
                            this.controller.
                        },
                        (args)->0,
                        (args)->0,
                        (args)->0,
                        (args)->0
                },
                new Menu.PreCondition[]{
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true,
                        ()->true
                });
    }

    public Menu createAlterSimulationDetailsMenu() {
        return  new Menu("alterSimulationDetails",
                new String[]{"Alterar Detalhes Casa", "Alterar Detalhes Fornecedor", "Adicionar Fornecedor", "Adicionar Casa", "Menu anterior"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Introduza nome do fornecedor:");
                            String providerName = is.nextLine();
                            if (this.controller.existsProvider(providerName)) {
                                this.executeMenuByName("alterSimulationDetailsHouse", new String[]{providerName});
                            } else {
                                System.out.println("Nome da casa inválida");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza a morada da casa:");
                            String houseAddress = is.nextLine();
                            if (this.controller.existsSmartHouse(houseAddress)) {
                                this.executeMenuByName("alterSimulationDetails", new String[]{houseAddress});
                            } else {
                                System.out.println("Nome da casa inválida");
                            }
                            return 1;
                        },
                        (args) -> {
                            addProviderView();
                            return 1;
                        },
                        (args) -> {
                            addSmartHouseView();
                            return 1;
                        },
                        (args) -> 0
                },
                new Menu.PreCondition[]{
                        () -> this.controller.isSimulationEmptyProvider(),
                        () -> this.controller.isSimulationEmptyHouse(),
                        () -> true,
                        () -> true,
                        () -> true
                });
    }

    public Menu createAlterSimulationDetailsHouseMenu() {
        return  new Menu("alterSimulationDetailsHouse",
                new String[]{"Adiciona SmartDevice", "Adiciona Divisão", "Ligar/Desligar SmartDevice",
                        "Ligar/Desligar Divisão", "Mudar de Fornecedor", "Menu Anterior"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Introduza o nome da divisão onde adicionar.");
                            String division = is.nextLine();
                            if (this.controller.existsDivision(args[0], division)) {
                                this.addSmartDeviceView(args[0], division);
                            } else {
                                System.out.println("Nome inválido");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome da divisão.");
                            String divisionName = is.nextLine();
                            this.addDivisionView(args[0], divisionName);
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o id do SmartDevice:");
                            String smartDevice = is.nextLine();
                            if (this.controller.existsSmartDevice(args[0], smartDevice)) {
                                System.out.println("Pretende ligar(y) ou desligar(n)?");
                                String response = is.nextLine();
                                if (response.equals("y")) {
                                    this.controller.turnSmartDeviceON(args[0], smartDevice);
                                } else if (response.equals("n")) {
                                    this.controller.turnSmartDeviceOFF(args[0], smartDevice);
                                }
                            } else {
                                System.out.println("ID inválido");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome da Divisão:");
                            String division = is.nextLine();
                            if (this.controller.existsDivision(args[0], division)) {
                                System.out.println("Digite ON para ligar, OFF para desligar");
                                String response = is.nextLine();
                                if (response.equals("ON")) {
                                    this.controller.turnONDivision(args[0], division);
                                } else {
                                    this.controller.turnOFFDivision(args[0], division);
                                }
                            } else {
                                System.out.println("Nome inválido");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome do Fornecedor");
                            String provider = is.nextLine();
                            if (this.controller.existsProvider(provider)) {
                                this.controller.changeProvider(args[0], provider);
                            } else {
                                System.out.println("Nome inválido");
                            }
                            return 1;
                        },
                        (args) -> 0
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true
                });
    }

    public Menu createAlterSimulationDetailsProviderMenu() {
        return  new Menu("alterSimulationDetailsProvider",
                new String[]{"Mudar de Algoritmo", "Mudar Valor de desconto", "Menu Anterior"},
                new Menu.Handler[]{
                        args -> {
                            this.executeMenuByName("alterProviderAlgorithmMenu", args);
                            return 1;
                        },
                        args -> {
                            System.out.println("Introduza o novo fator de desconto em percentagem.");
                            if (is.hasNextInt()) {
                                int discountFactor = is.nextInt();
                                this.controller.changeDiscountFactor(args[0], discountFactor);
                            } else {
                                System.out.println("Valor inválido.");
                            }
                            return 1;
                        },
                        args -> 0
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true
                });
    }

    public Menu createAlterProviderAlgorithmMenu() {
        return  new Menu("alterProviderAlgorithmMenu",
                new String[]{"Algoritmo 1", "Algoritmo 2"},
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
