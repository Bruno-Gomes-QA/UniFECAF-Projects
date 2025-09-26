package br.com.unifecaf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.unifecaf.model.Client;

public class ClientController {

    private final List<Client> clientList = new ArrayList<>();

    // Regex simples (didática)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /** Normaliza CPF removendo tudo que não é dígito */
    private String normalizeCPF(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("\\D", "");
    }

    /** Cria e adiciona um cliente após validar entradas e regras de negócio */
    public Client addClient(String name, String cpf, String rg, String email, String idadeStr) {
        final String sanitizedName  = name == null ? "" : name.trim();
        final String targetCPF      = normalizeCPF(cpf);
        final String sanitizedRG    = rg == null ? "" : rg.trim();
        final String sanitizedEmail = email == null ? "" : email.trim();
        final String sanitizedAge   = idadeStr == null ? "" : idadeStr.trim();

        // Validações
        if (sanitizedName.isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (targetCPF.isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        if (!targetCPF.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos (apenas números).");
        }
        if (!sanitizedEmail.isEmpty() && !EMAIL_PATTERN.matcher(sanitizedEmail).matches()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        final int parsedAge;
        if (sanitizedAge.isEmpty()) {
            parsedAge = 0;
        } else {
            try {
                parsedAge = Integer.parseInt(sanitizedAge);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Idade deve ser um número inteiro.");
            }
            if (parsedAge < 0) {
                throw new IllegalArgumentException("Idade não pode ser negativa.");
            }
        }

        // Regra de negócio: CPF único
        boolean exists = clientList.stream()
                .anyMatch(c -> normalizeCPF(c.getCPF()).equals(targetCPF));
        if (exists) {
            throw new IllegalArgumentException("Já existe cliente com este CPF.");
        }

        // Persiste em memória
        Client c = new Client();
        c.setName(sanitizedName);
        c.setCPF(targetCPF);
        c.setRG(sanitizedRG);
        c.setEmail(sanitizedEmail);
        c.setIdade(parsedAge);

        clientList.add(c);
        return c;
    }

    /** Busca por CPF (aceita com ou sem máscara) */
    public Client searchClient(String cpf) {
        final String target = normalizeCPF(cpf);
        if (target.isEmpty()) return null;

        return clientList.stream()
                .filter(c -> normalizeCPF(c.getCPF()).equals(target))
                .findFirst()
                .orElse(null);
    }

    /**
     * Atualiza campos básicos de um cliente identificado pelo CPF.
     * Campos nulos não são alterados; campos vazios são validados conforme regra.
     */
    public Client updateClient(String cpf, String newName, String newRg, String newEmail, String newIdadeStr) {
        final String target = normalizeCPF(cpf);
        Client existing = searchClient(target);
        if (existing == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF informado.");
        }

        if (newName != null) {
            final String n = newName.trim();
            if (n.isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio.");
            existing.setName(n);
        }

        if (newRg != null) {
            existing.setRG(newRg.trim());
        }

        if (newEmail != null) {
            final String em = newEmail.trim();
            if (!em.isEmpty() && !EMAIL_PATTERN.matcher(em).matches()) {
                throw new IllegalArgumentException("E-mail inválido.");
            }
            existing.setEmail(em);
        }

        if (newIdadeStr != null) {
            final String s = newIdadeStr.trim();
            if (s.isEmpty()) {
                existing.setIdade(0);
            } else {
                final int idadeParsed;
                try {
                    idadeParsed = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Idade deve ser um número inteiro.");
                }
                if (idadeParsed < 0) {
                    throw new IllegalArgumentException("Idade não pode ser negativa.");
                }
                existing.setIdade(idadeParsed);
            }
        }

        return existing;
    }

    /** Remove um cliente pelo CPF (aceita com ou sem máscara) */
    public boolean removeByCPF(String cpf) {
        final String target = normalizeCPF(cpf);
        return clientList.removeIf(c -> normalizeCPF(c.getCPF()).equals(target));
    }

    /** Retorna uma cópia da lista para leitura */
    public List<Client> listAll() {
        return new ArrayList<>(clientList);
    }
}
