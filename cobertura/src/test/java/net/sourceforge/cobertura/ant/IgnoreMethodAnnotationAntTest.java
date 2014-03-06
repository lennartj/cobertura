package net.sourceforge.cobertura.ant;

import groovy.util.Node;
import net.sourceforge.cobertura.test.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class IgnoreMethodAnnotationAntTest extends AbstractCoberturaAntTestCase {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAntProjectDirectoryName() {
        return "IgnoreMethodAnnotation";
    }

    @Test
	public void validateAnnotatedMethodsAreIgnored() throws Exception {

        // Assemble
        final String methodName = "foo";

        // Act
        final Node dom = executeAntTarget("all");

        // Assert
		Assert.assertEquals(0, TestUtils.getTotalHitCount(dom, "test.condition.IgnoreMe", methodName));
        Assert.assertEquals(0, TestUtils.getTotalHitCount(dom, "test.condition.IgnoreMeAlso", methodName));
		Assert.assertTrue(TestUtils.getHitCount(dom, "test.condition.IgnoreMeNot", methodName) > 0);

        // For debugging purposes (i.e. taking a peek at the generated DOM),
        // uncomment the line below.
        //
        // dom.print(new PrintWriter(System.out));
	}
}