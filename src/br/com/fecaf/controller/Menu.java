package br.com.fecaf.controller;

import br.com.fecaf.model.Circulo;
import br.com.fecaf.model.Retangulo;

import java.util.Scanner;

public class Menu {

    // Scanner responsável por capturar o input do usuário no menu principal
    Scanner scanner = new Scanner(System.in);

    // Instâncias dos modelos utilizados pelos submenus
    Retangulo retangulo = new Retangulo();
    Circulo circulo = new Circulo();

    public void executarMenu() {

        boolean exit = false; // flag de controle do loop principal

        // Enquanto o usuário não solicitar saída, mantém o menu em execução
        while (!exit) {
            System.out.println("/*****************************************");
            System.out.println("/*                 Menu                  *");
            System.out.println("/*****************************************");
            System.out.println("/* 1 - Retângulo                         *");
            System.out.println("/* 2 - Círculo                           *");
            System.out.println("/* 3 - Triângulo                         *");
            System.out.println("/* 4 - Sair                              *");
            System.out.println("/*****************************************");

            System.out.println("Informe uma opção: ");
            int userOption = scanner.nextInt();

            // Altera para o menu de acordo com a seleção do usuário
            switch (userOption) {
                case 1: {
                    boolean exitRetangulo = false; // controla o submenu de Retângulo
                    while (!exitRetangulo) {
                        System.out.println("/*****************************************");
                        System.out.println("/*                 Menu                  *");
                        System.out.println("/*****************************************");
                        System.out.println("1 - Cadastrar Retângulo");
                        System.out.println("2 - Calcular Área");
                        System.out.println("3 - Calcular Perímetro");
                        System.out.println("4 - Exibir Informações");
                        System.out.println("5 - Sair");
                        System.out.println("/*****************************************");

                        int userOptionRetangulo = scanner.nextInt();
                        scanner.nextLine(); // consome quebra de linha após nextInt

                        switch (userOptionRetangulo) {
                            case 1:
                                retangulo.cadastrarRetangulo();
                                break;
                            case 2:
                                retangulo.calcularArea();
                                break;
                            case 3:
                                retangulo.calcularPerimetro();
                                break;
                            case 4:
                                System.out.println("Exibindo informações do Retângulo");
                                retangulo.exibirInformacoes();
                                break;
                            case 5:
                                System.out.println("Saindo...");
                                exitRetangulo = true;
                                break;
                        }
                    }
                    break;
                }
                case 2: {
                    boolean exitCirculo = false; // controla o submenu de Círculo
                    while (!exitCirculo) {
                        System.out.println("/*****************************************");
                        System.out.println("/*                 Menu                  *");
                        System.out.println("/*****************************************");
                        System.out.println("1 - Cadastrar Círculo");
                        System.out.println("2 - Calcular Área");
                        System.out.println("3 - Calcular Perímetro");
                        System.out.println("4 - Exibir Informações");
                        System.out.println("5 - Sair");
                        System.out.println("/*****************************************");

                        int userOptionCirculo = scanner.nextInt();
                        scanner.nextLine(); // consome quebra de linha após nextInt

                        switch (userOptionCirculo) {
                            case 1:
                                circulo.cadastrarCirculo();
                                break;
                            case 2:
                                circulo.calcularArea();
                                break;
                            case 3:
                                circulo.calcularPerimetroC();
                                break;
                            case 4:
                                System.out.println("Exibindo informações do Círculo");
                                circulo.exibirInformacoes();
                                break;
                            case 5:
                                System.out.println("Saindo...");
                                exitCirculo = true;
                                break;
                        }
                    }
                    break;
                }
                case 3: {
                    boolean exitTriangulo = false; // controla o submenu do Triângulo
                    while (!exitTriangulo) {
                        // Menu do Triângulo
                        int userOptionTriangulo = scanner.nextInt();
                        scanner.nextLine(); // consome quebra de linha após nextInt

                        switch (userOptionTriangulo) {
                            case 1:
                                // Lógica para cadastrar o triângulo
                                break;
                        }
                    }
                    break;
                }
                case 4:
                    System.out.println("Saindo...");
                    exit = true;
                    break;
            }
        }
    }
}
