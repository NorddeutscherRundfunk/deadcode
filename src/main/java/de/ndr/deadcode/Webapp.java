package de.ndr.deadcode;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.taglib.Tag;

public class Webapp {

	private SortedSet<Tag> tags = new TreeSet<Tag>();
	private String base = "/WEB-INF/tags";

	public Webapp(File path) {
		File directory = new File(path, base);
		Collection<File> tagFiles = FileUtils.listFiles(directory, new SuffixFileFilter("tag"), TrueFileFilter.INSTANCE);
		
		for (File tagFile : tagFiles) {
			String tagdir = base + "/" + tagFile.getParentFile().getName();
			String tagname = StringUtils.substringBefore(tagFile.getName(), ".");
			tags.add(new Tag(tagdir, tagname));
		}
	}

	public Set<Tag> getTags() {
		return tags ;
	}

}
