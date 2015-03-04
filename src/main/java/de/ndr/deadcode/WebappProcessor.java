package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.Taglib;

public class WebappProcessor {

	private int commentRatio;
	private File webappRoot;

	public WebappProcessor(int commentRatio, File webappRoot) {
		this.commentRatio = commentRatio;
		this.webappRoot = webappRoot;
	}

	public WebappResult process() {
		WebappResult result = new WebappResult(webappRoot);
		Collection<File> files = FileUtils.listFiles(webappRoot, new SuffixFileFilter(new String[] {"jsp", "tag"}), TrueFileFilter.INSTANCE);
		
		for (File file : files) {
			try {
				JspPage page = new JspPage(file);
							
				Set<Taglib> unusedImports = page.getUnusedTaglibs();
				FileResult fileResult = new FileResult(page, unusedImports, page.getImports());
				result.addFileResults(fileResult);
				result.addTaglibImports(page.getImportedTaglibs());
				result.addUsedTags(page.getUsedTags());
				
				if (page.getCommentedCodeInfo().commentRatio > commentRatio) {
					result.getHighCommentRatioPages().add(page);
				}
			} catch (IOException e) {
				System.err.format("Could not parse file '%s': %s\n", file, e.getMessage());
			}
		}
		return result;
	}
}
