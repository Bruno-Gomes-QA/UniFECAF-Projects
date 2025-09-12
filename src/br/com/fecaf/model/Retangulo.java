package br.com.fecaf.model;

import java.util.Scanner;

public class Retangulo {

    // Atributos
    double lado1, lado2, area, perimetro;
    String nome;

    // Scanner responsável por capturar o input do usuário
    Scanner scanner = new Scanner(System.in);

    public void cadastrarRetangulo() {
        System.out.println("/*****************************************");
        System.out.println("/*           Cadastrar Retângulo         *");
        System.out.println("/*****************************************");

        System.out.print("Informe o Lado 1: ");
        lado1 = scanner.nextDouble();

        System.out.print("Informe o Lado 2: ");
        lado2 = scanner.nextDouble();

        scanner.nextLine(); // consome o \n deixado pelo nextDouble

        System.out.print("Informe o Nome: ");
        nome = scanner.nextLine();

        System.out.println("/*****************************************");
    }

    public void calcularArea() {
        System.out.println("/*****************************************");
        System.out.println("/*                Área                   *");
        System.out.println("/*****************************************");
        area = lado1 * lado2;
    }

    public void calcularPerimetro() {
        System.out.println("/*****************************************");
        System.out.println("/*              Perímetro                *");
        System.out.println("/*****************************************");
        perimetro = (lado1 * 2) + (lado2 * 2);
        System.out.println("/*****************************************");
    }

    public void exibirInformacoes() {
        System.out.println("/*****************************************");
        System.out.println("/*              Informações              *");
        System.out.println("/*****************************************");
        System.out.println("Nome: " + nome);
        System.out.println("Lado 1: " + lado1);
        System.out.println("Lado 2: " + lado2);
        System.out.println("Área: " + area);
        System.out.println("Perímetro: " + perimetro);
        System.out.println("/*****************************************");
        System.out.println("*           Indo para o menu            *");
    }
}
