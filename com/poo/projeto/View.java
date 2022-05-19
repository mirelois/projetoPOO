package com.poo.projeto;

import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Division;
import com.poo.projeto.SmartHouse.Exceptions.*;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

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
        this.addMenu(createAlterSimulationDetailsHouseMenu());
        this.addMenu(createAlterSimulationDetailsProviderMenu());
        this.addMenu(createAlterProviderAlgorithmMenu());
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
                            try {
                                this.controller.parser(lines);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
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
                                System.out.println("Introduza o nome do ficheiro das ações automáticas.");
                                String filename = is.nextLine();
                                List<String> lines = this.readLog(filename);
                                try {
                                    this.controller.parseActions(lines);
                                    this.executeMenuByName("automaticSimulationMenu", new String[]{});
                                } catch (DivisionDoesntExistException | DeviceDoesntExistException | NumberFormatException | AddressDoesntExistException | ProviderDoesntExistException e) {
                                    e.printStackTrace();
                                }
                                return 1;
                            },
                            (args) -> {
                                this.executeMenuByName("alterSimulationDetails", new String[]{});
                                return 1;
                            },
                            (args) -> {
                                int days;
                                do {
                                    System.out.println("Quantos dias pretende avançar?");
                                }while(!is.hasNextInt());
                                days = is.nextInt();
                                is.nextLine();
                                try {
                                    this.controller.advanceDays(days);
                                } catch (Exception e) {
                                    //TODO ver se faz sentido nomear cada exceção
                                    e.printStackTrace();
                                }
                                return 1;
                            },
                            (args) -> {
                                this.executeMenuByName("printMenu", new String[]{});
                                return 1;
                            },
                            (args) -> {
                                System.out.println("Introduza o nome de ficheiro para guardar.");
                                String filename = is.nextLine();
                                try {
                                    this.controller.saveState(filename);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
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
                                is.nextLine();
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
                                is.nextLine();
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
                        "Ordenação dos maiores consumidores de energia", "Menu Anterior"},
                new Menu.Handler[]{
                        (args)->{ //imprime tudo
                            System.out.println(this.controller.printAll());
                            return 1;
                        },
                        (args)->{ //imprime casa
                            System.out.println("Introduza nome da casa:");
                            String houseName = is.nextLine();
                            try {
                                System.out.println(this.controller.printHouse(houseName));
                            } catch (AddressDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args)->{ //imprime fornecedor
                            System.out.println("Introduza nome do fornecedor:");
                            String providerName = is.nextLine();
                            try {
                                System.out.println(this.controller.printProvider(providerName));
                            } catch (ProviderDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args)-> { //casa que mais gastou
                            String start, end;
                            System.out.println("Introduza a data de início:");
                            start = is.nextLine();
                            System.out.println("Introduza a data de fim:");
                            end = is.nextLine();
                            try {
                                System.out.println(this.controller.houseWithMostConsumption(start, end));
                            } catch (NoHouseInPeriodException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args)-> { //fornecedor com maior volume de faturação
                            try {
                                System.out.println("O fornecedor com maior volume de faturação é: " + this.controller.providerWithMostInvoicingVolume());
                            } catch (NoProvidersException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args)-> { //faturas de um fornecedor
                            System.out.println("Introduza nome do fornecedor:");
                            String providerName = is.nextLine();
                            try {
                                System.out.println(this.controller.invoicesByProvider(providerName));
                            } catch (ProviderDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args)-> { //ordenação de consumidores de energia
                            String start, end;
                            System.out.println("Introduza a data de início:");
                            start = is.nextLine();
                            System.out.println("Introduza a data de fim:");
                            end = is.nextLine();
                            try {
                                System.out.println(this.controller.orderedHousesByConsumption(start, end));
                            } catch (NoHouseInPeriodException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
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
                            System.out.println("Introduza a morada da casa:");
                            String houseAddress = is.nextLine();
                            if (this.controller.existsSmartHouse(houseAddress)) {
                                this.executeMenuByName("alterSimulationDetailsHouse", new String[]{houseAddress});
                            } else {
                                System.out.println("Nome da casa inválida");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome do fornecedor:");
                            String providerName = is.nextLine();
                            if (this.controller.existsProvider(providerName)) {
                                this.executeMenuByName("alterSimulationDetailsProvider", new String[]{providerName});
                            } else {
                                System.out.println("Nome do fornecedor inválido");
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

    private void addSmartHouseView() {
        String address, name, nif, provider;
        do {
            System.out.println("Introduza morada:");
            address = is.nextLine();
        }while (this.controller.existsSmartHouse(address));
        System.out.println("Introduza o nome do dono:");
        name = is.nextLine();
        System.out.println("Introduza o nif do dono:");
        nif = is.nextLine();
        do {
            System.out.println("Introduza o nome do fornecedor:");
            provider = is.nextLine();
        }while(this.controller.existsProvider(provider));
        try {
            this.controller.addSmartHouse(address, name, nif, provider);
            this.executeMenuByName("alterSimulationDetailsHouse", new String[]{address});
        } catch (ProviderDoesntExistException | AddressAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    private void addProviderView() {
        System.out.println("Introduza o nome do fornecedor:");
        String name = is.nextLine();
        try {
            this.controller.createProvider(name);
            this.executeMenuByName("alterSimulationDetailsProvider", new String[]{name});
        } catch (ProviderAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    public Menu createAlterSimulationDetailsHouseMenu() {
        return  new Menu("alterSimulationDetailsHouse",
                new String[]{"Adiciona SmartDevice", "Adiciona Divisão", "Ligar/Desligar SmartDevice",
                        "Ligar/Desligar Divisão", "Mudar de Fornecedor", "Imprimir", "Menu Anterior"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Introduza o nome da divisão onde adicionar.");
                            String division = is.nextLine();

                            if (this.controller.existsDivision(args[0], division)) {
                                this.addSmartDeviceView(args[0], division);
                            } else {
                                System.out.println("Divisão não existente.");
                                System.out.println("Deseja criar? (y/n)");
                                String read = is.nextLine();
                                if (read.equals("y")) {
                                    try {
                                        this.controller.addDivision(args[0], division);
                                    } catch (AddressDoesntExistException | DivisionAlreadyExistsException e ) {
                                        e.printStackTrace();
                                    }
                                    this.addSmartDeviceView(args[0], division);
                                }
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome da divisão.");
                            String divisionName = is.nextLine();
                            if (this.controller.existsDivision(args[0], divisionName)) {
                                System.out.println("Divisão já existente.");
                            } else {
                                try {
                                    this.controller.addDivision(args[0], divisionName);
                                } catch (AddressDoesntExistException | DivisionAlreadyExistsException e ) {
                                    e.printStackTrace();
                                }
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o id do SmartDevice:");
                            String smartDevice = is.nextLine();
                            if (this.controller.existsSmartDevice(args[0], smartDevice)) {
                                System.out.println("Pretende ligar(y) ou desligar(n)?");
                                String response = is.nextLine();
                                try {
                                    if (response.equals("y")) {
                                        this.controller.turnSmartDevice(args[0], smartDevice, true);
                                    } else if (response.equals("n")) {
                                        this.controller.turnSmartDevice(args[0], smartDevice, false);
                                    }
                                } catch (DeviceDoesntExistException | AddressDoesntExistException e) {
                                    e.printStackTrace();
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
                                try {
                                    if (response.equals("ON")) {
                                        this.controller.turnONDivision(args[0], division);
                                    } else {
                                        this.controller.turnOFFDivision(args[0], division);
                                    }
                                } catch (DivisionDoesntExistException | AddressDoesntExistException e) {
                                    e.printStackTrace();
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
                        (args) -> {
                            try{
                                this.controller.printHouse(args[0]);
                            } catch (AddressDoesntExistException e) {
                                e.printStackTrace();
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
                        () -> true,
                        () -> true
                });
    }

    private void addSmartDeviceView(String address, String division) {
        //TODO receber parametros para chamar o addSmartDevice
        //Esta função tem de ser buffered
        System.out.println("todo");
    }

    public Menu createAlterSimulationDetailsProviderMenu() {
        return  new Menu("alterSimulationDetailsProvider",
                new String[]{"Mudar de Algoritmo", "Mudar Valor de desconto", "Imprimir Fornecedor", "Menu Anterior"},
                new Menu.Handler[]{
                        args -> {
                            this.executeMenuByName("alterProviderAlgorithmMenu", args);
                            return 1;
                        },
                        args -> {
                            System.out.println("Introduza o novo fator de desconto em percentagem.");
                            if (is.hasNextInt()) {
                                Double discountFactor = is.nextDouble();
                                is.nextLine();
                                this.controller.changeDiscountFactor(args[0], discountFactor);
                            } else {
                                System.out.println("Valor inválido.");
                            }
                            return 1;
                        },
                        (args) -> {
                            try{
                                System.out.println(this.controller.printProvider(args[0]));
                            } catch (ProviderDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        args -> 0
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true,
                        () -> true
                });
    }

    public Menu createAlterProviderAlgorithmMenu() {
        return  new Menu("alterProviderAlgorithmMenu",
                new String[]{"Algoritmo 1", "Algoritmo 2", "Regressar"},
                new Menu.Handler[]{
                        (args) -> {
                            this.controller.changeProviderAlgorithm(args[0], 1);
                            return 1;
                        },
                        (args) -> {
                            this.controller.changeProviderAlgorithm(args[0], 2);
                            return 1;
                        },
                        (args) -> 0
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> true
                });
    }

    public void run() {
        executeMenuByName("startMenu", new String[]{});
        //Boot
        //Introduzir data inicial
        //TODO ao carregar do ficheiro log de texto/objetos fazer os adds em buffer como se faz durante a simulação
        //TODO Para colocar a data inicial faz-se um avança data de 0 dias ou algo do género para fazer com que as mudanças surtam efeito
    }

}
