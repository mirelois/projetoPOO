package com.poo.projeto;

import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
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
        this.addMenu(createAddSmartDeviceMenu());
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

    public List<String> readLog(String logFileName) throws IOException{
        return Files.readAllLines(Paths.get(logFileName), StandardCharsets.UTF_8);
    }

    public void executeMenuByName(String name, List<String> args) {
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
                            try {
                                List<String> lines = readLog(filename);
                                this.controller.parser(lines);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
                                //TODO cuidado com exceptions
                                e.printStackTrace();
                                return 1;
                            }
                            this.executeMenuByName("simulationMenu", null);
                            return 0;
                        },
                        (args) -> {
                            System.out.println("Introduza nome do ficheiro de objetos");
                            String filename = is.nextLine();
                            try {
                                this.controller.parseObjectFile(filename);
                            }catch (IOException | ClassNotFoundException e){
                                e.printStackTrace();
                                return 1;
                            }

                            this.executeMenuByName("simulationMenu", null);
                            return 0;
                        },
                        (args) -> {
                            this.executeMenuByName("simulationMenu", null);
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
                    new String[]{"Carregar Ficheiro das Ações Automáticas", "Alterar detalhes da simulação",
                            "Avançar dias", "Menu: Impressão de Estatísticas", "Gravar estado", "Sair da simulação"},
                    new Menu.Handler[]{
                            (args) -> {
                                System.out.println("Introduza o nome do ficheiro das ações automáticas.");
                                String filename = is.nextLine();
                                try {
                                    List<String> lines = this.readLog(filename);
                                    this.controller.parseActions(lines);
                                    this.executeMenuByName("automaticSimulationMenu", null);
                                } catch (DivisionDoesntExistException | DeviceDoesntExistException | NumberFormatException | AddressDoesntExistException | ProviderDoesntExistException | IOException e) {
                                    e.printStackTrace();
                                }
                                return 1;
                            },
                            (args) -> {
                                this.executeMenuByName("alterSimulationDetails", null);
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
                                this.executeMenuByName("printMenu", null);
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
                new String[]{"Avançar X Ciclos de Faturação", "Avançar Fim Simulação Automática", "Menu: Impressão"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Quantos ciclos?");
                            if (is.hasNextInt()) {
                                try {
                                    this.controller.advanceXCicles(is.nextInt());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                is.nextLine();
                                return this.controller.isAutomaticSimulationOver() ? 0 : 1;
                            } else {
                                System.out.println("Número inválido.");
                                return 1;
                            }
                        },
                        (args) -> {
                            try {
                                this.controller.advanceFullAutomaticSimulation();
                            } catch (ProviderAlreadyExistsException | AddressAlreadyExistsException |
                                     AddressDoesntExistException | DeviceDoesntExistException |
                                     ProviderDoesntExistException | DivisionDoesntExistException |
                                     DivisionAlreadyExistsException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        },
                        (args) -> {
                            this.executeMenuByName("printMenu", null);
                            return 1;
                        }
                },
                new Menu.PreCondition[]{
                        () -> true,
                        () -> true,
                        () -> this.controller.isSimulationEmpty()
                });
    }

    public Menu createPrintMenu() {
        return  new Menu("printMenu",
                new String[]{"Imprime Tudo", "Imprime Todos os Fornecedores", "Imprime Todas as Casas", "Imprime Casa", "Imprime Fornecedor", "Casa que mais gastou",
                        "Fornecedor com maior volume de faturação", "Faturas emitidas por um fornecedor",
                        "Ordenação dos maiores consumidores de energia", "Menu Anterior"},
                new Menu.Handler[]{
                        (args)->{ //imprime tudo
                            System.out.println(this.controller.printAll());
                            return 1;
                        },
                        (args)->{ //imprime todos os fornecedores
                            System.out.println(this.controller.printAllProviders());
                            return 1;
                        },
                        (args)->{ //imprime todas as casas
                            System.out.println(this.controller.printAllHouses());
                            return 1;
                        },
                        (args)->{ //imprime casa
                            System.out.println("Introduza a morada da casa:");
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
                            System.out.println("Introduza a data de início (yyyy-MM-dd):");
                            start = is.nextLine();
                            System.out.println("Introduza a data de fim: (yyyy-MM-dd)");
                            end = is.nextLine();
                            try {
                                System.out.println(this.controller.houseWithMostConsumption(start, end));
                            } catch (DateTimeParseException | NoHouseInPeriodException e) {
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
                            System.out.println("Introduza a data de início (yyyy-MM-dd):");
                            start = is.nextLine();
                            System.out.println("Introduza a data de fim (yyyy-MM-dd):");
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
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(houseAddress);
                                this.executeMenuByName("alterSimulationDetailsHouse", arrayList);
                            } else {
                                System.out.println("Nome da casa inválida");
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome do fornecedor:");
                            String providerName = is.nextLine();
                            if (this.controller.existsProvider(providerName)) {
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(providerName);
                                this.executeMenuByName("alterSimulationDetailsProvider", arrayList);
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
                            if (controller.isSimulationEmptyProvider()) {
                                System.out.println("Não há fornecedores para adicionar à casa");
                            } else {
                                addSmartHouseView();
                            }
                            return 1;
                        },
                        (args) -> 0
                },
                new Menu.PreCondition[]{
                        () -> !this.controller.isSimulationEmptyProvider(),
                        () -> !this.controller.isSimulationEmptyHouse(),
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
        }while(!this.controller.existsProvider(provider));
        try {
            this.controller.addSmartHouse(address, name, nif, provider);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(address);
            this.executeMenuByName("alterSimulationDetailsHouse", arrayList);
        } catch (ProviderDoesntExistException | AddressAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    private void addProviderView() {
        System.out.println("Introduza o nome do fornecedor:");
        String name = is.nextLine();
        System.out.println("Introduza o fator de desconto:");
        Double discountFactor = is.nextDouble();
        is.nextLine();
        //TODO adicionar mais coisas ao provider
        try {
            this.controller.addProvider(name, discountFactor);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(name);
            this.executeMenuByName("alterSimulationDetailsProvider", arrayList);
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

                            if (!this.controller.existsDivision(args.get(0), division)) {
                                System.out.println("Divisão não existente.");
                                System.out.println("Deseja criar? (y/n)");
                                String read = is.nextLine();
                                if (read.equals("y")) {
                                    try {
                                        this.controller.addDivision(args.get(0), division);
                                    } catch (AddressDoesntExistException | DivisionAlreadyExistsException e ) {
                                        e.printStackTrace();
                                        return 1;
                                    }
                                } else {
                                    return 1;
                                }
                            }
                            args.add(division);
                            this.executeMenuByName("addSmartDeviceMenu", args);
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o nome da divisão.");
                            String divisionName = is.nextLine();
                            if (this.controller.existsDivision(args.get(0), divisionName)) {
                                System.out.println("Divisão já existente.");
                            } else {
                                try {
                                    this.controller.addDivision(args.get(0), divisionName);
                                } catch (AddressDoesntExistException | DivisionAlreadyExistsException e ) {
                                    e.printStackTrace();
                                }
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o id do SmartDevice:");
                            String smartDevice = is.nextLine();
                            if (this.controller.existsSmartDevice(args.get(0), smartDevice)) {
                                System.out.println("Pretende ligar(y) ou desligar(n)?");
                                String response = is.nextLine();
                                try {
                                    if (response.equals("y")) {
                                        System.out.println("hello");
                                        this.controller.turnSmartDevice(args.get(0), smartDevice, true);
                                    } else if (response.equals("n")) {
                                        this.controller.turnSmartDevice(args.get(0), smartDevice, false);
                                    }
                                } catch (DeviceDoesntExistException e) {
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
                            if (this.controller.existsDivision(args.get(0), division)) {
                                System.out.println("Digite ON para ligar, OFF para desligar");
                                String response = is.nextLine();
                                try {
                                    if (response.equals("ON")) {
                                        this.controller.turnONDivision(args.get(0), division);
                                    } else {
                                        this.controller.turnOFFDivision(args.get(0), division);
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
                                this.controller.changeProvider(args.get(0), provider);
                            } else {
                                System.out.println("Nome inválido");
                            }
                            return 1;
                        },
                        (args) -> {
                            try {
                                System.out.println((this.controller.printHouse(args.get(0))));
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
                                this.controller.changeDiscountFactor(args.get(0), discountFactor);
                            } else {
                                System.out.println("Valor inválido.");
                            }
                            return 1;
                        },
                        (args) -> {
                            try{
                                System.out.println(this.controller.printProvider(args.get(0)));
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
                            this.controller.changeProviderAlgorithm(args.get(0), 1);
                            return 1;
                        },
                        (args) -> {
                            this.controller.changeProviderAlgorithm(args.get(0), 2);
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

    public Menu createAddSmartDeviceMenu() {
        return  new Menu("addSmartDeviceMenu",
                new String[]{"SmartBulb", "SmartCamera", "SmartSpeaker", "Menu Anterior"},
                new Menu.Handler[]{
                        (args) -> {
                            System.out.println("Introduza o tom da SmartBulb");
                            String tone = is.nextLine();
                            System.out.println("Introduza o diâmetro da SmartBulb");
                            String diameter = is.nextLine();
                            System.out.println("Introduza o consumo base da SmartBulb");
                            String baseConsumption = is.nextLine();
                            try {
                                this.controller.addSmartBulb(args.get(0), args.get(1), tone, diameter, baseConsumption);
                            } catch (AddressDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza a resolução (LxA) da SmartCamera");
                            String resolution = is.nextLine();
                            System.out.println("Introduza a dimensão da SmartCamera");
                            String dimension = is.nextLine();
                            System.out.println("Introduza o consumo base da SmartCamera");
                            String baseConsumption = is.nextLine();
                            try {
                                this.controller.addSmartCamera(args.get(0), args.get(1), resolution, dimension, baseConsumption);
                            } catch (AddressDoesntExistException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        },
                        (args) -> {
                            System.out.println("Introduza o volume da SmartSpeaker");
                            String volume = is.nextLine();
                            System.out.println("Introduza a marca da SmartSpeaker");
                            String brand = is.nextLine();
                            System.out.println("Introduza a rádio da SmartBulb");
                            String radio = is.nextLine();
                            System.out.println("Introduza o consumo base da SmartSpeaker");
                            String baseConsumption = is.nextLine();
                            try {
                                this.controller.addSmartSpeaker(args.get(0), args.get(1), volume, brand, radio, baseConsumption);
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
                        () -> true
                });
    }

    public void run() {
        String initialDate;
        do {
            System.out.println("Introduza data inicial válida (YYYY-MM-DD)");
            initialDate = is.nextLine();
        } while (!this.controller.setInitialDate(initialDate));

        executeMenuByName("startMenu", null);
        //Boot
        //Introduzir data inicial
    }

}
