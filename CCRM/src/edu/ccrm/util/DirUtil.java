package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;

public class DirUtil {
    public static long getTotalSize(Path dir) throws IOException {
        final long[] size = {0};
        Files.walk(dir).filter(Files::isRegularFile).forEach(p -> {
            try {
                size[0] += Files.size(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return size[0];
    }
}
