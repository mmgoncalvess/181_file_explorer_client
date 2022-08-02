package com.example.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Icons {
    private static final ClassLoader classLoader = Icons.class.getClassLoader();

    public static ImageView getFileIcon() {
        return getImageView("images/file_icon.png");
    }

    public static ImageView getFolderIcon() {
        return getImageView("images/directory_icon.png");
    }

    public static ImageView getCheckMarkIcon() {
        return getImageView("images/check_mark_icon.png");
    }

    public static ImageView getBackIcon() {
        return getImageView("images/back_icon.png");
    }

    private static ImageView getImageView(String string) {
        String fileURL = Objects.requireNonNull(classLoader.getResource(string)).toExternalForm();
        Image image = new Image(fileURL);
        ImageView imageView = new ImageView(image);
        if (!string.equals("images/back_icon.png")) imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
