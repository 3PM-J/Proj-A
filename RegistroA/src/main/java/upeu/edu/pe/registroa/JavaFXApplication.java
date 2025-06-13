package upeu.edu.pe.registroa;

public class JavaFXApplication{

    public static void main(String[] args) {
        RegistroAApplication.main(args);
    }

    /*private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(RegistroAApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/paciente.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);

        primaryStage.setTitle("Sistema de Registro de Pacientes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }*/
}