package ru.gb.sklyarov.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class FileInfo implements Serializable {

    private static final Logger log = LogManager.getLogger(FileInfo.class);

    private String name;
    private long size;
    private FileType type;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Path path;

    public FileInfo(Path path) {
        if (path.endsWith("..")){
            this.name = "..";
            this.size = -1L;
            this.type = FileType.APARENT;
            this.modified = LocalDateTime.of(1,1,1,0,0);
            this.created = LocalDateTime.of(1,1,1,0,0);
            this.path = path.getParent();
            return;
        }
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            this.name = path.getFileName().toString();
            this.size = attrs.size();
            this.type = attrs.isDirectory() ? FileType.DIR : FileType.FILE;
            if (this.type == FileType.DIR){
                this.size = -1L;
            }
            this.created = LocalDateTime.ofInstant(attrs.creationTime().toInstant(), ZoneId.systemDefault());
            this.modified = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
            this.path = path;

        } catch (IOException e) {
            log.error("Unable to get file info from path", e);
        }
    }
}
