package ru.gb.sklyarov.cloud.client.service.impl;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.cloud.client.factory.Factory;
import ru.gb.sklyarov.cloud.client.service.NetworkService;
import ru.gb.sklyarov.cloud.client.util.PropertyUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class IONetworkService implements NetworkService {

    public static Socket socket;
    public static ObjectDecoderInputStream in;
    public static ObjectEncoderOutputStream out;
    private static IONetworkService instance;

    private IONetworkService() {
    }

    public static IONetworkService getInstance() {
        if (instance == null) {
            instance = new IONetworkService();

            initializeSocket();
            initializeIOStreams();
        }

        return instance;
    }

    private static void initializeSocket() {
        try {
            PropertyUtil propertyUtil = Factory.getProperty();
            Map<String, String> properties = propertyUtil.getAllProperties();

            socket = new Socket(properties.getOrDefault("server_host","localhost"),
                    Integer.parseInt(properties.getOrDefault("server_port","9000")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeIOStreams() {
        try {
            in = new ObjectDecoderInputStream(socket.getInputStream());
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommand(Command command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readCommandResult() {
        try {
            return (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Read command result exception: " + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
