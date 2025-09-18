package br.com.fecaf.model;

import java.util.Arrays;
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


    public boolean validarTriangulo() {
        System.out.println("/************************/");
        System.out.println("/* Validar Triângulo    */");
        System.out.println("/************************/");

        boolean ladosPositivos = base > 0 && lado1 > 0 && lado2 > 0;
        boolean desigualdade = base < (lado1 + lado2) && lado1 < (base + lado2) && lado2 < (base + lado1);
        boolean valido = ladosPositivos && desigualdade;

        if (valido) {
            System.out.println("É um triângulo válido!");
        } else {
            System.out.println("Não é um triângulo válido!");
        }

        return valido;
    }


    public boolean verificarTrianguloRetangulo() {
        System.out.println("/************************/");
        System.out.println("/* Triângulo Retângulo  */");
        System.out.println("/************************/");

        double hipotenusa = base;
        double cateto1 = lado1;
        double cateto2 = lado2;

        if (lado1 > hipotenusa) {
            hipotenusa = lado1;
            cateto1 = base;
            cateto2 = lado2;
        }

        if (lado2 > hipotenusa) {
            hipotenusa = lado2;
            cateto1 = base;
            cateto2 = lado1;
        }

        double pitagoras = (cateto1 * cateto1) + (cateto2 * cateto2);
        double hipotenusaAoQuadrado = hipotenusa * hipotenusa;
        boolean retangulo = Math.abs(pitagoras - hipotenusaAoQuadrado) < 0.0001;

        if (retangulo) {
            System.out.println("É um triângulo retângulo!");
        } else {
            System.out.println("Não é um triângulo retângulo!");
        }

        return retangulo;
    }


    public boolean verificarPadraoTresQuatroCinco() {
        System.out.println("/************************/");
        System.out.println("/*   Triângulo 3-4-5    */");
        System.out.println("/************************/");

        double[] lados = {base, lado1, lado2};
        Arrays.sort(lados);
        boolean padrao = lados[0] == 3 && lados[1] == 4 && lados[2] == 5;

        if (padrao) {
            System.out.println("É um triângulo do tipo 3, 4, 5!");
        } else {
            System.out.println("Não é um triângulo do tipo 3, 4, 5!");
        }

        return padrao;
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
