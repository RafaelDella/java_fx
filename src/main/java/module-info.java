module com.biblioteca.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;

    // Permite que o JavaFX faça reflexão para carregar FXML nas packages que contêm UI
    opens com.biblioteca.ui to javafx.fxml;

    // Caso use FXML também para classes do domínio (normalmente não usa)
    opens com.biblioteca.biblioteca to javafx.fxml;

    // Exporta os pacotes para que possam ser usados fora do módulo (ex.: na aplicação principal)
    exports com.biblioteca.biblioteca;
    exports com.biblioteca.ui;
}
