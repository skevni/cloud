package ru.gb.sklyarov.cloud.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.network.handler.CommandInboundHandler;
import ru.gb.sklyarov.cloud.server.service.ServerService;
import ru.gb.sklyarov.cloud.server.util.PropertyUtil;

import java.util.Map;

public class NettyServerService implements ServerService {

    private static final Logger log = LogManager.getLogger("NettyServerService");

    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) {
                            channel.pipeline()
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new CommandInboundHandler());
                        }
                    });

            int server_port = getServerPortFromProperties();

            ChannelFuture future = bootstrap.bind(server_port).sync();
            log.info("The server is up and available on port: " + server_port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("The server has ended with an error and is unavailable");
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private int getServerPortFromProperties() {
        PropertyUtil propertyUtil = Factory.getProperty();
        Map<String, String> properties = propertyUtil.getAllProperties();
        return Integer.parseInt(properties.getOrDefault("server_port", "9000"));
    }
}
