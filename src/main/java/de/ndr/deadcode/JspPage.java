package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.result.CommentedCodeInfo;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.TagdirTaglibEntry;
import de.ndr.deadcode.taglib.Taglib;
import de.ndr.deadcode.taglib.UriTaglibEntry;

public class JspPage {
	
	private File file;
	private Set<Tag> usedTags = new HashSet<Tag>();
	private Set<Taglib> importedTaglibs = new HashSet<Taglib>();
	private Set<Taglib> unusedTaglib = new HashSet<Taglib>();
	private Set<Import> imports = new HashSet<Import>();
	private CommentedCodeInfo commentedCodeInfo = new CommentedCodeInfo();
	
	private static final Pattern COMMENT_PATTERN;
	private static final Pattern[] URI_PATTERNS;
	private static final Pattern[] TAGDIR_PATTERNS;
	private static final Pattern[] IMPORT_PATTERNS;
	
	static {
		COMMENT_PATTERN = Pattern.compile("<%--.*?--%>", Pattern.DOTALL);
		
		URI_PATTERNS = new Pattern[] {
				Pattern.compile("<%@\\s*taglib\\s+uri\\s*=\\s*\"(?<uri>[^\"]*)\"\\s+prefix\\s*=\\s*\"(?<prefix>[^\"]*)\"\\s*%>", Pattern.MULTILINE | Pattern.DOTALL),
				Pattern.compile("<%@\\s*taglib\\s+prefix\\s*=\\s*\"(?<prefix>[^\"]*)\"\\s+uri\\s*=\\s*\"(?<uri>[^\"]*)\"\\s*%>", Pattern.MULTILINE | Pattern.DOTALL)
		};
		
		TAGDIR_PATTERNS = new Pattern[] {
				Pattern.compile("<%@\\s*taglib\\s+tagdir\\s*=\\s*\"(?<tagdir>[^\"]*)\"\\s+prefix\\s*=\\s*\"(?<prefix>[^\"]*)\"\\s*%>", Pattern.MULTILINE | Pattern.DOTALL),
				Pattern.compile("<%@\\s*taglib\\s+prefix\\s*=\\s*\"(?<prefix>[^\"]*)\"\\s+tagdir\\s*=\\s*\"(?<tagdir>[^\"]*)\"\\s*%>", Pattern.MULTILINE | Pattern.DOTALL)
		};
		
		IMPORT_PATTERNS = new Pattern[] {
				Pattern.compile("<%@\\s*page\\s+import\\s*=\\s*\"(?<target>[^\"]+)\"\\s?%>", Pattern.MULTILINE | Pattern.DOTALL)
		};
	}

	public JspPage(File file) throws IOException {
		this.file = file;
		String fileContent = FileUtils.readFileToString(file);
		findCommentedCode(fileContent);
		fileContent = COMMENT_PATTERN.matcher(fileContent).replaceAll("");
		
		// Find URI-Taglib imports, e.g. <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		for (Pattern pattern : URI_PATTERNS) {
			Matcher matcher = pattern.matcher(fileContent);
			while (matcher.find()) {
				UriTaglibEntry entry = new UriTaglibEntry(matcher.group("prefix"), matcher.group("uri"));
				importedTaglibs.add(entry);
			}
		}
		
		// Find tagdir-Taglib imports, e.g. <%@ taglib tagdir="/WEB-INF/tags/foo" prefix="foo" %>
		for (Pattern pattern : TAGDIR_PATTERNS) {
			Matcher matcher = pattern.matcher(fileContent);
			while (matcher.find()) {
				TagdirTaglibEntry entry = new TagdirTaglibEntry(matcher.group("prefix"), matcher.group("tagdir"));
				importedTaglibs.add(entry);
			}
		}
		
		// TODO: Check for unused imports
		// Find Java-Imports imports, e.g. <%@ page import="java.util.Collection" %>
		for (Pattern pattern : IMPORT_PATTERNS) {
			Matcher matcher = pattern.matcher(fileContent);
			while (matcher.find()) {
				Import imprt = new Import(matcher.group("target"));
				imports.add(imprt);
			}
		}
		
		// Find usedTags in fileContent
		for (Taglib taglib : importedTaglibs) {
			Pattern pattern = Pattern.compile("<" + taglib.getPrefix() + ":(?<tagname>[^>/\\s]*)[^/>]*", Pattern.MULTILINE);
			Matcher matcher = pattern.matcher(fileContent);
			while (matcher.find()) {
				String tagname = matcher.group("tagname");
				if (taglib instanceof TagdirTaglibEntry) {
					usedTags.add(new Tag(((TagdirTaglibEntry) taglib).getTagdir(), tagname));
				} else if (taglib instanceof UriTaglibEntry) {
					usedTags.add(new Tag(((UriTaglibEntry) taglib).getUri(), tagname));
				}
			}
		}
		
		// Find unused imports by checking each imported taglib for occurence in fileContent
		List<String> lines = FileUtils.readLines(file);
		unusedTaglib = new HashSet<Taglib>(importedTaglibs);
		unusedTaglib.removeIf(t -> lines.stream()
								.anyMatch(l -> l.contains("<" + t.getPrefix() + ":")));
	}

	private void findCommentedCode(String fileContent) {
		if (!StringUtils.isEmpty(fileContent)) {
			Matcher matcher = COMMENT_PATTERN.matcher(fileContent);
			while (matcher.find()) {
				commentedCodeInfo.commentChars += matcher.toMatchResult().end() - matcher.toMatchResult().start();
				commentedCodeInfo.commentCount++;
			}
			commentedCodeInfo.commentRatio = commentedCodeInfo.commentChars * 100 / fileContent.length();
		}
	}

	public Set<Taglib> getUnusedTaglibs() {
		return unusedTaglib;
	}
	
	public Set<Taglib> getImportedTaglibs() {
		return importedTaglibs;
	}

	public Set<Tag> getUsedTags() {
		return usedTags;
	}

	public CommentedCodeInfo getCommentedCodeInfo() {
		return commentedCodeInfo;
	}
	
	@Override
	public String toString() {
		return file.getAbsolutePath();
	}

	public Set<Import> getImports() {
		return imports;
	}

	public File getFile() {
		return file;
	}
}
