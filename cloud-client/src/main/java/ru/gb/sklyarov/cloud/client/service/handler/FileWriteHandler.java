package ru.gb.sklyarov.cloud.client.service.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileWriteHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LogManager.getLogger(FileWriteHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.debug("Preparing to upload the file");
    }
}
