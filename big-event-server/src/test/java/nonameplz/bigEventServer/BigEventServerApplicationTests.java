package nonameplz.bigEventServer;

import com.sun.jdi.PathSearchingVirtualMachine;
import nonameplz.bigEventServer.pojo.token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BigEventServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testRandomToken(){
		System.out.println(token.generateTokenByShuffle());
	}

}
