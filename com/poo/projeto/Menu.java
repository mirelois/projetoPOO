package com.poo.projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu implements Serializable{

    public interface Handler extends Serializable {
        public int execute(List<String> args);
    }

    public interface PreCondition extends Serializable {
        public boolean validate();
    }

    private static Scanner is = new Scanner(System.in);
    private String name;
    private List<String> options;
    private List<Handler> handlers;
    private List<PreCondition> preConditions;
    //private Map<String, Menu> menuMap;

    public Menu() {
        this.name = "";
        this.options = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.preConditions = new ArrayList<>();
        //this.menuMap = new HashMap<>();
    }

    public Menu(Menu menu) {
        this.setName(menu.name);
        this.setOptions(menu.options);
        this.setHandlers(menu.handlers);
        this.setPreConditions(menu.preConditions);
        //this.setMenuMap(menu.menuMap);
    }

    public Menu(String name, String[] options) {
        this.name = name;
        this.setOptions(Arrays.asList(options.clone()));
        this.preConditions = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.options.forEach(s -> {
            this.preConditions.add(()->true);
            this.handlers.add((args) -> {
                System.out.println("Opção não implementada.");
                return 0;
            });
        });
    }

    public Menu(String name, String[] options, Handler[] handlers, PreCondition[] preConditions) {
        this.name = name;
        this.setOptions(Arrays.asList(options.clone()));
        this.setHandlers(Arrays.asList(handlers.clone()));
        this.setPreConditions(Arrays.asList(preConditions.clone()));
        //this.setMenuMap(menus);
    }

    public void execute(List<String> args) {
        int op, r = -1;
        do {
            showMenu();
            op = readOption();
            if (op > 0) {
                if (this.preConditions.get(op-1).validate()) {
                    r = this.handlers.get(op-1).execute(args);
                } else {
                    System.out.println("Opção indisponível no momento! Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida");
            }
        }while(r != 0);
    }

    public void showMenu() {
        System.out.println(" --- Menu --- \n");
        for (int i = 0; i<options.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(options.get(i));
        }
    }

    public int readOption() {
        while (!is.hasNextInt()) {
            is.nextLine();
        }
        int read = is.nextInt();
        is.nextLine();
        return read>=0 && read<=this.options.size() ? read : -1;
    }

    //public void addMenuMap

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOptions() {
        return new ArrayList<>(this.options);
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public List<Handler> getHandlers() {
        return new ArrayList<>(this.handlers);
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    public List<PreCondition> getPreConditions() {
        return new ArrayList<>(this.preConditions);
    }

    public void setPreConditions(List<PreCondition> preConditions) {
        this.preConditions = new ArrayList<>(preConditions);
    }

    //public Map<String, Menu> getMenuMap() {
    //    return new HashMap<>(this.menuMap);
    //}

    //public void setMenuMap(Map<String, Menu> menuMap) {
    //    this.menuMap = new HashMap<>(menuMap);
    //}

    public Menu clone() {
        return new Menu(this);
    }
}
