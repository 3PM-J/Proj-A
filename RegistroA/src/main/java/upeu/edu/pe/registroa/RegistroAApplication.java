package upeu.edu.pe.registroa;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RegistroAApplication extends Application {

	private static ConfigurableApplicationContext springContext;
	private Parent parent;

	public static void main(String[] args) {
		//SpringApplication.run(RegistroAApplication.class, args);
		launch(args);
	}

	@Override
	public void init() {
		SpringApplication builder = new SpringApplication(RegistroAApplication.class);

		springContext = builder.run(getParameters().getRaw().toArray(new String[0]));
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/paciente.fxml"));
			fxmlLoader.setControllerFactory(springContext::getBean);
			parent = fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}

	}

	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = new Scene(parent);

		double preferredWidth = parent.prefWidth(-1);
		double preferredHeight = parent.prefHeight(-1);
		stage.setMinWidth(preferredWidth);
		stage.setMinHeight(preferredHeight);
		stage.setScene(scene);
		stage.setTitle("Registro de Pacientes");
		stage.show();
	}
}