package com.abriv.abriviator;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField AbriviationInput;

    @FXML
    private Button addButton;

    @FXML
    private Text error;

    @FXML
    private VBox Vbox;

    @FXML
    private TextField linkInput;

    @FXML
    void initialize() throws IOException, SQLException, ClassNotFoundException {


        DB db = new DB();

        ResultSet resultSet = db.getLinks();

        while (resultSet.next()) {

            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("linksPanel.fxml")));

            Hyperlink linkk = (Hyperlink) node.lookup("#link");
            linkk.setText(resultSet.getString("abriviation"));

            String hybLink = resultSet.getString("link");

            linkk.setOnAction(event -> {

                try {
                    Desktop.getDesktop().browse(new URI(hybLink));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            });


            node.setOnMouseEntered(mouseEvent -> {
                node.setStyle("-fx-background-color: #343434;");
            });

            node.setOnMouseExited(mouseEvent -> {
                node.setStyle("-fx-background-color: #2e2e2e;");
            });


            Vbox.getChildren().add(node);
            Vbox.setSpacing(10);



        }

        addButton.setOnAction(event -> {

            String abriv = AbriviationInput.getText();
            String link = linkInput.getText();

            try {
                if(abriv.length() >= 2) {
                    db.addLink(abriv, link);
                    Vbox.getChildren().clear();
                    initialize();
                    error.setText("Готово");
                    AbriviationInput.clear();
                    linkInput.clear();

                }else
                    error.setText("error");
                Vbox.getChildren().clear();
                initialize();
                AbriviationInput.clear();
                linkInput.clear();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });




}



}


