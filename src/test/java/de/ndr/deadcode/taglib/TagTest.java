package de.ndr.deadcode.taglib;

import java.util.Iterator;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import de.ndr.deadcode.taglib.Tag;

public class TagTest {
	
	@Test
	public void testCompare() {
		TreeSet<Tag> tags = new TreeSet<Tag>();
		
		tags.add(new Tag("/WEB-INF/tags/epg", "getImage"));
		tags.add(new Tag("/WEB-INF/tags/epg", "getSendungen"));
		tags.add(new Tag("/WEB-INF/tags/epg", "checkMasterID"));
		tags.add(new Tag("/WEB-INF/tags/epg", "getSidForTime"));
		tags.add(new Tag("/WEB-INF/tags/epg", "getPrimaryImage"));
		tags.add(new Tag("/WEB-INF/tags/epg", "datumToTimestamp"));
		tags.add(new Tag("/WEB-INF/tags/gsa", "weatherOneBox"));
		tags.add(new Tag("/WEB-INF/tags/gsa", "dynamicNavigation"));
		tags.add(new Tag("/WEB-INF/tags/gsa", "buildRequiredFieldsParam"));
		tags.add(new Tag("/WEB-INF/tags/osc", "sinplu"));
		tags.add(new Tag("/WEB-INF/tags/osc", "getDateFromImageExif"));
		tags.add(new Tag("/WEB-INF/tags/debug", "log"));
		tags.add(new Tag("/WEB-INF/tags/avmeta", "createSlideshowXml"));
		
		Iterator<Tag> iter = tags.iterator();
		Assert.assertEquals(new Tag("/WEB-INF/tags/avmeta", "createSlideshowXml"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/debug", "log"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "checkMasterID"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "datumToTimestamp"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "getImage"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "getPrimaryImage"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "getSendungen"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/epg", "getSidForTime"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/gsa", "buildRequiredFieldsParam"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/gsa", "dynamicNavigation"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/gsa", "weatherOneBox"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/osc", "getDateFromImageExif"), iter.next());
		Assert.assertEquals(new Tag("/WEB-INF/tags/osc", "sinplu"), iter.next());
	}
}
