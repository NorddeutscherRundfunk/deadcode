package de.ndr.deadcode.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.taglib.Taglib;

public class GraphvizWriter {
	
	public static void write(WebappResult result, File graphvizFile) {
		if (graphvizFile == null) {
			return;
		}
		
		List<String> lines = new LinkedList<String>();
		lines.add("digraph {");
		lines.add("\trankdir=LR;");
		lines.add("\t");
		
		lines.add("\tsubgraph cluster_taglibs {");
		lines.add("\t\tlabel = \"taglibs\"");
		lines.add("\t\t");
		for (Taglib taglib : result.getTaglibImports()) {
			lines.add(String.format("\t\t\"%s\"", taglib.getTarget()));
		}
		lines.add("\t}");
		lines.add("\t");
		lines.add("\tsubgraph cluster_webapp {");
		lines.add("\t\tlabel = \"/\"");
		lines.add("\t\t");
		for (FileResult fileResult : result.getFileResults()) {
			String relativePath = StringUtils.substringAfter(fileResult.getFile().getAbsolutePath(), result.getWebappRoot().getAbsolutePath());
			for (Taglib taglibImport : result.getTaglibImports()) {
				if (fileResult.getUnusedTaglibImports().contains(taglibImport)) {
					String line = String.format(
							"\t\t\"%s\" -> \"%s\" [color=red, label=<<FONT COLOR=\"red\">%s</FONT>>]", 
							relativePath, taglibImport.getTarget(), taglibImport.getPrefix());
					lines.add(line);
				}
			}
		}	
		
		lines.add("\t}");
		lines.add("}");
		
		try {
			FileUtils.writeLines(graphvizFile, lines);
		} catch (IOException e) {
			System.err.format("Could not write to '%s': %s\n", graphvizFile, e.getMessage());
		}
	}
}
