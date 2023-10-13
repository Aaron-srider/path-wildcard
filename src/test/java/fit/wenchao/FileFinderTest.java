package fit.wenchao;

import fit.wenchao.sandbox.FileSandBox;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileFinderTest {
    @BeforeEach
    void setup() {
        preparedFileList.add("fordebug/test/buiABCld/file.data");
        preparedFileList.add("fordebug/test/build/file.data");
        preparedFileList.add("fordebug/TestA/file.data");
        preparedFileList.add("fordebug/TestB/file.data");
        preparedFileList.add("fordebug/TestCDEF/file.data");
        preparedFileList.add("fordebug/file.data");
        preparedFileList.add("TestA/sample.data");
        preparedFileList.add("TestB/file.data");
    }

    List<String> preparedFileList = new ArrayList<String>();

    @Test
    void testFileFinder() {
        var fileFinder = new FileFinder();
        var fileSandBox = new FileSandBox();

        fileSandBox.doThings((sandBox) -> {
            sandBox.prepareFiles(preparedFileList);

            var targetSearchPath = sandBox.getNormalizedPath("/ddd/ddd");

            List<Path> matchResults = fileFinder.findFilesWithWildcard(
                    sandBox.getAbsoluteRootDir(),
                    targetSearchPath.toString(),
                    "../Test?/*.data"
            );

            var expectedResults = new ArrayList<String>();
            expectedResults.add("TestA/sample.data");
            expectedResults.add("TestB/file.data");
            assertMatches(sandBox, matchResults, expectedResults);
        });
    }

    private void assertMatches(
            FileSandBox sandBox,
            List<Path> matchResults,
            List<String> expectedList
    ) {
        Assertions.assertTrue(matchResults.size() > 0);
        expectedList.forEach(relativeFilePath -> {
            var normalizedPath = sandBox.getNormalizedPath(relativeFilePath);
            Assertions.assertTrue(matchResults.contains(normalizedPath));
        });
    }
}
