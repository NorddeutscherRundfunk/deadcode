package de.ndr.deadcode.taglib;


public class UriTaglibEntry extends Taglib {

	private String uri;

	public UriTaglibEntry(String prefix, String uri) {
		super(prefix);
		this.uri = uri;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(prefix).append(": ").append(uri).toString();
	}
	
	public String getUri() {
		return uri;
	}

	@Override
	public String getTarget() {
		return uri;
	}
	
}
