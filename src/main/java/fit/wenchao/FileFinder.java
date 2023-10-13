package fit.wenchao;

import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileFinder {
    @NotNull
    public ArrayList<Path> findFilesWithWildcard(
            @NotNull
            String dir,
            @NotNull
            String wildcard
    ) {
        if (!Files.isDirectory(Paths.get(dir))) {
            return new ArrayList<>();
        }
        var wildcardObj = new Wildcard(wildcard);

        var result = new ArrayList<Path>();

        var dirPath = Paths.get(dir).normalize().toAbsolutePath();

        try {
            Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(
                        Path file,
                        BasicFileAttributes attrs
                ) throws IOException {
                    var fileAbosolutePath =file.normalize().toAbsolutePath();
                    var relative = dirPath.relativize(fileAbosolutePath).normalize();
                    if (wildcardObj.match(relative.toString())) {
                        result.add(dirPath.resolve(relative).normalize());
                    }
                    return FileVisitResult.CONTINUE; // Continue to the next file
                }
            });
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
