package de.ndr.deadcode;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.ndr.deadcode.imports.Import;
import de.ndr.deadcode.result.CommentedCodeInfo;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Function;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.TagdirTaglibEntry;
import de.ndr.deadcode.taglib.Taglib;
import de.ndr.deadcode.taglib.UriTaglibEntry;

public class JspPageTest {

	private JspPage jspPage;
	private JspPage jspPage2;

	@Before
	public void setup() throws IOException {
		jspPage = new JspPage(new File(Test.class.getResource("/webapp/test.jsp").getFile()));
		jspPage2 = new JspPage(new File(Test.class.getResource("/webapp/test2.jsp").getFile()));
	}
	
	@Test
	public void parseTaglibImports() {
		Set<Taglib> importedTaglibs = jspPage.getImportedTaglibs();
		Assert.assertThat(importedTaglibs.size(), is(7));
		
		Assert.assertTrue(importedTaglibs.contains(new UriTaglibEntry("ex", "http://www.example.com/example/jsp")));
		Assert.assertTrue(importedTaglibs.contains(new UriTaglibEntry("c", "http://java.sun.com/jsp/jstl/core")));
		Assert.assertTrue(importedTaglibs.contains(new UriTaglibEntry("rx", "http://jakarta.apache.org/taglibs/regexp-1.0")));
		Assert.assertTrue(importedTaglibs.contains(new UriTaglibEntry("fn", "http://java.sun.com/jsp/jstl/functions")));
		Assert.assertTrue(importedTaglibs.contains(new TagdirTaglibEntry("foo", "/WEB-INF/tags/foo")));
		Assert.assertTrue(importedTaglibs.contains(new TagdirTaglibEntry("test", "/WEB-INF/tags/test")));
		Assert.assertTrue(importedTaglibs.contains(new TagdirTaglibEntry("gsa-ext", "/WEB-INF/tags/gsa")));
	}
	
	@Test
	public void unusedImports() {
		Set<Taglib> unusedImports = jspPage.getUnusedTaglibs();

		Assert.assertThat(unusedImports.size(), is(2));
		
		Assert.assertTrue(unusedImports.contains(new UriTaglibEntry("rx", "http://jakarta.apache.org/taglibs/regexp-1.0")));
		Assert.assertTrue(unusedImports.contains(new TagdirTaglibEntry("gsa-ext", "/WEB-INF/tags/gsa")));
	}
	
	@Test
	public void parseUsedTags() {
		Set<AbstractJSTLEntity> usedEntities = jspPage.getUsedEntities();
		Assert.assertThat(usedEntities.size(), is(12));
		
		Assert.assertTrue(usedEntities.contains(new Tag("/WEB-INF/tags/foo", "getBar")));
		Assert.assertTrue(usedEntities.contains(new Tag("http://www.example.com/example/jsp", "addBar")));
		Assert.assertFalse(usedEntities.contains(new Tag("/WEB-INF/tags/foo", "displayBar")));
		Assert.assertTrue(usedEntities.contains(new Tag("/WEB-INF/tags/test", "testTag")));
		Assert.assertTrue(usedEntities.contains(new Tag("/WEB-INF/tags/test", "testTag2")));
		Assert.assertTrue(usedEntities.contains(new Tag("/WEB-INF/tags/test", "testTag3")));
		
		Assert.assertTrue(usedEntities.contains(new Tag("http://java.sun.com/jsp/jstl/core", "if")));
		Assert.assertTrue(usedEntities.contains(new Tag("http://java.sun.com/jsp/jstl/core", "when")));
		Assert.assertTrue(usedEntities.contains(new Tag("http://java.sun.com/jsp/jstl/core", "forEach")));
		Assert.assertTrue(usedEntities.contains(new Tag("http://java.sun.com/jsp/jstl/core", "choose")));
		
		Assert.assertTrue(usedEntities.contains(new Function("http://java.sun.com/jsp/jstl/functions", "trim")));
		Assert.assertTrue(usedEntities.contains(new Function("http://java.sun.com/jsp/jstl/functions", "length")));
		Assert.assertTrue(usedEntities.contains(new Function("http://java.sun.com/jsp/jstl/functions", "startsWith")));
	}
	
	@Test
	public void handleCommentedCode() {
		Set<Taglib> taglibImports = jspPage2.getImportedTaglibs();
		Assert.assertThat(taglibImports.size(), is(1));
	}
	
	@Test
	public void countCommentedCode() {
		CommentedCodeInfo commentedCodeInfo = jspPage2.getCommentedCodeInfo();
		Assert.assertThat(commentedCodeInfo.commentChars, is(238));
		Assert.assertThat(commentedCodeInfo.commentCount, is(3));
		Assert.assertThat(commentedCodeInfo.commentRatio, is(40));
	}
	
	@Test
	public void parseImports() {
		Set<Import> imports = jspPage2.getImports();
		Assert.assertThat(imports.size(), is(4));
		
		Assert.assertTrue(imports.contains(new Import("java.util.*")));
		Assert.assertTrue(imports.contains(new Import("java.util.Set")));
		Assert.assertTrue(imports.contains(new Import("java.util.List")));
		Assert.assertTrue(imports.contains(new Import("java.util.HashMap")));
	}
}
