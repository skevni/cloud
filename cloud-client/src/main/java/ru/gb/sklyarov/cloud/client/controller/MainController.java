package ru.gb.sklyarov.cloud.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.gb.domain.Command;
import ru.gb.sklyarov.cloud.client.factory.Factory;
import ru.gb.sklyarov.cloud.client.service.NetworkService;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField commandTextField;
    public TextArea commandResultTextArea;

    public NetworkService networkService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();

        createCommandResultHandler();
    }

    private void createCommandResultHandler() {
        new Thread(() -> {
            while (true) {
                String resultCommand = networkService.readCommandResult();
                Platform.runLater(() -> commandResultTextArea.appendText(resultCommand + System.lineSeparator()));
            }
        }).start();
    }

    public void sendCommand(ActionEvent actionEvent) {
        String[] strCommand = commandTextField.getText().trim().split("\\s+");
        if (strCommand.length > 1) {
            String[] commandArgs = Arrays.copyOfRange(strCommand, 1, strCommand.length);
            networkService.sendCommand(new Command(strCommand[0], commandArgs));
            commandTextField.clear();
        }
    }

    public void shutdown() {
        networkService.closeConnection();
    }


}
