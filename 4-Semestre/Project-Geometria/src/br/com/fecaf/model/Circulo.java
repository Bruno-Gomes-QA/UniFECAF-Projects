package br.com.fecaf.model;

import java.util.Scanner;

public class Circulo {

    // Atributos
    double raio, diametro, area, perimetro;

    Scanner scanner = new Scanner(System.in);

    public void cadastrarCirculo () {
        System.out.println("/************************/");
        System.out.println("/*  Cadastro Circulo    */");
        System.out.println("/************************/");
        System.out.print("Informe o raio: ");
        raio = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("/************************/");
    }

    public void calcularArea () {
        System.out.println("/************************/");
        System.out.println("/*    Calculando Area   */");
        System.out.println("/************************/");
        // Exemplo 1
        // area = 3.14159 * (raio * raio);
        // Exemplo 2
       // area = Math.PI * (raio * raio);
        area = Math.PI * Math.pow(raio, 2);
        System.out.println("/************************/");
    }

    public void calcularPerimetro () {
        System.out.println("/************************/");
        System.out.println("/* Calculando Perimetro */");
        System.out.println("/************************/");

        perimetro = 2 * Math.PI * raio;
        System.out.println("/************************/");

    }

    public void exibirInformacoes () {
        System.out.println("/************************/");
        System.out.println("/* Informações Circulo  */");
        System.out.println("/************************/");
        System.out.println("Raio: " + raio);
        System.out.println("Diametro: " + diametro);
        System.out.println("Area: " + area);
        System.out.println("Perimetro: " + perimetro);
        System.out.println("/*************************/");
    }





}
