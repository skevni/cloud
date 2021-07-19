package ru.gb.sklyarov.cloud.server.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.service.CommandDictionaryService;

public class CommandInboundHandler extends SimpleChannelInboundHandler<Command> {

    private final CommandDictionaryService dictionaryService;

    public CommandInboundHandler() {
        this.dictionaryService = Factory.getCommandDirectoryService();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) {
        String result = dictionaryService.processCommand(command);
        channelHandlerContext.writeAndFlush(result);
    }
}
