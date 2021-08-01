package ru.gb.sklyarov.cloud.client.service.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.domain.Command;

@Log4j2
public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    @Override
    protected void channelRead0(ChannelHandlerContext context, Command command) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Error in command handler", cause);
    }
}
