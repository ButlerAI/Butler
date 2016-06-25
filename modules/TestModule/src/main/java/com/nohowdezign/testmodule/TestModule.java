package com.nohowdezign.testmodule;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "foobar")
public class TestModule {

    @Initialize
    public void initTest(String query) {
        System.out.println("testefst");
    }

}
