package de.ndr.deadcode.result;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.Taglib;

public class WebappResult {

	private File webappRoot;
	private List<FileResult> fileResults;
	private Set<AbstractJSTLEntity> usedEntities = new LinkedHashSet<AbstractJSTLEntity>();
	private Set<Taglib> taglibImports = new LinkedHashSet<Taglib>();
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
		return taglibImports;
	}
	
	public void addTaglibImports(Set<Taglib> taglibImports) {
		this.taglibImports.addAll(taglibImports);
	}

	public Set<AbstractJSTLEntity> getUsedEntities() {
		return usedEntities;
	}
	
	public Set<JspPage> getHighCommentRatioPages() {
		return highCommentRatioPages;
	}
	
	public Set<Import> getImports() {
		return imports;
	}

	public void addImports(Set<Import> imports) {
		this.imports.addAll(imports);
	}

	public void addUsedEntities(Set<AbstractJSTLEntity> usedEntities) {
		this.usedEntities.addAll(usedEntities);
	}
}
