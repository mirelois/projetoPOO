package com.poo.projeto;

public class Main {
    public static void main(String[] args) {
        Community community = new Community();
        Controller controller = new Controller(community);
        View view = new View(controller);
        view.run();
    }
}
