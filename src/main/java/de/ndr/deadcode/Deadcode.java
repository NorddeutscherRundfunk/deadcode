package de.ndr.deadcode;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import de.ndr.deadcode.result.FileResult;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.taglib.Taglib;
import de.ndr.deadcode.utils.GraphvizWriter;

public class Deadcode {
	
	private static final int COMMENT_RATIO = 30;

	public static void main(String[] args) throws IOException {
		Deadcode deadcode = new Deadcode();
		
		switch (args.length) {
			case 1:
				deadcode.run(new File(args[0]), null);
				break;
			case 2:
				deadcode.run(new File(args[0]), new File(args[1]));
				break;
			default:
				System.out.println("Usage: deadcode <webapproot> [<graphvizFile>]");
				System.out.println("example: deadcode /home/foo/workspace/project/src/main/webapp /tmp/deadcode.dot");
				System.exit(1);
		}
	}
	
	private void run(File webappRoot, File graphvizFile) {
		WebappProcessor processor = new WebappProcessor(COMMENT_RATIO, webappRoot);
		WebappResult result = processor.process();
		
		GraphvizWriter.write(result, graphvizFile);
		
		System.out.println("Unused Taglib imports:");
		for (FileResult fileResult : result.getFileResults()) {
			Set<Taglib> unusedTaglibImports = fileResult.getUnusedTaglibImports();
			if (!unusedTaglibImports.isEmpty()) {
				Set<String> prefixes = unusedTaglibImports.stream()
						.map(Taglib::getPrefix)
						.collect(Collectors.toSet());

				System.out.format("%d/%d unused imports (%s): %s\n",
						unusedTaglibImports.size(), fileResult.getJspPage().getImportedTaglibs().size(),
						String.join(", ", prefixes), 
						StringUtils.substringAfter(fileResult.getJspPage().getFile().getAbsolutePath(), webappRoot.getAbsolutePath()));
			}
		}
		
		Webapp webapp = new Webapp(webappRoot);
		Set<Tag> definedTags = webapp.getTags();
		
		Collection<AbstractJSTLEntity> unusedTags = CollectionUtils.subtract(definedTags, result.getUsedEntities());
		
		System.out.println("\nUnused tags:");
		unusedTags.forEach(System.out::println);
		System.out.println(unusedTags.size() + "/" + definedTags.size() + " Tags unused.");
		
		
		System.out.println("\nHighCommentRatioPages (> " + COMMENT_RATIO + "%):");
		for (JspPage page : result.getHighCommentRatioPages()) {
			System.out.format("%s: %d\n", 
								StringUtils.substringAfter(page.getFile().getAbsolutePath(), webappRoot.getAbsolutePath()),
								page.getCommentedCodeInfo().commentRatio);
		}
		
		System.out.println("\nDuplicateFiles:");
		webapp.getDuplicateFiles().values().forEach(v -> {
			Set<String> files = v.stream()
					.map((f) -> StringUtils.substringAfter(f.getAbsolutePath(), webappRoot.getAbsolutePath()))
					.collect(Collectors.toSet());
			System.out.format("%d: %s\n", files.size(),	String.join(", ", files));
		});
	}
}
