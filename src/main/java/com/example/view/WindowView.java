package com.example.view;

import com.example.explorer.CopyBoard;
import com.example.explorer.FileExplorer;
import com.example.explorer.FileExplorerLocal;
import com.example.explorer.OngoingDirectory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.util.*;

public class WindowView {
    private final FileExplorer fileExplorer;
    private OngoingDirectory ongoingDirectory;
    private final String hostName;
    private final VBox localPane;
    private VBox directoryWindow;
    private Text currentDirectory;
    private Text message;
    private Timer timer;
    private TimerTask timerTask;
    private String separator;

    public WindowView(FileExplorer fileExplorer, VBox localPane, String hostName) {
        this.fileExplorer = fileExplorer;
        this.localPane = localPane;
        this.hostName = hostName;
        setSeparator();
    }

    public void init() {
        Label tittleOne = new Label(hostName);
        Button button = new Button("Go to parent");
        currentDirectory = new Text();
        directoryWindow = new VBox(8);
        ScrollPane scrollPane = new ScrollPane(directoryWindow);
        message = new Text();
        localPane.getChildren().addAll(tittleOne, button, currentDirectory, scrollPane, message);
        directoryWindow.setPadding(new Insets(15));
        tittleOne.setId("tittle");
        button.setGraphic(Icons.getBackIcon());
        timer = new Timer(true);

        fileExplorer.goToDirectory(System.getProperty("user.dir"));
        renderCurrentDirectory();
        addBackgroundContextMenu(scrollPane);
        button.setOnAction(event -> {
            boolean result= fileExplorer.goToParent();
            renderCurrentDirectory();
            setTextAndTimer("Go to parent: " + result);
        });
    }

    private void renderCurrentDirectory() {
        ongoingDirectory = fileExplorer.getOngoingDirectory();
        if (ongoingDirectory == null) {
            setTextAndTimer("Directory is not accessible...");
            return;
        }
        String parent = ongoingDirectory.getCurrentDirectory();
        currentDirectory.setText(parent);
        ArrayList<String> directories = ongoingDirectory.getDirectories();
        Collections.sort(directories);
        directoryWindow.getChildren().clear();
        for (String fileName : directories) {
            directoryWindow.getChildren().add(createDirectoryLabel(fileName, parent));
        }
        HashMap<String, Integer> hashMap = ongoingDirectory.getFiles();
        Set<String> keySet = hashMap.keySet();
        ArrayList<String> files = new ArrayList<>(keySet);
        Collections.sort(files);
        for (String fileName : files) {
            Integer integer = hashMap.get(fileName);
            Label label = createFileLabel(fileName, integer, parent);
            directoryWindow.getChildren().add(label);
        }
    }

