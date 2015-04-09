package de.ndr.deadcode.taglib;

import java.util.Iterator;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import de.ndr.deadcode.taglib.Tag;

public class TagTest {
	
	@Test
	public void testCompare() {
		TreeSet<Tag> tags = new TreeSet<>();
		
		tags.add(new Tag("/WEB-INF/tags/epg", "getImage"));
		tags.add(new Tag("/WEB-INF/tags/gsa", "dynamicNavigation"));
		tags.add(new Tag("/WEB-INF/tags/debug", "log"));
		
		Iterator<Tag> iter = tags.iterator();
		Assert.assertEquals(new Tag("/WEB-INF/tags/debug", "log"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "getImage"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/gsa", "dynamicNavigation"), iter.next());
	}
}
