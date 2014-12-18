package MP3Player;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        // Parent root = FXMLLoader.load(getClass().getResource("MP3Player.fxml"));
        final FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "MP3Player.fxml"
                )
        );

        final Parent root = (Parent)loader.load();
        final Controller controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("MP3 Player");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent w) {
                System.out.println("Stage is closing");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
