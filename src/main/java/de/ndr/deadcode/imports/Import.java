package de.ndr.deadcode.imports;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Import implements Comparable<Import> {

	private String target;

	public Import(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(target).toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public int compareTo(Import o) {
		return new CompareToBuilder().append(target, o.target).toComparison();
	}

}
