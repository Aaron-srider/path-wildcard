package fit.wenchao;

import fit.wenchao.sandbox.FileSandBox;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinderTest {
    @Test
    void test() {
        var fileFinder = new FileFinder();
        var list = new ArrayList<String>();
        list.add("file.data");
        List<Path> result = fileFinder.findFilesWithWildcard("fordebug", "file.data");
        Assertions.assertTrue(result.size() > 0);
        result.forEach((file) -> {
            Assertions.assertTrue(list.contains(file.toString()));
        });
    }

    ArrayList<String> list = new ArrayList<String>();

    @BeforeEach
    void setup() {
        list.add("fordebug/test/buiABCld/file.data");
        list.add("fordebug/test/build/file.data");
        list.add("fordebug/TestA/file.data");
        list.add("fordebug/TestB/file.data");
        list.add("fordebug/TestCDEF/file.data");
        list.add("fordebug/file.data");
        list.add("sample.data");
    }

    @Test
    void test1() {
        var fileFinder = new FileFinder();
        var fileSandBox = new FileSandBox();

        fileSandBox.doThings((sandBox) -> {
            sandBox.prepareFiles(list);


            var targetPath = sandBox.getNormalizedPath("fordebug/jflas/hgkja.hfak");
            var np = Paths.get("../../*.data/../").normalize();
            String string = np.toString();
            String[] split = string.split("/");
            long count = Arrays.stream(split).filter(s -> s.equals("..")).count();
            var withoutdoubledots = Arrays.stream(split).filter(s -> !s.equals("..")).collect(Collectors.joining("/"));

            targetPath =   sandBox.goBackTillRoot(count, targetPath);
            System.out.println(withoutdoubledots);
            System.out.println(targetPath);
            System.out.println("jaklsdf");
            //var pattern = sandBox.getNormalizedPath();
            //
            //List<Path> matchResults = fileFinder.findFilesWithWildcard(targetPath.toString(), "../*.data");
            //
            //assertAllMatches(sandBox, matchResults, list);
        });
    }

    private void assertAllMatches(
            FileSandBox sandBox,
            List<Path> matchResults,
            ArrayList<String> list
    ) {
        Assertions.assertTrue(matchResults.size() > 0);
        list.forEach(file -> {
            var a = sandBox.getNormalizedPath(file);
            Assertions.assertTrue(matchResults.contains(a));
        });
    }
}
