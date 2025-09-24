package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    public void backup(String sourceDir) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path source = Paths.get(sourceDir);
        Path backupDir = Paths.get("backups/backup_" + timestamp);

        Files.createDirectories(backupDir);
        Files.walk(source).forEach(path -> {
            try {
                Path dest = backupDir.resolve(source.relativize(path));
                if (Files.isDirectory(path)) {
                    Files.createDirectories(dest);
                } else {
                    Files.copy(path, dest, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Backup completed at " + backupDir.toAbsolutePath());
    }
}
