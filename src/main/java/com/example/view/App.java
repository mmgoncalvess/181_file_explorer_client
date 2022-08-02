package com.example.view;

import com.example.explorer.FileExplorer;
import com.example.explorer.FileExplorerLocal;
import com.example.explorer.FileExplorerRemote;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        HBox pane = new HBox(15);
        pane.setPadding(new Insets(15));
        VBox localPane = new VBox(10);
        VBox remotePane = new VBox(10);
        FileExplorer localFileExplorer = new FileExplorerLocal();
        FileExplorer remoteFileExplorer = new FileExplorerRemote();
        WindowView windowLocal = new WindowView(localFileExplorer, localPane, "Local Host");
        WindowView windowRemote = new WindowView(remoteFileExplorer, remotePane, "Remote Host");
        pane.getChildren().addAll(localPane, remotePane);
        windowLocal.init();
        windowRemote.init();
        Scene scene = new Scene(pane, 1845, 800);
        scene.getStylesheets().add("styles.css");
        stage.setTitle("  File Explorer");
        stage.setScene(scene);
        stage.getIcons().add(Icons.getCheckMarkIcon().getImage());
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
