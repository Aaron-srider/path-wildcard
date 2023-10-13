package fit.wenchao;

import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder {

    List<Path> result;
    Path root;
    String dir;
    String wildcard;
    String relativeTargetSearchPath;
    String relativePattern;
    long count;

    @NotNull
    public List<Path> findFilesWithWildcard(
            @NotNull
            Path root,
            @NotNull
            String dir,
            @NotNull
            String wildcard
    ) {
        this.root = root;
        this.dir = dir;
        this.wildcard = wildcard;

        eliminateDoubleDotsInPaths();

        searchAndMatch();

        return result;
    }

    public void recycle() {
        result = null;
        root = null;
        dir = null;
        wildcard = null;
        relativeTargetSearchPath = null;
        relativePattern = null;
    }


    private void eliminateDoubleDotsInPaths() {
        removeDoubleDotsInWildcard();
        removeDoubleDotsInDir();
    }

    String patternWithoutIntermediateDoubleDots;

    private void removeDoubleDotsInWildcard() {
        resolveAllIntermediateDoubleDots();
        countHeadingDoubleDots();
        removeAllHeadingDoubleDots();
    }

    private void resolveAllIntermediateDoubleDots() {
        patternWithoutIntermediateDoubleDots = Paths.get(wildcard).normalize().toString();
    }

    private void countHeadingDoubleDots() {
        String[] split = patternWithoutIntermediateDoubleDots.split("/");
        count = Arrays.stream(split).filter(s -> s.equals("..")).count();
    }

    private void removeAllHeadingDoubleDots() {
        String[] split = patternWithoutIntermediateDoubleDots.split("/");
        relativePattern = Arrays.stream(split).filter(s -> !s.equals("..")).collect(Collectors.joining("/"));
    }


    private void removeDoubleDotsInDir() {
        var pathGoBack = new PathGoBack(root, count, Paths.get(dir));
        var targetSearchPath = pathGoBack.goBack();
        relativeTargetSearchPath = targetSearchPath.toString();
    }

    private class PathGoBack {
        Path root;
        long count;
        Path path;
        Path result;
        PathGoBack(Path root, long count, Path path) {
            this.root = root;
            this.count = count;
            this.path = path;
        }

        Path goBack() {
            normalizeBothPaths();
            doGoBack();
            return result;
        }

        private void doGoBack() {
            var relative = root.relativize(path).toString();
            relative = "/" + relative;
            for (long l = 0; l < count; l++) {
                relative = relative + "/..";
            }
            result = Paths.get(relative).normalize();
        }

        private void normalizeBothPaths(  ) {
            root = root.normalize().toAbsolutePath();
            path = path.normalize().toAbsolutePath();
        }
    }


    private void searchAndMatch() {
        var wildcardObj = new Wildcard(relativePattern);
        var absoluteTargetSearchPath = resolvePath(root, relativeTargetSearchPath);
        result = new ArrayList<Path>();
        try {
            Files.walkFileTree(absoluteTargetSearchPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(
                        Path file,
                        BasicFileAttributes attrs
                ) {
                    var fileAbosolutePath = file.normalize().toAbsolutePath();
                    var relative = absoluteTargetSearchPath.relativize(fileAbosolutePath).normalize();
                    if (wildcardObj.match(relative.toString())) {
                        result.add(absoluteTargetSearchPath.resolve(relative).normalize());
                    }
                    return FileVisitResult.CONTINUE; // Continue to the next file
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Path resolvePath(
            @NotNull
            Path root,
            @NotNull
            String target
    ) {
        return Paths.get(root.toAbsolutePath() + "/" + target).normalize();
    }
}
