package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.Taglib;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StdoutOutputPlugin implements IOutputPlugin {

    @Override
    public void outputUnusedTaglibImports(WebappResult result, File webappRoot) {
        System.out.println("Unused Taglib imports:");
        for (FileResult fileResult : result.getFileResults()) {
            Set<Taglib> unusedTaglibImports = fileResult.getUnusedTaglibImports();
            if (!unusedTaglibImports.isEmpty()) {
                Set<String> prefixes = unusedTaglibImports.stream()
                        .map(Taglib::getPrefix)
                        .collect(Collectors.toSet());

                System.out.format("%d/%d unused imports (%s): %s\n",
                        unusedTaglibImports.size(), fileResult.getJspPage().getImportedTaglibs().size(),
                        String.join(", ", prefixes),
                        StringUtils.substringAfter(fileResult.getJspPage().getFile().getAbsolutePath(), webappRoot.getAbsolutePath()));
            }
        }
    }

    @Override
    public void outputUnusedTags(Collection<AbstractJSTLEntity> unusedTags, Set<Tag> definedTags) {
        System.out.println("Unused tags:");
        unusedTags.forEach(System.out::println);
        System.out.println(unusedTags.size() + "/" + definedTags.size() + " Tags unused.");
    }

    @Override
    public void outputDuplicateFiles(Map<String, Set<File>> duplicateFiles, File webappRoot) {
        System.out.println("\nDuplicateFiles:");
        duplicateFiles.values().forEach(v -> {
            Set<String> files = v.stream()
                    .map((f) -> StringUtils.substringAfter(f.getAbsolutePath(), webappRoot.getAbsolutePath()))
                    .collect(Collectors.toSet());
            System.out.format("%d: %s\n", files.size(),	String.join(", ", files));
        });
    }

    @Override
    public void outputHighCommentRatioPages(Set<JspPage> highCommentRatioPages, File webappRoot, int commentRatio) {
        System.out.format("HighCommentRatioPages (> %d%%):", commentRatio);
        for (JspPage page : highCommentRatioPages) {
            System.out.format("%s: %d\n",
                    StringUtils.substringAfter(page.getFile().getAbsolutePath(), webappRoot.getAbsolutePath()),
                    page.getCommentedCodeInfo().commentRatio);
        }
    }

}
