package com.poo.projeto;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    private String fileName;
    public View(){
        this.fileName = "";
    }

    public View(String fileName){
        this.fileName = fileName;
    }

    public View(View view){
        this.fileName = view.getFileName();
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

    public List<String> readLog() {
        List<String> list;
        try {
            list = Files.readAllLines(Paths.get(this.fileName), StandardCharsets.UTF_8);
        }catch (IOException exception){
            System.out.println("Error in IO");
            exception.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }

}
