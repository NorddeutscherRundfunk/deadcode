package de.ndr.deadcode.taglib;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public abstract class Taglib {
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
	
	public abstract String getTarget();
}
