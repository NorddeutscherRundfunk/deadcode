package de.ndr.deadcode.result;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.ndr.deadcode.JspPage;
import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.taglib.Taglib;

public class FileResult {
	private JspPage jspPage;
	private Set<Taglib> unusedTaglibImports;
	private Set<Import> imports;
	
	public FileResult(JspPage jspPage, Set<Taglib> unusedTaglibImports, Set<Import> imports) {
		this.jspPage = jspPage;
		this.unusedTaglibImports = unusedTaglibImports;
		this.imports = imports;
	}
	
	public JspPage getJspPage() {
		return jspPage;
	}
	
	public Set<Taglib> getUnusedTaglibImports() {
		return unusedTaglibImports;
	}
	
	public Set<Import> getImports() {
		return imports;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}