package de.ndr.deadcode.plugins.output;

import de.ndr.deadcode.taglib.AbstractJSTLEntity;
import de.ndr.deadcode.taglib.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TeamCityOutputPluginTest extends AbstractOutputPluginTest {

    @Before
    public void setup() {
        outputPlugin = new TeamCityOutputPlugin();
    }

    @Test
    public void testOutputUnusedTags() {
        Collection<AbstractJSTLEntity> unusedTags = new ArrayList<>();

        outputPlugin.outputUnusedTags(unusedTags, definedTags);
        String expected = "##teamcity[buildStatisticValue key='deadcode.definedTags' value='1']\n" +
                "##teamcity[buildStatisticValue key='deadcode.unusedTags' value='0']";
        Assert.assertEquals(expected, outContent.toString().trim());
    }

    @Test
    public void testOutputUnusedTags2() {
        Collection<AbstractJSTLEntity> unusedTags = new ArrayList<>();
        unusedTags.add(new Tag("/WEB-INF/tags/debug", "log"));

        outputPlugin.outputUnusedTags(unusedTags, definedTags);
        String expected = "##teamcity[buildStatisticValue key='deadcode.definedTags' value='1']\n" +
                "##teamcity[buildStatisticValue key='deadcode.unusedTags' value='1']";
        Assert.assertEquals(expected, outContent.toString().trim());
    }
}
