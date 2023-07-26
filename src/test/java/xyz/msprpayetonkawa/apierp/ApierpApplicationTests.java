package xyz.msprpayetonkawa.apierp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import xyz.msprpayetonkawa.apierp.service.ProductServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ProductServiceTest.class
		// Add more test classes here if needed
})
class ApierpApplicationTests {


}
