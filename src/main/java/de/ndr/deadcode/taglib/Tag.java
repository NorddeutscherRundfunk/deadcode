package de.ndr.deadcode.taglib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Tag implements Comparable<Tag> {

	private String tagdir;
	private String tagname;

	public Tag(String tagdir, String tagname) {
		this.tagdir = tagdir;
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(tagdir).append(": ").append(tagname).toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public String getTagdir() {
		return tagdir;
	}
	
	public String getTagname() {
		return tagname;
	}

	public int compareTo(Tag o) {
		return new CompareToBuilder().append(tagdir, o.tagdir).append(tagname, o.tagname).toComparison();
	}
	
}
