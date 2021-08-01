package ru.gb.sklyarov.cloud.client.service.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import ru.gb.sklyarov.cloud.client.service.PipelineService;
import ru.gb.sklyarov.cloud.client.service.handler.CommandHandler;
import ru.gb.sklyarov.cloud.client.service.handler.FileWriteHandler;

public class PipelineServiceImpl implements PipelineService {
    @Override
    public void switchToFileTransfer(ChannelHandlerContext context) {

        context.pipeline().addLast(new ChunkedWriteHandler())
                .addLast( new FileWriteHandler());
        context.pipeline().remove(ObjectEncoder.class);
        context.pipeline().remove(ObjectDecoder.class);
        context.pipeline().remove(CommandHandler.class);

    }

    @Override
    public void switchToCommand(ChannelHandlerContext context) {
        context.pipeline().remove(ChunkedWriteHandler.class);
        context.pipeline().remove(FileWriteHandler.class);
        context.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                new ObjectEncoder(),
                new CommandHandler());

    }

    @Override
    public void clearChannel(SocketChannel channel) {

    }
}
