package ru.gb.sklyarov.cloud.client.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.gb.sklyarov.cloud.client.factory.Factory;
import ru.gb.sklyarov.cloud.client.service.NetworkService;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.FileInfo;
import ru.gb.sklyarov.domain.FileType;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public static final int BYTES_STEP = 1024;

    public TextField commandTextField;
    public TextArea commandResultTextArea;

    public NetworkService networkService;

    public TableView clientView;
    public TableView serverView;

    public Button btnDownload;
    public Button btnUpload;

    public Label textUser;

    Path applicationPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();
        initializeClientView();
        initializeServerView();
        createCommandResultHandler();
    }

    public void sendCommand(ActionEvent actionEvent) {
        String[] strCommand = commandTextField.getText().trim().split("\\s+");
        if (strCommand.length > 1) {
            String[] commandArgs = Arrays.copyOfRange(strCommand, 1, strCommand.length);
            networkService.sendCommand(new Command(strCommand[0], commandArgs));
            commandTextField.clear();
        }
    }

    private void initializeClientView() {
        createTableView(clientView);
        applicationPath = Paths.get("").toAbsolutePath();
        clientView.getItems().add(0, new FileInfo(Paths.get(applicationPath.toString(),"..")));
        clientView.getItems().addAll(getFilesDirectories(applicationPath));
        clientView.sort();
    }

    private List<FileInfo> getFilesDirectories(Path path) {
        List<FileInfo> files = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path path1 : stream) {
                files.add(new FileInfo(path1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    private void initializeServerView() {
        createTableView(serverView);
    }

    private void createTableView(TableView tableView) {
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Name");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        TableColumn<FileInfo, String> fileModifiedColumn = new TableColumn<>("Modified");
        fileModifiedColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getModified().format(dateTimeFormatter).equals("01.01.0001 00:00:00") ? "" : param.getValue().getModified().format(dateTimeFormatter)));

        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>("Type");
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().toString().equals("APARENT") ? "" : param.getValue().getType().toString()));

        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Size");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeColumn.setCellFactory(column -> new TableCell<FileInfo, Long>() {

            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    String text = convertSizeToString(item);
                    if (item == -1l) {
                        text = "";
                    }
                    setText(text);
                }
            }
        });

        tableView.getColumns().addAll(fileNameColumn, fileModifiedColumn, fileTypeColumn, fileSizeColumn);
        fileNameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(2.2));
        fileModifiedColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3.9));
        fileTypeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(8));
        fileSizeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));

        tableView.getSortOrder().add(fileTypeColumn);

    }

    private String convertSizeToString(Long value) {

        int count = 0;
        while (value > BYTES_STEP && count < 5) {
            value /= BYTES_STEP;
            count++;
        }
        StringBuilder builder = new StringBuilder(100);
        builder.append(value).append(" ");
        switch (count) {
            case (1):
                builder.append("KB");
                break;

            case (2):
                builder.append("MB");
                break;

            case (3):
                builder.append("GB");
                break;

            case (4):
                builder.append("PB");
                break;

            default:
                builder.append("bytes");
                break;
        }
        builder.trimToSize();

        return builder.toString();
    }

    private void createCommandResultHandler() {
        new Thread(() -> {
            while (true) {
                String resultCommand = networkService.readCommandResult();
                Platform.runLater(() -> commandResultTextArea.appendText(resultCommand + System.lineSeparator()));
            }
        }).start();
    }


    public void shutdown() {
        networkService.closeConnection();
    }

    public void exitTheApplication(ActionEvent actionEvent) {
        shutdown();
        Platform.exit();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {

            FileInfo fileInfo = (FileInfo) clientView.getSelectionModel().getSelectedItem();

            if (fileInfo.getType() == FileType.DIR || fileInfo.getType() == FileType.APARENT) {
                changeLocalDirectoryAndRefresh(fileInfo);
            }

            if (fileInfo.getType() == FileType.FILE) {
                transferToServer(fileInfo);
            }
        }
    }

    private void changeLocalDirectoryAndRefresh(FileInfo fileInfo) {
        Path path = fileInfo.getPath();
        boolean pathIsRoot = false;
        if (fileInfo.getType() == FileType.APARENT){
            if (path.getNameCount() > 1){
                path = path.getParent();
            } else {
                pathIsRoot = true;
            }
        }
        clientView.getItems().clear();
        if (!pathIsRoot) {
            clientView.getItems().add(0, new FileInfo(Paths.get(path.toString(), "..")));
        }
        clientView.getItems().addAll(getFilesDirectories(path));
        clientView.sort();
    }
    // TODO make file transfer
    private void transferToServer(FileInfo fileInfo) {
    }
}
