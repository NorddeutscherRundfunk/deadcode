package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.Taglib;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StdoutOutputPluginTest extends AbstractOutputPluginTest {

    @Before
    public void setup() {
        outputPlugin = new StdoutOutputPlugin();
    }

    @Test
    public void testOutputUnusedTaglibImports() throws IOException {
        WebappResult webappResult = mock(WebappResult.class);
        List<FileResult> fileResults = new ArrayList<>();
        JspPage jspPage = new JspPage(new File(Test.class.getResource("/webapp/test.jsp").getFile()));
        Set<Import> imports = new HashSet<>();
        Set<Taglib> unusedTaglibImports = jspPage.getUnusedTaglibs();
        fileResults.add(new FileResult(jspPage, unusedTaglibImports, imports));
        when(webappResult.getFileResults()).thenReturn(fileResults);

        File webappRoot = new File(StringUtils.substringBeforeLast(new File(Test.class.getResource("/webapp").getFile()).getAbsolutePath(), "/"));

        outputPlugin.outputUnusedTaglibImports(webappResult, webappRoot);
        String expected = "Unused Taglib imports:\n" +
                "2/7 unused imports (gsa-ext, rx): /webapp/test.jsp\n";
        Assert.assertEquals(expected, outContent.toString());
    }

    @Test
    public void testOutputUnusedTags() {
        Collection<AbstractJSTLEntity> unusedTags = new ArrayList<>();

        outputPlugin.outputUnusedTags(unusedTags, definedTags);
        String expected = "Unused tags:\n" +
                "0/1 Tags unused.";
        Assert.assertEquals(expected, outContent.toString().trim());
    }

    @Test
    public void testOutputUnusedTags2() {
        Collection<AbstractJSTLEntity> unusedTags = new ArrayList<>();
        unusedTags.add(new Tag("/WEB-INF/tags/debug", "log"));

        outputPlugin.outputUnusedTags(unusedTags, definedTags);
        String expected = "Unused tags:\n" +
                "/WEB-INF/tags/debug: log\n" +
                "1/1 Tags unused.";
        Assert.assertEquals(expected, outContent.toString().trim());
    }
}


//    void outputUnusedTaglibImports(WebappResult result, File webappRoot);
//    void outputUnusedTags(Collection<AbstractJSTLEntity> unusedTags, Set<Tag> definedTags);
//    void outputDuplicateFiles(Map<String, Set<File>> duplicateFiles, File webappRoot);
//    void outputHighCommentRatioPages(Set<JspPage> highCommentRatioPages, File webappRoot, int commentRatio);