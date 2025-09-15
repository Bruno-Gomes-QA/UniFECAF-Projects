package br.com.fecaf.model;

import java.util.Scanner;

public class Retangulo {

    // Atributos
    double lado1, lado2, area, perimetro;
    String nome;

    public void cadastrarRetangulo () {
        System.out.println("/**************************/");
        System.out.println("/*    Cadastro Retângulo  */");
        System.out.println("/**************************/");
        System.out.print("Informe o lado 1: ");
        Scanner scanner = new Scanner(System.in);
        lado1 = scanner.nextDouble();
        System.out.print("Informe o lado 2: ");
        lado2 = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Informe o Nome: ");
        nome = scanner.nextLine();
        System.out.println("/*************************/");
    }


    public void calcularArea () {
        System.out.println("/**************************/");
        System.out.println("/*         Area           */");
        System.out.println("/**************************/");
        area = lado1 * lado2;
        System.out.println("/**************************/");
    }

    public void calcularPerimetro () {
        System.out.println("/**************************/");
        System.out.println("/*       Perimetro        */");
        System.out.println("/**************************/");
        perimetro = (lado1 * 2) + (lado2 * 2);
        System.out.println("/**************************/");
    }

    public void exibirInformacoes () {
        System.out.println("/**************************/");
        System.out.println("/*      Informações       */");
        System.out.println("/**************************/");
        System.out.println("Nome: " + nome);
        System.out.println("Lado 1: " + lado1);
        System.out.println("Lado 2: " + lado2);
        System.out.println("Area: " + area);
        System.out.println("Perimetro: " + perimetro);
        System.out.println("/**************************/");
    }


    public boolean validarQuadrado() {
        if (lado1 != lado2) {
            System.out.println("Não é um Quadrado !");
            return false;

        } else {
            System.out.println("É um Quadrado !");
            return true;
        }
    }






}
