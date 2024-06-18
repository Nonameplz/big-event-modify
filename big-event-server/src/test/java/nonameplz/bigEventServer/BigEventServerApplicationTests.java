package nonameplz.bigEventServer;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.pageBean;
import nonameplz.bigEventServer.service.articleService;
import nonameplz.bigEventServer.utils.AliOSSUtils;
import nonameplz.bigEventServer.utils.randomStringGetter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BigEventServerApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(BigEventServerApplicationTests.class);
	@Autowired
	private articleService aService;
	@Autowired
	private AliOSSUtils aliOSSUtils;

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

	@Test
	void testPageBean(){
		pageBean pagebean = aService.getArticlesSelected(1,10,"0000018f-d4ac-f81e-0000-00003e6fa0db","all","all");
        var rows = pagebean.getRows();
		for (Object row : rows) {
			System.out.println(((article)row).getArticleUID());
			System.out.println(((article)row).toString());
		}
	}
	@Test
	void testUpload() throws IOException, ClientException {
		MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());
		aliOSSUtils.setBucketName("image-container");
		String url=aliOSSUtils.upload(file);
		System.out.println(url);
	}
}
