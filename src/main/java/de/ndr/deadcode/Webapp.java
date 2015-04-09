package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.taglib.Tag;

public class Webapp {

	private SortedSet<Tag> tags = new TreeSet<>();
	private Map<String, Set<File>> duplicateFileMap = new HashMap<>();
	private String base = "/WEB-INF/tags";

	public Webapp(File path) {
		File directory = new File(path, base);
		Collection<File> tagFiles = FileUtils.listFiles(directory, new SuffixFileFilter("tag"), TrueFileFilter.INSTANCE);
		
		for (File tagFile : tagFiles) {
			String tagdir = base + "/" + tagFile.getParentFile().getName();
			String tagname = StringUtils.substringBefore(tagFile.getName(), ".");
			tags.add(new Tag(tagdir, tagname));
		}
		
		Collection<File> allFiles = FileUtils.listFiles(path, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : allFiles) {
			try {
				String sha1Hex = DigestUtils.sha1Hex(FileUtils.readFileToByteArray(file));
				duplicateFileMap.computeIfAbsent(sha1Hex, v -> new HashSet<File>()).add(file);
			} catch (IOException e) {
				System.err.format("Could not hash file '%s': %s\n", file, e.getMessage());
			}
		}
		duplicateFileMap.entrySet().removeIf(e -> e.getValue().size() == 1);
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Map<String, Set<File>> getDuplicateFiles() {
		return duplicateFileMap;
	}
}
