package com.poo.projeto;

import java.util.*;

public class Menu {

    /** Functional interface para handlers. */
    public interface Handler {  // método de tratamento
        public void execute();
    }

    /** Functional interface para pré-condições. */
    public interface PreCondition {  // Predicate ?
        public boolean validate();
    }

    private List<String> options;
    private List<Handler> handlers;
    private List<PreCondition> preConditions;
    private Map<String, Menu> menuMap;

    public Menu() {
        this.options = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.preConditions = new ArrayList<>();
    }

    public Menu(Menu menu) {
        this.setOptions(menu.options);
        this.setHandlers(menu.handlers);
        this.setPreConditions(menu.preConditions);
    }

    public Menu(String[] options) {
        this.setOptions(Arrays.asList(options.clone()));
        this.preConditions = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.options.forEach(s -> {
            this.preConditions.add(()->true);
            this.handlers.add(() -> System.out.println("Opção não implementada."));
        });
    }

    public void execute() {
        int op;
        do {
            showMenu();
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.preConditions.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        }while(op == -1);
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
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt());
        int read = scanner.nextInt();
        return read>=0 && read<this.options.size() ? read : -1;
    }

    public List<String> getOptions() {
        return new ArrayList<>(this.options);
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public List<PreCondition> getPreConditions() {
        return preConditions;
    }

    public void setPreConditions(List<PreCondition> preConditions) {
        this.preConditions = preConditions;
    }
}
