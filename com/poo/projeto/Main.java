package com.poo.projeto;

import com.poo.projeto.Community.CommunityApp;

public class Main {
    public static void main(String[] args) {
        CommunityApp community = new CommunityApp();
        Controller controller = new Controller(community);
        View view = new View(controller);
        view.run();
    }
}
