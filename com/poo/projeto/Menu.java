package com.poo.projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<String> options;
    private int op;

    public Menu() {
        this.options = new ArrayList<>();
        this.op = -1;
    }

    public Menu(Menu menu) {
        this.setOptions(menu.options);
        this.setOp(menu.op);
    }

    public Menu(String[] options) {
        this.setOptions(Arrays.asList(options.clone()));
        this.setOp(-1);
    }

    public void execute() {
        do {
            showMenu();
            this.op = readOption();
        }while(this.op == -1);
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
        return scanner.nextInt();
    }

    public List<String> getOptions() {
        return new ArrayList<>(this.options);
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
