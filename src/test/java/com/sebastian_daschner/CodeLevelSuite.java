package com.sebastian_daschner;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeClassNamePatterns("^(.*Test)$")
@SelectPackages("com.sebastian_daschner.coffee")
public class CodeLevelSuite {
}
