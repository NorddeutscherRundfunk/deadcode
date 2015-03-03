package de.ndr.deadcode.taglib;

public class TagdirTaglibEntry extends Taglib {

	private String tagdir;

	public TagdirTaglibEntry(String prefix, String tagdir) {
		super(prefix);
		this.tagdir = tagdir;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(prefix).append(": ").append(tagdir).toString();
	}
	
	public String getTagdir() {
		return tagdir;
	}
	
	@Override
	public String getTarget() {
		return tagdir;
	}
}
