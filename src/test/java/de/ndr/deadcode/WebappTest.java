package de.ndr.deadcode;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		Set<Tag> usedTags = jspPage.getUsedTags();
		Set<Tag> definedTags = webapp.getTags();
		@SuppressWarnings("unchecked")
		Collection<Tag> unusedTags = CollectionUtils.subtract(definedTags, usedTags);
		
		Assert.assertThat(unusedTags.size(), is(2));
		
		Assert.assertTrue(unusedTags.contains(new Tag("/WEB-INF/tags/test", "test4")));
		Assert.assertTrue(unusedTags.contains(new Tag("/WEB-INF/tags/test", "test5")));
	}
}
