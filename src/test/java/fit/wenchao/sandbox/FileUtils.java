package fit.wenchao.sandbox;

import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
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
        throwIfPathExists(path);

        doCreateFile(path);
    }

    private static void throwIfPathExists(String path) {
        if (Paths.get(path).toFile().exists()) {
            throw new FileExistsException("file exists: " + path);
        }
    }

    private static void doCreateFile(
            @NotNull
            String path
    ) {
        try {
            doCreateFile0(path);
        } catch (IOException e) {
            throw new RuntimeException("create file failed: " + path, e);
        }
    }

    private static void doCreateFile0( @NotNull
                                String path) throws IOException {
        var file = new File(path);
        var par = file.getParentFile();
        if (par != null) {
            par.mkdirs();
        }
        file.createNewFile();
    }


    public static void deleteDirectory(@NotNull String path) {
        // Specify the path of the directory to be deleted recursively
        Path directoryPath = Paths.get(path);

        try {
            // Use Files.walkFileTree to traverse the directory tree
            Files.walkFileTree(directoryPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Delete files
                    Files.delete(file);
                    System.out.println("Deleted file: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc == null) {
                        // Delete directories after all files have been deleted
                        Files.delete(dir);
                        System.out.println("Deleted directory: " + dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        // Directory iteration failed, report an error
                        throw exc;
                    }
                }
            });
        } catch (IOException e) {
            throw
                    new RuntimeException("delete directory failed: " + path, e);
        }
    }
}
