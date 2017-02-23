package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.plugins.IPlugin;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IOutputPlugin extends IPlugin {

    void outputUnusedTaglibImports(WebappResult result, File webappRoot);
    void outputUnusedTags(Collection<AbstractJSTLEntity> unusedTags, Set<Tag> definedTags);
    void outputDuplicateFiles(Map<String, Set<File>> duplicateFiles, File webappRoot);
    void outputHighCommentRatioPages(Set<JspPage> highCommentRatioPages, File webappRoot, int commentRatio);

}
