package de.ndr.deadcode.taglib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class Taglib implements Comparable<Taglib> {
	
	private String prefix;
	private String target;

	public Taglib(String prefix, String target) {
		this.prefix = prefix;
		this.target = target;
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
	
	public String getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", prefix, target);
	}
}
