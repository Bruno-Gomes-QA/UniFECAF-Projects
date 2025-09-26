package br.com.unifecaf.model;

public class Client {
  // Encapsulation: all attributes are private
  private String name, cpf, rg, email;
  private int idade;

  // Getter and Setter for Name
  public String getName(){
    return name;
  }
  public void setName(String name){
    this.name = name;
  }

  // Getter and Setter for CPF
  public String getCPF(){
    return cpf;
  }
  public void setCPF(String cpf){
    this.cpf = cpf;
  }

  // Getter and Setter for RG
  public String getRG(){
    return rg;
  }
  public void setRG(String rg){
    this.rg = rg;
  }

  // Example: Email and Age follow the same encapsulation principle
  public String getEmail(){
    return email;
  }
  public void setEmail(String email){
    this.email = email;
  }

  public int getIdade(){
    return idade;
  }
  public void setIdade(int idade){
    this.idade = idade;
  }
}
