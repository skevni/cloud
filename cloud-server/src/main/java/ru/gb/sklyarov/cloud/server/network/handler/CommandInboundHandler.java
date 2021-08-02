package ru.gb.sklyarov.cloud.server.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.service.CommandDictionaryService;

@Log4j2
public class CommandInboundHandler extends SimpleChannelInboundHandler<Command> {

    private final CommandDictionaryService dictionaryService;

    public CommandInboundHandler() {
        this.dictionaryService = Factory.getCommandDirectoryService();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) {
        Command result = dictionaryService.processCommand(command);
        channelHandlerContext.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Command handler error", cause);
    }
}
