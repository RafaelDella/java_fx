package com.biblioteca.biblioteca;

import com.biblioteca.util.Validador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para CRUD e persistência do aluno.
 */
public class AlunoDAO {
    private static final String ARQUIVO = "alunos.dat";

    public void salvar(Aluno aluno) throws IOException {
        validarAluno(aluno);
        List<Aluno> alunos = listar();

        for (Aluno a : alunos) {
            if (a.getMatricula() == aluno.getMatricula()) {
                throw new IllegalArgumentException("Matrícula já cadastrada: " + aluno.getMatricula());
            }
        }

        alunos.add(aluno);
        salvarLista(alunos);
    }

    public void atualizar(Aluno aluno) throws IOException {
        validarAluno(aluno);
        List<Aluno> alunos = listar();
        boolean encontrado = false;

        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula() == aluno.getMatricula()) {
                alunos.set(i, aluno);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Aluno não encontrado para atualizar: matrícula " + aluno.getMatricula());
        }

        salvarLista(alunos);
    }

    public void deletar(int matricula) throws IOException {
        List<Aluno> alunos = listar();
        boolean removido = alunos.removeIf(a -> a.getMatricula() == matricula);

        if (!removido) {
            throw new IllegalArgumentException("Aluno não encontrado para deletar: matrícula " + matricula);
        }

        salvarLista(alunos);
    }

    public List<Aluno> listar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (List<Aluno>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void salvarLista(List<Aluno> alunos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(alunos);
        }
    }

    private void validarAluno(Aluno aluno) {
        Validador.validarNome(aluno.getNome());
        Validador.validarCurso(aluno.getCurso());
        Validador.validarMatricula(aluno.getMatricula());
        Validador.validarAno(aluno.getAno());
    }
}
