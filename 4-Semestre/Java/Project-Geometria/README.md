# Projeto Figuras Geométricas (AAI)

[![Java](https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=java)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-blue?style=for-the-badge&logo=github)]()  
[![License](https://img.shields.io/badge/License-MIT-black?style=for-the-badge)]()  

Projeto desenvolvindo durante as aulas de POO na UniFECAF utilizando **Java 17**; Esse projeto permite cadastrar figuras geométricas (Retângulo, Círculo e Triângulo), calcular suas áreas, perímetros e exibir informações formatadas em um menu interativo.

---

## Funcionalidades

- **Retângulo**
  - Cadastrar lados e nome
  - Calcular área
  - Calcular perímetro
  - Exibir informações
  - Validar se é quadrado

- **Círculo**
  - Cadastrar raio
  - Calcular área
  - Calcular perímetro
  - Exibir informações

- **Triângulo**
  - Cadastrar lados e nome
  - Calcular área
  - Calcular perímetro
  - Classificar tipo (Equilátero, Isósceles, Escaleno)
  - Validar triângulo
  - Verificar triângulo retângulo (Pitágoras)
  - Validar padrão 3-4-5

---
## Explicação do Triângulo

**Triângulo Implementado**

- Utilizamos o mesmo `Scanner` da classe para ler base, lado1, lado2 e o nome, mantendo os valores nos atributos para reutilizar quando o menu chamar qualquer opção.
- `calcularArea` preserva a fórmula `(base * altura) / 2`, como definido originalmente para receber a altura posteriormente.
- `calcularPerimetro` soma os três lados cadastrados para manter o valor atualizado.
- `verificarTipo` compara as medidas: três lados iguais indicam equilátero, três lados diferentes indicam escaleno e o restante é isósceles.

**Validações Novas**

- `validarTriangulo` garante que os lados são positivos e aplica a desigualdade triangular; só confirmamos a validade quando todas as verificações são verdadeiras.
- `verificarTrianguloRetangulo` identifica o maior lado como hipotenusa, calcula `cateto1^2 + cateto2^2` e compara com `hipotenusa^2` usando uma tolerância pequena (`Math.abs(...) < 0.0001`).
- `verificarPadraoTresQuatroCinco` ordena os lados e confere se ficam exatamente em `3`, `4` e `5`.

**Menu Integrado**

- No `Menu.java` criamos um laço específico para o triângulo; ao escolher “Triângulo” surge um sub-menu com nove opções.
- Cada opção chama diretamente os métodos da classe `Triangulo` para cadastrar, calcular, classificar, validar, verificar retângulo, testar o padrão 3-4-5 e exibir os dados. A última opção encerra o sub-menu e retorna ao menu principal.

---

## Exemplos para Testar

| Lados informados | Situação esperada |
| --- | --- |
| `3, 4, 5` | Triângulo válido; tipo escaleno; retângulo; padrão 3-4-5 true |
| `5, 5, 5` | Triângulo válido; tipo equilátero; não retângulo; padrão 3-4-5 false |
| `6, 6, 4` | Triângulo válido; tipo isósceles; não retângulo; padrão 3-4-5 false |
| `7, 8, 9` | Triângulo válido; tipo escaleno; não retângulo; padrão 3-4-5 false |
| `2, 2, 5` | Triângulo inválido (falha na desigualdade triangular); demais classificações não se aplicam |
| `6, 8, 10` | Triângulo válido; tipo escaleno; retângulo (hipotenusa 10); padrão 3-4-5 false |