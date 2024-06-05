package nonameplz.bigEventServer.controller;

import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.mapper.tokenMapper;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.articleService;
import nonameplz.bigEventServer.service.userService;
import nonameplz.bigEventServer.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("my/cate/")
@CrossOrigin(origins = "*")
public class getArticleController {

    @Autowired
    private articleService aService;
    @Autowired
    private userService uService;

    @GetMapping("/list")
    public Result getArticleCategory(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        List<article> articleCategory=aService.getArticleCategory(u.getUserUUID());

        List<String> articleCategoryJwt = new ArrayList<>();
        for (article article : articleCategory) {
            Map<String, Object> claim=new HashMap<>();
            claim.put("title", article.getTitle());
            claim.put("description",article.getDescription());
            claim.put("category",article.getCategory());
            claim.put("likes",article.getLikes());
            String jwt = JwtUtils.generateJwt(claim);

            articleCategoryJwt.add(jwt);
        }

        return Result.success(articleCategoryJwt);
    }

}
