package net.sourceforge.cobertura.ant;

import groovy.util.Node;

import java.io.File;
import java.net.URL;

import net.sourceforge.cobertura.test.util.TestUtils;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.MagicNames;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * Tutorial on how to add a new Ant Test unit.
 * <p/>
 * In this case we are creating a new argument called IgnoreMethodAnnotations.
 * <p/>
 * 1. Copy src/test/resources/ant/basic and rename the folder. In this case we rename to IgnoreMethodAnnotations.
 * 2. Make a new test unit (call it IgnoreMethodAnnotationAntTest) and extend this class.
 * 3. In test unit, set buildXmlFile = src/test/resources/ant/IgnoreMethodAnnotations/build.xml.
 * 4. Make modification to the build.xml file accordingly.
 * 5. To execute ant, call super.executeAntTarget(TARGET_NAME)
 * 6. To verify execution there are helper methods that can do verification for you.
 * If you would like to obtain the xml document it is public groovy.util.Node dom.
 *
 * @author schristou88
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractCoberturaAntTestCase {

    // Constants
    private static final String ANT_RESOURCE_DIR_FILE = "ant/antResourceDir.txt";
    public static final String COBERTURA_REPORTS_XML_PATH = "reports/cobertura-xml/coverage.xml";

    // Shared state
    @Rule
    public TestName name = new TestName();
    public Node dom;
    public File buildXmlFile;
    private File antResourcesDir;

    /**
     * Locates the ANT test structures within the target/test-classes directory, which
     * ensures that we do not write stuff to the srt/test/resources directory.
     * <p/>
     * (Writing or modifying src/test/resources/* files generates Git modifications,
     * implying confusion about what has actually been modified).
     */
    @Before
    public void setupSharedState() {

        // Find the ANT test resource directory
        final URL antPointOfOrigin = getClass().getClassLoader().getResource(ANT_RESOURCE_DIR_FILE);
        Assert.assertNotNull("ANT resources dir file not found. Attempted URL [" + ANT_RESOURCE_DIR_FILE + "]",
                antPointOfOrigin);

        File tmp = new File(antPointOfOrigin.getPath());
        Assert.assertTrue("ANT resources dir file not found. Attempted [" + tmp.getAbsolutePath() + "]",
                tmp.isFile() && tmp.length() > 0);

        antResourcesDir = tmp.getParentFile();
        Assert.assertTrue("ANT resources directory not found. Attempted [" + antResourcesDir.getAbsolutePath() + "].",
                tmp.isFile() && tmp.length() > 0);
    }

    /**
     * @return The name of the directory holding the Ant project.
     */
    protected abstract String getAntProjectDirectoryName();

    /**
     * Fires the given ANT target within the build.xml file in the active ANT project.
     * The build file is assumed to be {@code getAntProjectDirectoryName() + "/build.xml"}.
     *
     * @param antTarget The name of the ANT target (within the build.xml file) to fire.
     * @return The XML DOM top Node of the report generated.
     */
    public Node executeAntTarget(final String antTarget) throws Exception {

        // Find the build.xml file.
        final File buildXmlFile = new File(antResourcesDir, getAntProjectDirectoryName() + "/build.xml");
        Assert.assertTrue("ANT build.xml file not found. Attempted [" + buildXmlFile.getAbsolutePath() + "]",
                buildXmlFile.isFile() && buildXmlFile.length() > 0);

        System.out.println("Using build.xml file: " + buildXmlFile.getAbsolutePath());

        Exception error = null;
        Project project = new Project();
        BuildLogger buildLogger = new DefaultLogger();
        buildLogger.setErrorPrintStream(System.err);
        buildLogger.setOutputPrintStream(System.out);

        try {
            project.addBuildListener(buildLogger);
            project.fireBuildStarted();
            project.init();
            project.setUserProperty(MagicNames.ANT_FILE, buildXmlFile.getAbsolutePath());
            project.setUserProperty(MagicNames.PROJECT_BASEDIR, buildXmlFile.getParentFile().getAbsolutePath());
            ProjectHelper.configureProject(project, buildXmlFile);
            project.executeTarget(antTarget);
        } catch (Exception e) {
            error = e;
            throw e;
        } finally {
            project.fireBuildFinished(error);
        }

        // Generate the XML report DOM.
        dom = TestUtils.getXMLReportDOM(new File(buildXmlFile.getParentFile(), COBERTURA_REPORTS_XML_PATH));

        // All done.
        return dom;
    }

    //
    // Private helpers
    //

    /**
     * @return The root directory containing the ANT test resources.
     */
    protected File getAntResourcesDir() {
        return antResourcesDir;
    }
}