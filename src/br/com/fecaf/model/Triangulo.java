package br.com.fecaf.model;

import java.util.Scanner;

public class Triangulo {
    // Atributo
    double base, lado1, lado2, altura, area, perimetro;
    String nome;

    Scanner scanner = new Scanner(System.in);

    // Cadastrar Triangulo
    public void cadastrarTriangulo () {
        System.out.println("/***************************/");
        System.out.println("/*         Triângulo       */");
        System.out.println("/***************************/");
        System.out.println("/* Informe a base:         */");
        base = scanner.nextDouble();
        System.out.println("/* Informe o lado 1:       */");
        lado1 = scanner.nextDouble();
        System.out.println("/* Informe o lado 2:       */");
        lado2 = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Informe o nome:            */");
        nome = scanner.nextLine();
        System.out.println("/*  Cadastrado com Sucesso */");
        System.out.println("/***************************/");
    }

    // Calcular Area
    public void calcularArea () {
        System.out.println("/************************/");
        System.out.println("/*    Calcular Area     */");
        System.out.println("/************************/");
        area = (base * altura ) / 2;
        System.out.println("/************************/");
    }

    public void calcularPerimetro () {
        System.out.println("/************************/");
        System.out.println("/* Calcular Perimetro   */");
        System.out.println("/************************/");
        perimetro = base + lado1 + lado2;
        System.out.println("/************************/");
    }

    // Definir Tipo Triangulo
    public void verificarTipo () {
        System.out.println("/************************/");
        System.out.println("/*    Verificar Tipo    */");
        System.out.println("/************************/");

        if (base == lado1 && base == lado2) {
            System.out.println("Equilatero");

        } else if (base != lado1 && base != lado2 && lado1 != lado2) {
            System.out.println("Escaleno");

        } else {
            System.out.println("Isosceles");
        }
    }


    public void exibirInformacoes () {
        System.out.println("/*************************/");
        System.out.println("/* Informações Triângulo */");
        System.out.println("/*************************/");
        System.out.println("/* Nome: " + nome);
        System.out.println("/* Base: " + base);
        System.out.println("/* Lado 1: " + lado1);
        System.out.println("/* Lado 2: " + lado2);
        System.out.println("/* Area: " + area);
        System.out.println("/* Perimetro: " + perimetro);
        System.out.println("/**************************/");
    }







}
