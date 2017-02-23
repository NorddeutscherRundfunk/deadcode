package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.taglib.Tag;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class AbstractOutputPluginTest {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    protected IOutputPlugin outputPlugin;
    protected Set<Tag> definedTags;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Before
    public void abstractSetup() {
        definedTags = new HashSet<>();
        definedTags.add(new Tag("/WEB-INF/tags/debug", "log"));
    }
}
