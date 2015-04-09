package de.ndr.deadcode.taglib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class AbstractJSTLEntity implements Comparable<AbstractJSTLEntity> {

	private String tagdir;
	private String name;
	
	public AbstractJSTLEntity(String tagdir, String name) {
		this.tagdir = tagdir;
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", tagdir, name);
	}

	public String getName() {
		return name;
	}

	public int compareTo(AbstractJSTLEntity o) {
		return new CompareToBuilder().append(tagdir, o.tagdir).append(name, o.name).toComparison();
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
}
