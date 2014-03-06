package net.sourceforge.cobertura.ant;

import groovy.util.Node;
import net.sourceforge.cobertura.test.IgnoreUtil;
import org.junit.Test;

public class IgnoreTrivialAntTest extends AbstractCoberturaAntTestCase {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAntProjectDirectoryName() {
        return "IgnoreTrivial";
    }

    @Test
	public void validateTrivialSectionsAreIgnored() throws Exception {

        // Assemble
        final String mainClass = "mypackage.Main";

        // Act
        final Node dom = executeAntTarget("all");
        final IgnoreUtil ignoreUtil = new IgnoreUtil(mainClass, dom);

		// trivial empty constructor
        ignoreUtil.assertIgnored("<init>", "()V");

		// trivial constructor Main(Thread, String) that just calls super()
        ignoreUtil.assertIgnored("<init>", "(Ljava/lang/Thread;Ljava/lang/String;)V");

		// trivial getter
        ignoreUtil.assertIgnored("getterTrivial", null);

		// isBool is trivial
        ignoreUtil.assertIgnored("isBool", null);

		// hasBool is trivial
        ignoreUtil.assertIgnored("hasBool", null);

		// setInt is trivial
        ignoreUtil.assertIgnored("setInt", null);

		// Main(int) has non-trivial switch
        ignoreUtil.assertNotIgnored("<init>", "(I)V");

		// Main(boolean) has non-trivial conditional
        ignoreUtil.assertNotIgnored("<init>", "(Z)V");

		// "empty" does not start with "get", "is", "has", or "set".
        ignoreUtil.assertNotIgnored("empty", null);

		// gets with no return are considered non-trivial
        ignoreUtil.assertNotIgnored("getVoid", null);

		// gets that have parameters are considered non-trivial
        ignoreUtil.assertNotIgnored("getIntWithIntParm", null);

		// sets that have no parameters are considered non-trivial
        ignoreUtil.assertNotIgnored("set", null);

		// sets that have more than one parameters are considered non-trivial
        ignoreUtil.assertNotIgnored("setIntWithTwoParms", null);

		// don't ignore methods with multi-dimensional array creates
        ignoreUtil.assertNotIgnored("getMultiDimArray", null);

		// don't ignore methods with increment instructions for local variables
        ignoreUtil.assertNotIgnored("setIncrement", null);

		// don't ignore methods with LDC instructions (that use constants from the runtime pool)
        ignoreUtil.assertNotIgnored("setConst", null);
        ignoreUtil.assertNotIgnored("<init>", "(Ljava/lang/Thread;I)V"); // Main(Thread, int)

		// don't ignore methods with a single int operand (like creating an array).
        ignoreUtil.assertNotIgnored("getArray", null);

		// don't ignore methods with type instructions (like creating an object).
        ignoreUtil.assertNotIgnored("getObject", null);

		// don't ignore methods that use statics.
        ignoreUtil.assertNotIgnored("getStatic", null);
        ignoreUtil.assertNotIgnored("setStatic", null);
        ignoreUtil.assertNotIgnored("<init>", "(Ljava/lang/String;)V");

		// non-trivial local variable instructions (causes visitVarInsn call)
        ignoreUtil.assertNotIgnored("setISTORE", null);
        ignoreUtil.assertNotIgnored("setLSTORE", null);
        ignoreUtil.assertNotIgnored("setFSTORE", null);
        ignoreUtil.assertNotIgnored("setDSTORE", null);
        ignoreUtil.assertNotIgnored("setASTORE", null);

		// non-trivial method calls
        ignoreUtil.assertNotIgnored("getINVOKEVIRTUAL", null);
        ignoreUtil.assertNotIgnored("getINVOKESPECIAL", null);
        ignoreUtil.assertNotIgnored("getINVOKESTATIC", null);
        ignoreUtil.assertNotIgnored("setINVOKEINTERFACE", null);
        ignoreUtil.assertNotIgnored("<init>", "(Ljava/lang/String;Ljava/lang/String;)V"); // Main(String, String)
        ignoreUtil.assertNotIgnored("<init>", "(Ljava/lang/String;I)V"); // Main(String, int)
        ignoreUtil.assertNotIgnored("<init>", "(Ljava/lang/String;Z)V"); // Main(String, boolean)
	}
}