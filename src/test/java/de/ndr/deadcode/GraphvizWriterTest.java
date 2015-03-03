package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.utils.GraphvizWriter;

public class GraphvizWriterTest {

	private File graphvizFile;

	@Before
	public void setup() throws IOException {
		graphvizFile = File.createTempFile("GraphvizWriterTest", "dot");
	}
	
	@After
	public void cleanup() {
		FileUtils.deleteQuietly(graphvizFile);
	}
	
	@Test
	public void testWrite() throws IOException, URISyntaxException {
		WebappProcessor processor = new WebappProcessor(50);
		WebappResult result = processor.processWebapp(new File(Test.class.getResource("/webapp").toURI()));
		
		GraphvizWriter.write(result, graphvizFile);
		
		File expectedFile = new File(Test.class.getResource("/graphviz/graphviz.dot").getFile());
		Assert.assertTrue(FileUtils.contentEquals(expectedFile, graphvizFile));
				
	}
	
}
