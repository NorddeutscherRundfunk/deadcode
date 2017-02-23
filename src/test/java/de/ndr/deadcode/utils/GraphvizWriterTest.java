package de.ndr.deadcode.utils;

import de.ndr.deadcode.WebappProcessor;
import de.ndr.deadcode.result.WebappResult;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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

	@Ignore
	@Test
	public void testWrite() throws IOException, URISyntaxException {
		WebappProcessor processor = new WebappProcessor(50, new File(Test.class.getResource("/webapp").toURI()));
		WebappResult result = processor.process();
		
		GraphvizWriter.write(result, graphvizFile);
		
		File expectedFile = new File(Test.class.getResource("/graphviz/graphviz.dot").getFile());
		Assert.assertTrue(FileUtils.contentEquals(expectedFile, graphvizFile));
				
	}
	
}
