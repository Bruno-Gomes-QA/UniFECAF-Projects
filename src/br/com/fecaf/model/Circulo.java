package br.com.fecaf.model;

import java.util.Scanner;

public class Circulo {
    public double raio, diametro, area, perimetro;
    Scanner scanner = new Scanner(System.in);

    public void cadastrarCirculo() {
        System.out.println("/*****************************************");
        System.out.println("/*            Cadastrar Círculo          *");
        System.out.println("/*****************************************");
        System.out.print("Informe o Raio: ");
        raio = scanner.nextDouble();
        scanner.nextLine(); // consome o \n deixado pelo nextDouble
        System.out.println("/*****************************************");
    }

    public void calcularArea() {
        System.out.println("/*****************************************");
        System.out.println("/*             Calculando Área           *");
        System.out.println("/*****************************************");
        // Math.PI é uma constante e Math.pow eleva um número a uma potência
        area = Math.PI * Math.pow(raio, 2);
    }

    public void calcularPerimetroC() {
        System.out.println("/*****************************************");
        System.out.println("/*           Calculando Perímetro        *");
        System.out.println("/*****************************************");
        perimetro = 2 * Math.PI * raio;
        System.out.println("/*****************************************");
    }

    public void exibirInformacoes() {
        System.out.println("/*****************************************");
        System.out.println("/*          Informações do Círculo       *");
        System.out.println("/*****************************************");
        System.out.println("Raio: " + raio);
        System.out.println("Área: " + area);
        System.out.println("Perímetro: " + perimetro);
        System.out.println("/*****************************************");
        System.out.println("*           Indo para o menu            *");
    }
}
