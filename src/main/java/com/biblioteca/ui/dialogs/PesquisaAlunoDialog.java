package com.biblioteca.ui.dialogs;

import com.biblioteca.biblioteca.Aluno;
import com.biblioteca.biblioteca.AlunoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Janela popup para pesquisar aluno por matrícula.
 */
public class PesquisaAlunoDialog {

    private AlunoDAO alunoDAO;

    public PesquisaAlunoDialog(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public void mostrar() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Pesquisar Aluno por Matrícula");

        Label label = new Label("Digite a matrícula:");
        TextField matriculaInput = new TextField();

        Button btnPesquisar = new Button("Pesquisar");

        btnPesquisar.setOnAction(e -> {
            try {
                int matricula = Integer.parseInt(matriculaInput.getText());
                Aluno resultado = buscarAlunoPorMatricula(matricula);
                if (resultado == null) {
                    mostrarErro("Aluno com matrícula " + matricula + " não encontrado.");
                } else {
                    mostrarInfo("Aluno encontrado", resultado.toString());
                    dialog.close();
                }
            } catch (NumberFormatException ex) {
                mostrarErro("Matrícula deve ser um número inteiro válido.");
            }
        });

        VBox vbox = new VBox(10, label, matriculaInput, btnPesquisar);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 300, 150);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private Aluno buscarAlunoPorMatricula(int matricula) {
        return alunoDAO.listar().stream()
                .filter(a -> a.getMatricula() == matricula)
                .findFirst()
                .orElse(null);
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
