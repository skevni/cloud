package ru.gb.sklyarov.cloud.client.service.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.cloud.client.config.PropertyConfig;
import ru.gb.sklyarov.cloud.client.service.NetworkService;
import ru.gb.sklyarov.cloud.client.service.handler.CommandHandler;
import ru.gb.sklyarov.domain.Command;

import java.io.IOException;

@Log4j2
public class NettyNetworkService implements NetworkService {

    private static NettyNetworkService instance;
    private static SocketChannel socketChannel;

    private NettyNetworkService() {
    }

    public static NettyNetworkService getInstance() {
        if (instance == null) {
            instance = new NettyNetworkService();
            initializeInstance();
        }

        return instance;
    }

    private static void initializeInstance() {

        Thread thread = new Thread(() -> {
            EventLoopGroup workgroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workgroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                socketChannel = channel;
                                channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                        .addLast(new ObjectEncoder())
                                        .addLast(new CommandHandler());
                            }
                        });
                ChannelFuture future = bootstrap.connect(PropertyConfig.getServerHost(), PropertyConfig.getServerPort()).sync();
                log.info("Connecting to server host " + PropertyConfig.getServerHost() + " on port " + PropertyConfig.getServerPort());
                future.channel().closeFuture().sync();
            } catch (Exception ex) {
                log.error("Error initializing the netty client service instance", ex);
            } finally {
                workgroup.shutdownGracefully();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void sendCommand(Command command) {

        log.debug("Command used: " + command.toString());
        socketChannel.writeAndFlush(command);

    }

    @Override
    public void sendFile(String path) {

    }

    @Override
    public void downloadFile(String path) {

    }

    @Override
    public void closeConnection() {
        if (socketChannel != null && !socketChannel.isShutdown()) {
            socketChannel.close();
        }
    }

}
