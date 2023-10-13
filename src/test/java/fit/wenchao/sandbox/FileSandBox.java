package fit.wenchao.sandbox;

import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FileSandBox {

    @NotNull
    private final Logger log = Objects.requireNonNull(LoggerFactory.getLogger(FileSandBox.class));

    private Path absoluteRootDir;

    public FileSandBox() {
        try {
            absoluteRootDir = Files.createTempDirectory("fordebug").toAbsolutePath();
            log.debug("create sand box rootDir: {}", absoluteRootDir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Path getAbsoluteRootDir() {
        return absoluteRootDir;
    }


    public void doThings(Consumer<FileSandBox> c) {
        try {
            c.accept(this);
        } finally {
            try {
                FileUtils.deleteDirectory(absoluteRootDir.toAbsolutePath().toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void createFile(String file) {
        var p = getNormalizedPath(file).toString();
        try {
            FileUtils.createFile(p);
            log.debug("create file in sand box: {}", file);
        } catch (FileExistsException e) {
            log.debug("file already exists: {}", file);
        }
    }

    public boolean fileExists(
            @NotNull
            String s
    ) {
        var p = absoluteRootDir.toString() + "/" + s;
        var pp = Paths.get(p).normalize();
        return Files.exists(pp);
    }

    public boolean isRegFile(
            @NotNull
            String s
    ) {
        var p = absoluteRootDir.toString() + "/" + s;
        var pp = Paths.get(p).normalize();
        return Files.isRegularFile(pp);
    }

    public boolean isDir(
            @NotNull
            String s
    ) {
        var p = absoluteRootDir.toString() + "/" + s;
        var pp = Paths.get(p).normalize();
        return Files.isDirectory(pp);
    }

    public boolean isSym(
            @NotNull
            String s
    ) {
        var p = absoluteRootDir.toString() + "/" + s;
        var pp = Paths.get(p).normalize();
        return Files.isSymbolicLink(pp);
    }

    public void prepareFiles(
            @NotNull
            String... files
    ) {
        for (var file : files) {
            createFile(file);
        }
    }

    public void prepareFiles(
            @NotNull
            List<String> files
    ) {
        for (var file : files) {
            createFile(file);
        }
    }

    @NotNull
    public String get(
            @NotNull
            String s
    ) {
        return Paths.get(absoluteRootDir.toString() + "/" + s).normalize().toString();
    }

    @NotNull
    public Path getNormalizedPath(
            @NotNull
            String s
    ) {
        return Paths.get(absoluteRootDir.toString() + "/" + s).normalize();
    }

    public Path goBackTillRoot(long count,
                               @NotNull
                               Path path
    ) {

        var relative = absoluteRootDir.relativize(path.normalize());
        var p = relative.toString();
        p = "/" + p;
        for (long l = 0; l < count; l++) {
            p = p + "/..";
        }
        var pp = Paths.get(p).normalize();
        return getNormalizedPath(pp.toString());
    }
}
