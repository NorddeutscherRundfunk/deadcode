package de.ndr.deadcode.result;

import java.io.File;
import java.util.Set;

import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.taglib.Taglib;

public class FileResult {
	private File file;
	private Set<Taglib> unusedTaglibImports;
	private Set<Import> imports;
	
	public FileResult(File file, Set<Taglib> unusedTaglibImports, Set<Import> imports) {
		this.file = file;
		this.unusedTaglibImports = unusedTaglibImports;
		this.imports = imports;
	}
	
	public File getFile() {
		return file;
	}
	
	public Set<Taglib> getUnusedTaglibImports() {
		return unusedTaglibImports;
	}
	
	public Set<Import> getImports() {
		return imports;
	}
}