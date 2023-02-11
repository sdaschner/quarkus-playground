package com.sebastian_daschner.coffee;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class TestResultNotifier implements TestExecutionListener {

    private boolean passed = true;

    @Override
    public void executionFinished(
            TestIdentifier testIdentifier,
            TestExecutionResult testExecutionResult) {

        if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED)
            passed = false;
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        if (!passed) {
            System.out.println("!!!");
            System.out.println("Tests NOT passed!");
            System.out.println("!!!");
        } else {
            System.out.println("All tests passed!");
        }

        // to invoke a command line script:
//        String arg = passed ? "--green" : "--red";
//
//        try {
//            Process process = new ProcessBuilder("blink1-tool", arg).start();
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Could not execute blink1-tool");
//            e.printStackTrace();
//        }
    }

}