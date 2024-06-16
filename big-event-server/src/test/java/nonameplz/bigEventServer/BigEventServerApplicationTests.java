package nonameplz.bigEventServer;

import com.alibaba.fastjson.JSON;
import nonameplz.bigEventServer.utils.randomStringGetter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class BigEventServerApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(BigEventServerApplicationTests.class);

	@Test
	void contextLoads() {
	}

	@Test
	void testRandomToken(){
		System.out.println(randomStringGetter.generateTokenByShuffle());
	}

	@Test
	void testUUIDGet(){
		System.out.println(randomStringGetter.generateUUIDRandom());
	}

	@Test
	void testFastJSON(){
		System.out.println(LocalDateTime.now());
		System.out.println(JSON.toJSONString(LocalDateTime.now()));
	}

}
