package fit.wenchao;

import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

    @NotNull
    private static final Logger log = Objects.requireNonNull(LoggerFactory.getLogger(FileUtils.class));

    @NotNull
    public static String getContent(
            @NotNull
            String path
    ) {
        try (
                var in = new BufferedInputStream(
                        Files.newInputStream(Paths.get(path))
                );
                var out = new ByteArrayOutputStream()
        ) {
            var buffer = new byte[1024];
            var len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toString();
        } catch (Throwable e) {
            throw new RuntimeException("read file content failed", e);
        }
    }

    public static void saveContent(
            @NotNull
            String filePath,
            @NotNull
            String content
    ) {

        try (
                var out = new BufferedOutputStream(
                        Files.newOutputStream(Paths.get(filePath))
                )
        ) {
            out.write(content.getBytes());
        } catch (Throwable e) {
            throw new RuntimeException("write file content failed", e);
        }
    }

    /**
     * Create file along with all parents if not exist
     *
     * @param path file path
     */
    public static void createFile(
            @NotNull
            String path
    ) {
        if (!Paths.get(path).toFile().exists()) {
            try {
                var file = new File(path);
                var par = file.getParentFile();
                // the null assertion is necessary
                // parent("/") === null
                // parent("test") === null
                // parent("test/file.json") === file("test")
                if (par != null) {
                    par.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("create file failed: " + path, e);
            }
        }
    }
}
