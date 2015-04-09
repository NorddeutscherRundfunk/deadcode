package de.ndr.deadcode;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;

public class WebappTest {

	private Webapp webapp;

	@Before
	public void setup() throws IOException {
		webapp = new Webapp(new File(Test.class.getResource("/webapp").getFile()));
	}
	
	@Test
	public void collectTags() throws Exception {
		Set<Tag> tags = webapp.getTags();
		Assert.assertThat(tags.size(), is(3));
		
		Assert.assertTrue(tags.contains(new Tag("/WEB-INF/tags/foo", "getBar")));
		Assert.assertTrue(tags.contains(new Tag("/WEB-INF/tags/test", "test4")));
		Assert.assertTrue(tags.contains(new Tag("/WEB-INF/tags/test", "test5")));
	}
	
	@Test
	public void findUnusedTags() throws Exception {
		JspPage jspPage = new JspPage(new File(Test.class.getResource("/webapp/test.jsp").getFile()));
		Set<AbstractJSTLEntity> usedEntities = jspPage.getUsedEntities();
		Set<Tag> definedTags = webapp.getTags();
		Collection<AbstractJSTLEntity> unusedTags = CollectionUtils.subtract(definedTags, usedEntities);
		
		Assert.assertThat(unusedTags.size(), is(2));
		
		Assert.assertTrue(unusedTags.contains(new Tag("/WEB-INF/tags/test", "test4")));
		Assert.assertTrue(unusedTags.contains(new Tag("/WEB-INF/tags/test", "test5")));
	}
	
	@Test
	public void findDuplicateFiles() {
		Map<String, Set<File>> duplicateFiles = webapp.getDuplicateFiles();
		Assert.assertTrue(duplicateFiles.containsKey("da39a3ee5e6b4b0d3255bfef95601890afd80709"));
		Assert.assertThat(duplicateFiles.size(), is(1));
		
		Set<File> files = duplicateFiles.get("da39a3ee5e6b4b0d3255bfef95601890afd80709");
		Assert.assertThat(files.size(), is(3));
		Assert.assertTrue(files.contains(new File(Test.class.getResource("/webapp/WEB-INF/tags/test/test4.tag").getFile())));
		Assert.assertTrue(files.contains(new File(Test.class.getResource("/webapp/WEB-INF/tags/test/test5.tag").getFile())));
		Assert.assertTrue(files.contains(new File(Test.class.getResource("/webapp/WEB-INF/tags/foo/getBar.tag").getFile())));
	}
}
