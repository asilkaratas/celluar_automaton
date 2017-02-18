package com.celluarautomaton.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by asilkaratas on 1/24/15.
 */
public class BorderedTitledPane extends StackPane
{
    private StackPane contentPane;
    public BorderedTitledPane(String titleString, Node content)
    {
        Label title = new Label(" " + titleString + " ");
        title.getStyleClass().add("bordered-titled-title");
        VBox titleVBox = new VBox();
        titleVBox.getStyleClass().add("bordered-titled-title-container");
        titleVBox.getChildren().add(title);

        StackPane.setAlignment(titleVBox, Pos.TOP_LEFT);

        contentPane = new StackPane();
        content.getStyleClass().add("bordered-titled-content");
        contentPane.getChildren().add(content);
        contentPane.setMinSize(0, 0);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.autosize();

        getStyleClass().add("bordered-titled-border");
        getChildren().addAll(titleVBox, contentPane);
    }

    public StackPane getContentPane()
    {
        return contentPane;
    }
}