package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class NoopOutputPlugin implements IOutputPlugin {

    @Override
    public void outputUnusedTaglibImports(WebappResult result, File webappRoot) {
    }

    @Override
    public void outputUnusedTags(Collection<AbstractJSTLEntity> unusedTags, Set<Tag> definedTags) {
    }

    @Override
    public void outputDuplicateFiles(Map<String, Set<File>> duplicateFiles, File webappRoot) {
    }

    @Override
    public void outputHighCommentRatioPages(Set<JspPage> highCommentRatioPages, File webappRoot, int commentRatio) {
    }

}
