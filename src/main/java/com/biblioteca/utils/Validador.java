package com.biblioteca.util;

/**
 * Classe utilitária para validações de dados.
 */
public class Validador {

    /**
     * Valida nome (mínimo 3 caracteres, sem números).
     */
    public static void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve conter pelo menos 3 caracteres.");
        }
        if (nome.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Nome não pode conter números.");
        }
    }

    /**
     * Valida curso (mínimo 6 caracteres, somente letras e espaços).
     */
    public static void validarCurso(String curso) {
        if (curso == null || curso.trim().length() < 6) {
            throw new IllegalArgumentException("Curso deve conter pelo menos 6 caracteres.");
        }
        if (!curso.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Curso deve conter apenas letras e espaços.");
        }
    }

    /**
     * Valida matrícula (exatamente 4 dígitos numéricos, positivo).
     */
    public static void validarMatricula(int matricula) {
        String matriculaStr = String.valueOf(matricula);
        if (!matriculaStr.matches("\\d{4}")) {
            throw new IllegalArgumentException("Matrícula deve conter exatamente 4 dígitos numéricos.");
        }
        if (matricula <= 0) {
            throw new IllegalArgumentException("Matrícula deve ser um número positivo.");
        }
    }

    /**
     * Valida ano ingresso (entre 2000 e 2100).
     */
    public static void validarAno(int ano) {
        if (ano < 2000 || ano > 2100) {
            throw new IllegalArgumentException("Ano de ingresso deve estar entre 2000 e 2100.");
        }
    }
}
