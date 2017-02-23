package de.ndr.deadcode;

import de.ndr.deadcode.plugins.output.IOutputPlugin;
import de.ndr.deadcode.result.WebappResult;
import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import de.ndr.deadcode.utils.GraphvizWriter;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class Deadcode {
	
	private final File webappRoot;
	private final int commentRatio;
	private File graphvizFile;
	private IOutputPlugin outputPlugin;

	public static void main(String[] args) throws IOException {

		Namespace ns = createAndParseArguments(args);

		File webappRoot = new File(ns.getString("webappRoot"));
		File graphvizFile = null;
		if (ns.getString("graphvizFile") != null) {
			graphvizFile = new File(ns.getString("graphvizFile"));
		}
		IOutputPlugin outputPlugin = null;
		try {
			outputPlugin = (IOutputPlugin) Class.forName("de.ndr.deadcode.plugins.output." + ns.getString("outputPlugin")).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.exit(1);
		}
		int commentRatio = ns.getInt("commentRatio");

		Deadcode deadcode = new Deadcode(webappRoot, graphvizFile, outputPlugin, commentRatio);

		deadcode.run();
	}

	private static Namespace createAndParseArguments(String[] args) {
		ArgumentParser parser = ArgumentParsers.newArgumentParser("deadcode")
				.defaultHelp(true)
				.description("Find unused Tags and Imports");
		parser.addArgument("-op", "--outputPlugin")
				.choices("NoopOutputPlugin", "StdoutOutputPlugin", "TeamCityOutputPlugin").setDefault("StdoutOutputPlugin")
				.help("Output plugin to use");
		parser.addArgument("-gv", "--graphvizFile")
				.help("file to write graphviz output to");
		parser.addArgument("-cr", "--commentRatio")
				.setDefault(30)
				.help("High comment ratio");
		parser.addArgument("webappRoot").
				help("root of webapp to check").
				required(true);
		try {
			return parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}
		return null;
	}

	private Deadcode(File webappRoot, File graphvizFile, IOutputPlugin outputPlugin, int commentRatio) {
		this.webappRoot = webappRoot;
		this.graphvizFile = graphvizFile;
		this.outputPlugin = outputPlugin;
		this.commentRatio = commentRatio;
	}


	private void run() {
		WebappProcessor processor = new WebappProcessor(commentRatio, webappRoot);
		Webapp webapp = new Webapp(webappRoot);
		WebappResult result = processor.process();

		GraphvizWriter.write(result, graphvizFile);

		outputPlugin.outputUnusedTaglibImports(result, webappRoot);
		
		Set<Tag> definedTags = webapp.getTags();
		Collection<AbstractJSTLEntity> unusedTags = CollectionUtils.subtract(definedTags, result.getUsedEntities());
		outputPlugin.outputUnusedTags(unusedTags, definedTags);

		outputPlugin.outputHighCommentRatioPages(result.getHighCommentRatioPages(), webappRoot, commentRatio);
		outputPlugin.outputDuplicateFiles(webapp.getDuplicateFiles(), webappRoot);
	}


}