    private Label createDirectoryLabel(String fileName, String parent) {
        Label label= new Label(fileName);
        addLabelContextMenu(label, fileName, parent);
        label.setGraphic(Icons.getFolderIcon());
        label.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                boolean result = fileExplorer.goToDirectory(parent + separator + fileName);
                renderCurrentDirectory();
                setTextAndTimer("Go to directory " + fileName + ": " + result);
            }
        });
        addDirectoryMenuItem(label, fileName, parent);
        return label;
    }

    private Label createFileLabel(String fileName, int size, String parent) {
        String string = String.format("%-68s %,12d bytes", fileName, size);
        Label label= new Label(string);
        addLabelContextMenu(label, fileName, parent);
        label.setGraphic(Icons.getFileIcon());
        addFileMenuItem(label, fileName, parent);
        return label;
    }

    private void addLabelContextMenu(Label label, String fileName, String parent) {
        MenuItem menuItemRename = new MenuItem("Rename");
        menuItemRename.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Enter the new name:");
            textInputDialog.showAndWait();
            String newName = textInputDialog.getEditor().getText();
            boolean result = fileExplorer.rename(parent + separator + fileName, parent + separator + newName);
            renderCurrentDirectory();
            CopyBoard.getInstance().clean();
            setTextAndTimer("Rename file/folder " + newName + ": " + result);
        });

        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(event -> {
            boolean result =  fileExplorer.delete(parent + separator + fileName);
            renderCurrentDirectory();
            CopyBoard.getInstance().clean();
            setTextAndTimer("Delete file/folder " + fileName + ": " + result);
        });

        ContextMenu contextMenu = new ContextMenu(menuItemDelete, menuItemRename);
        label.setContextMenu(contextMenu);
    }

    private void addDirectoryMenuItem(Label label, String fileName, String parent) {
        MenuItem menuItem = new MenuItem("Copy");
        menuItem.setOnAction(event -> {
            CopyBoard.getInstance().fillCopyBoard(parent, fileName, hostName, separator, true);
            setTextAndTimer("Copied file/folder " + fileName + ": " + CopyBoard.getInstance().isFilled());
        });
        label.getContextMenu().getItems().add(menuItem);
    }

    private void addFileMenuItem(Label label, String fileName, String parent) {
        MenuItem menuItem = new MenuItem("Copy");
        menuItem.setOnAction(event -> {
            CopyBoard.getInstance().fillCopyBoard(parent, fileName, hostName, separator, false);
            setTextAndTimer("Copied file/folder " + fileName + ": " + CopyBoard.getInstance().isFilled());
        });
        label.getContextMenu().getItems().add(menuItem);
    }

    private void addBackgroundContextMenu(ScrollPane scrollPane) {
        MenuItem newDirectoryMenu = new MenuItem("New directory");
        newDirectoryMenu.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Name for new directory:");
            textInputDialog.showAndWait();
            String directoryName = textInputDialog.getEditor().getText();
            String path = ongoingDirectory.getCurrentDirectory() + separator + directoryName;
            boolean result = fileExplorer.createDirectory(path);
            renderCurrentDirectory();
            CopyBoard.getInstance().clean();
            setTextAndTimer("New directory " + directoryName + ": " + result);
        });

        MenuItem pasteMenu = new MenuItem("Paste");
        pasteMenu.setOnAction(event -> {
            String sourcePath = CopyBoard.getInstance().getParent() + CopyBoard.getInstance().getSeparator() +
                    CopyBoard.getInstance().getFileName();
            String targetPath = ongoingDirectory.getCurrentDirectory() + separator + CopyBoard.getInstance().getFileName();
            if (CopyBoard.getInstance().getHostname().equals(hostName)) {
                boolean result = fileExplorer.copyFile(sourcePath, targetPath);
                setTextAndTimer("Pasted file/directory " + CopyBoard.getInstance().getFileName() + ": " + result);
            } else {
                boolean result;
                if (fileExplorer instanceof FileExplorerLocal) {
                    if (CopyBoard.getInstance().isDirectory()) {
                        result = fileExplorer.createDirectory(targetPath);
                    } else {
                        result = fileExplorer.importFile(sourcePath, targetPath);
                    }
                } else {
                    if (CopyBoard.getInstance().isDirectory()) {
                        result = fileExplorer.createDirectory(targetPath);
                    } else {
                        result = fileExplorer.exportFile(sourcePath, targetPath);
                    }
                }
                setTextAndTimer("Pasted file/directory " + CopyBoard.getInstance().getFileName() + ": " + result);
            }
            renderCurrentDirectory();
            CopyBoard.getInstance().clean();
        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(newDirectoryMenu, pasteMenu);
        scrollPane.setOnContextMenuRequested(event -> {
            contextMenu.show(scrollPane.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            if (CopyBoard.getInstance().isFilled()) {
                boolean visibleCondition = !(CopyBoard.getInstance().getParent().equals(ongoingDirectory.getCurrentDirectory())
                        && CopyBoard.getInstance().getHostname().equals(hostName));
                pasteMenu.setVisible(visibleCondition);
            } else {
                pasteMenu.setVisible(false);
            }
        });
    }

    private void setSeparator() {
        if (fileExplorer instanceof FileExplorerLocal) {
            separator = File.separator;
        } else {
            fileExplorer.getOngoingDirectory();
            String json = fileExplorer.getOngoingDirectoryJSON();
            char stringA = '\\';
            char stringB = '/';
            int countA = 0;
            int countB = 0;
            for (int index = 0; index < json.length(); index++) {
                if (json.charAt(index) == stringA) countA++;
                if (json.charAt(index) == stringB) countB++;
            }
            separator = String.valueOf(countA > countB? stringA : stringB);
        }
    }

    private void setTextAndTimer(String string) {
        if (timerTask != null) timerTask.cancel();
        timer.purge();
        message.setText(string);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                message.setText("");
            }
        };
        timer.schedule(timerTask, 5000);
    }
}
