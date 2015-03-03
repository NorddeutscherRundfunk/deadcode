package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.Taglib;

public class WebappProcessor {

	private int commentRatio;

	public WebappProcessor(int commentRatio) {
		this.commentRatio = commentRatio;
	}

	public WebappResult processWebapp(File webappRoot) {
		return processDirectory(webappRoot, new WebappResult(webappRoot));
	}
	
	private WebappResult processDirectory(File webappRoot, WebappResult result) {
		Collection<File> files = FileUtils.listFiles(webappRoot, new SuffixFileFilter(new String[] {"jsp", "tag"}), TrueFileFilter.INSTANCE);
		
		for (File file : files) {
			try {
				JspPage page = new JspPage(file);
							
				Set<Taglib> unusedImports = page.getUnusedTaglibs();
				FileResult fileResult = new FileResult(file, unusedImports, page.getImports());
				result.addFileResults(fileResult);
				result.addTaglibImports(page.getImportedTaglibs());

				if (!unusedImports.isEmpty()) {
					Set<String> prefixes = unusedImports.stream()
											.map((t) -> t.getPrefix())
											.collect(Collectors.toSet());

					System.out.format("%d/%d unused imports (%s): %s\n", 
							unusedImports.size(), 
							page.getImportedTaglibs().size(),
							StringUtils.join(prefixes, ", "),
							StringUtils.substringAfter(file.getAbsolutePath(), webappRoot.getAbsolutePath())
							);
				}
				result.getUsedTags().addAll(page.getUsedTags());
				
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
