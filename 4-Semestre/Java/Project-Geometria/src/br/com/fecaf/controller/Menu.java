package br.com.fecaf.controller;

import br.com.fecaf.model.Circulo;
import br.com.fecaf.model.Retangulo;
import br.com.fecaf.model.Triangulo;

import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    Retangulo retangulo = new Retangulo();
    Circulo circulo = new Circulo();
    Triangulo triangulo = new Triangulo();

    public void executarMenu() {

        boolean exit = false;

        while (!exit) {

            System.out.println("/******************************/");
            System.out.println("/*        Menu Principal      */");
            System.out.println("/******************************/");
            System.out.println("/* 1 - Retangulo              */");
            System.out.println("/* 2 - Circulo                */");
            System.out.println("/* 3 - Triangulo              */");
            System.out.println("/* 4 - Sair                   */");
            System.out.println("/******************************/");

            System.out.println("Informe uma opção: ");
            int userOption = scanner.nextInt();

            switch (userOption) {
                case 1:
                    boolean exitRetangulo = false;

                    while (!exitRetangulo) {
                        System.out.println("/*****************************/");
                        System.out.println("/*********   Menu   **********/");
                        System.out.println("/*****************************/");
                        System.out.println("1 - Cadastrar Retângulo");
                        System.out.println("2 - Calcular Area");
                        System.out.println("3 - Calcular Perimetro");
                        System.out.println("4 - Exibir Informações");
                        System.out.println("5 - Validar Quadrado");
                        System.out.println("6 - Sair");
                        System.out.println("/*****************************/");

                        int userOptionRetangulo = scanner.nextInt();
                        scanner.nextLine();


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
                                retangulo.exibirInformacoes();
                                break;
                            case 5:
                                retangulo.validarQuadrado();
                                break;
                            case 6:
                                System.out.println("Retornando ao Menu Principal...");
                                exitRetangulo = true;
                                break;
                            default:
                                System.out.println("Escolha uma opção válida !");
                        }
                    }



                    break;
                case 2:

                    boolean exitCirculo = false;

                    while (!exitCirculo) {

                        System.out.println("/****************************/");
                        System.out.println("/*       Menu Circulo       */");
                        System.out.println("/****************************/");
                        System.out.println("/* 1 - Cadastrar Circulo    */");
                        System.out.println("/* 2 - Calcular Area        */");
                        System.out.println("/* 3 - Calcular Perimetro   */");
                        System.out.println("/* 4 - Exibir Circulo       */");
                        System.out.println("/* 5 - Sair                 */");
                        System.out.println("/****************************/");

                        System.out.println("Informe uma opção: ");
                        int userOptionCirculo = scanner.nextInt();

                        switch (userOptionCirculo){
                            case 1:
                                circulo.cadastrarCirculo();
                                break;
                            case 2:
                                circulo.calcularArea();
                                break;
                            case 3:
                                circulo.calcularPerimetro();
                                break;
                            case 4:
                                circulo.exibirInformacoes();
                                break;
                            case 5:
                                System.out.println("Retornando ao Menu Principal...");
                                exitCirculo = true;
                                break;
                        }
                    }
                    break;
                case 3:
                    boolean exitTriangulo = false;

                    while (!exitTriangulo) {
                        System.out.println("/****************************/");
                        System.out.println("/*      Menu Triângulo     */");
                        System.out.println("/****************************/");
                        System.out.println("/* 1 - Cadastrar Triângulo */");
                        System.out.println("/* 2 - Calcular Area       */");
                        System.out.println("/* 3 - Calcular Perimetro  */");
                        System.out.println("/* 4 - Verificar Tipo      */");
                        System.out.println("/* 5 - Validar Triângulo   */");
                        System.out.println("/* 6 - Triângulo Retângulo */");
                        System.out.println("/* 7 - Triângulo 3, 4, 5   */");
                        System.out.println("/* 8 - Exibir Informações  */");
                        System.out.println("/* 9 - Sair                */");
                        System.out.println("/****************************/");

                        int userOptionTriangulo = scanner.nextInt();
                        scanner.nextLine();

                        switch (userOptionTriangulo) {
                            case 1:
                                triangulo.cadastrarTriangulo();
                                break;
                            case 2:
                                triangulo.calcularArea();
                                break;
                            case 3:
                                triangulo.calcularPerimetro();
                                break;
                            case 4:
                                triangulo.verificarTipo();
                                break;
                            case 5:
                                triangulo.validarTriangulo();
                                break;
                            case 6:
                                triangulo.verificarTrianguloRetangulo();
                                break;
                            case 7:
                                triangulo.verificarPadraoTresQuatroCinco();
                                break;
                            case 8:
                                triangulo.exibirInformacoes();
                                break;
                            case 9:
                                System.out.println("Retornando ao Menu Principal...");
                                exitTriangulo = true;
                                break;
                            default:
                                System.out.println("Escolha uma opção válida !");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saindo ...");
                    exit = true;
                    break;
            }














        }
    }
}
