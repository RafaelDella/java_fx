package com.biblioteca.ui;

import com.biblioteca.biblioteca.Aluno;
import com.biblioteca.biblioteca.AlunoDAO;
import com.biblioteca.ui.dialogs.PesquisaAlunoDialog;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe principal JavaFX para CRUD Aluno.
 */
public class AlunoCRUDApp extends Application {

    private AlunoDAO alunoDAO = new AlunoDAO();
    private ListView<Aluno> listView = new ListView<>();

    private TextField nomeField = new TextField();
    private TextField cursoField = new TextField();
    private TextField matriculaField = new TextField();
    private CheckBox bolsistaCheckBox = new CheckBox("Bolsista");
    private TextField anoField = new TextField();

    private ComboBox<String> filtroAnoCombo = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {

        Button btnAdicionar = new Button("Adicionar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");
        Button btnPesquisar = new Button("Pesquisar");

        btnAdicionar.setOnAction(e -> adicionarAluno());
        btnAtualizar.setOnAction(e -> atualizarAluno());
        btnExcluir.setOnAction(e -> excluirAluno());
        btnPesquisar.setOnAction(e -> {
            PesquisaAlunoDialog dialog = new PesquisaAlunoDialog(alunoDAO);
            dialog.mostrar();
        });

        filtroAnoCombo.getItems().add("Todos");
        filtroAnoCombo.getSelectionModel().selectFirst();
        filtroAnoCombo.setOnAction(e -> aplicarFiltroAno());

        VBox form = new VBox(10,
                new Label("Nome:"), nomeField,
                new Label("Curso:"), cursoField,
                new Label("Matrícula:"), matriculaField,
                bolsistaCheckBox,
                new Label("Ano de Ingresso:"), anoField
        );

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnExcluir, btnPesquisar);

        HBox filtroBox = new HBox(10, new Label("Filtrar por Ano:"), filtroAnoCombo);

        VBox root = new VBox(10,
                form,
                botoes,
                filtroBox,
                new Label("Lista de Alunos:"), listView
        );
        root.setPadding(new Insets(10));

        atualizarLista();
        atualizarFiltroAno();

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nomeField.setText(newSel.getNome());
                cursoField.setText(newSel.getCurso());
                matriculaField.setText(String.valueOf(newSel.getMatricula()));
                bolsistaCheckBox.setSelected(newSel.isBolsista());
                anoField.setText(String.valueOf(newSel.getAno()));
                matriculaField.setDisable(true);
            }
        });

        Scene scene = new Scene(root, 450, 600);
        primaryStage.setTitle("CRUD Completo - Aluno");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarAluno() {
        try {
            String nome = nomeField.getText();
            String curso = cursoField.getText();
            int matricula = Integer.parseInt(matriculaField.getText());
            boolean bolsista = bolsistaCheckBox.isSelected();
            int ano = Integer.parseInt(anoField.getText());

            Aluno aluno = new Aluno(nome, curso, matricula, bolsista, ano);
            alunoDAO.salvar(aluno);
            limparCampos();
            atualizarLista();
            atualizarFiltroAno();
        } catch (NumberFormatException ex) {
            mostrarErro("Matrícula e Ano devem ser números inteiros válidos.");
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
        } catch (IOException ex) {
            mostrarErro("Erro ao salvar o aluno: " + ex.getMessage());
        }
    }

    private void atualizarAluno() {
        try {
            Aluno selecionado = listView.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarErro("Selecione um aluno para atualizar.");
                return;
            }

            String nome = nomeField.getText();
            String curso = cursoField.getText();
            int matricula = selecionado.getMatricula();
            boolean bolsista = bolsistaCheckBox.isSelected();
            int ano = Integer.parseInt(anoField.getText());

            Aluno aluno = new Aluno(nome, curso, matricula, bolsista, ano);
            alunoDAO.atualizar(aluno);
            limparCampos();
            atualizarLista();
            atualizarFiltroAno();
            matriculaField.setDisable(false);
        } catch (NumberFormatException ex) {
            mostrarErro("Ano deve ser um número inteiro válido.");
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
        } catch (IOException ex) {
            mostrarErro("Erro ao atualizar o aluno: " + ex.getMessage());
        }
    }

    private void excluirAluno() {
        try {
            Aluno selecionado = listView.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarErro("Selecione um aluno para excluir.");
                return;
            }

            alunoDAO.deletar(selecionado.getMatricula());
            limparCampos();
            atualizarLista();
            atualizarFiltroAno();
            matriculaField.setDisable(false);
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
        } catch (IOException ex) {
            mostrarErro("Erro ao deletar o aluno: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.clear();
        cursoField.clear();
        matriculaField.clear();
        bolsistaCheckBox.setSelected(false);
        anoField.clear();
        matriculaField.setDisable(false);
        listView.getSelectionModel().clearSelection();
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void aplicarFiltroAno() {
        String selecionado = filtroAnoCombo.getSelectionModel().getSelectedItem();
        List<Aluno> alunos = alunoDAO.listar();

        if ("Todos".equals(selecionado)) {
            listView.getItems().setAll(alunos);
        } else {
            try {
                int anoFiltro = Integer.parseInt(selecionado);
                List<Aluno> filtrados = alunos.stream()
                        .filter(a -> a.getAno() == anoFiltro)
                        .collect(Collectors.toList());
                listView.getItems().setAll(filtrados);
            } catch (NumberFormatException e) {
                listView.getItems().setAll(alunos);
            }
        }
    }

    private void atualizarLista() {
        listView.getItems().setAll(alunoDAO.listar());
    }

    private void atualizarFiltroAno() {
        List<Aluno> alunos = alunoDAO.listar();
        filtroAnoCombo.getItems().clear();
        filtroAnoCombo.getItems().add("Todos");
        alunos.stream()
                .map(Aluno::getAno)
                .distinct()
                .sorted()
                .forEach(ano -> filtroAnoCombo.getItems().add(String.valueOf(ano)));
        filtroAnoCombo.getSelectionModel().selectFirst();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
