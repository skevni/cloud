package ru.gb.sklyarov.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileInfo implements Serializable {

    private static final Logger log = LogManager.getLogger(FileInfo.class);

    private String name;
    private long size;
    private FileType type;
    private FileTime created;
    private FileTime modified;

    public FileInfo(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            this.name = path.getFileName().toString();
            this.size = attrs.size();
            this.type = attrs.isDirectory() ? FileType.DIR : FileType.FILE;
            this.created = attrs.creationTime();
            this.modified = attrs.lastModifiedTime();

        } catch (IOException e) {
            log.error("Unable to get file info from path", e);
        }
    }
}
