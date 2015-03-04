package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;

public class WebappProcessorTest {

	private WebappResult result;

	@Before
	public void setup() throws IOException {
		WebappProcessor webappProcessor = new WebappProcessor(30, new File(Test.class.getResource("/webapp").getFile()));
		result = webappProcessor.process();
	}
	
	@Test
	public void fileResults() {
		Assert.assertTrue(result.getFileResults().size() == 5);
	}
	
	@Test
	public void unusedImports() {
		Map<String, Integer> unusedTaglibImportsMap = new HashMap<String, Integer>();
		unusedTaglibImportsMap.put("test4.tag", 0);
		unusedTaglibImportsMap.put("test5.tag", 0);
		unusedTaglibImportsMap.put("getBar.tag", 0);
		unusedTaglibImportsMap.put("test.jsp", 3);
		unusedTaglibImportsMap.put("test2.jsp", 1);
		
		List<FileResult> fileResults = result.getFileResults();
		for (FileResult fileResult : fileResults) {
			String filename = fileResult.getJspPage().getFile().getName();
			int unusedTaglibImportsSize = fileResult.getUnusedTaglibImports().size();
			Assert.assertTrue(unusedTaglibImportsMap.containsKey(filename));
			Assert.assertTrue(unusedTaglibImportsMap.get(filename) == unusedTaglibImportsSize);
		}
	}
}
