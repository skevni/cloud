package ru.gb.sklyarov.cloud.client.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public interface PipelineService {
    void switchToCommand(ChannelHandlerContext context);

    void switchToFileTransfer(ChannelHandlerContext context);

    void clearChannel(SocketChannel channel);


}
