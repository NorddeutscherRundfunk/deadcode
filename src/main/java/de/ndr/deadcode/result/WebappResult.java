package de.ndr.deadcode.result;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Taglib;

public class WebappResult {

	private File webappRoot;
	private List<FileResult> fileResults = new ArrayList<>();
	private Set<AbstractJSTLEntity> usedEntities = new LinkedHashSet<>();
	private Set<Taglib> taglibImports = new TreeSet<>();
	private Set<Import> imports = new LinkedHashSet<>();
	private Set<JspPage> highCommentRatioPages = new LinkedHashSet<>();
	
	public WebappResult(File webappRoot) {
		this.webappRoot = webappRoot;
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
