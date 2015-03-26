package de.ndr.deadcode.taglib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public abstract class Taglib implements Comparable<Taglib> {
	protected String prefix;

	public Taglib(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getPrefix() {
		return prefix;
	}
	
	@Override
	public int compareTo(Taglib o) {
		return new CompareToBuilder().append(this.prefix, o.prefix).toComparison();
	}
	
	public abstract String getTarget();
}
