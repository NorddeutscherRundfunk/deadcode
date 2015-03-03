package de.ndr.deadcode.result;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.Taglib;

public class WebappResult {

	private File webappRoot;
	private List<FileResult> fileResults;
	private Set<Tag> usedTags = new LinkedHashSet<Tag>();
	private Set<Taglib> tagImports = new LinkedHashSet<Taglib>();
	private Set<Import> imports = new LinkedHashSet<Import>();
	private Set<JspPage> highCommentRatioPages = new LinkedHashSet<JspPage>();
	
	public WebappResult(File webappRoot) {
		this.webappRoot = webappRoot;
		this.fileResults = new ArrayList<FileResult>();
	}

	public File getWebappRoot() {
		return webappRoot;
	}
	
	public List<FileResult> getFileResults() {
		return fileResults;
	}
	
	public void addFileResults(FileResult fileResult) {
		fileResults.add(fileResult);
	}
	
	public Set<Taglib> getTaglibImports() {
		return tagImports;
	}
	
	public void addTaglibImports(Set<Taglib> taglibImports) {
		tagImports.addAll(taglibImports);
	}

	public Set<Tag> getUsedTags() {
		return usedTags;
	}
	
	public Set<JspPage> getHighCommentRatioPages() {
		return highCommentRatioPages;
	}
	
	public Set<Import> getImports() {
		return imports;
	}

	public void addImports(Set<Import> imports) {
		imports.addAll(imports);
	}

}
