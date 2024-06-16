package nonameplz.bigEventServer.controller;

import lombok.extern.slf4j.Slf4j;
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
public class articleController {

    @Autowired
    private articleService aService;
    @Autowired
    private userService uService;

    @GetMapping("/list")
    public Result getArticles(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        List<article> articles=aService.getArticles(u.getUserUUID());

        List<String> articleCategoryJwt = new ArrayList<>();
        for (article article : articles) {
            Map<String, Object> claim=new HashMap<>();
            claim.put("articleUID", article.getArticleUID());
            claim.put("title", article.getTitle());
            claim.put("description",article.getDescription());
            claim.put("category",article.getCategory());
            claim.put("likes",article.getLikes());
            claim.put("state",article.getIsPublish());
            claim.put("createTime", article.getCreateTime().toString().replace("T","/"));
            claim.put("modifyTime", article.getModifyTime().toString().replace("T","/"));
            String jwt = JwtUtils.generateJwtNoExp(claim);

            articleCategoryJwt.add(jwt);
        }

        return Result.success(articleCategoryJwt);
    }

    @PostMapping("/addNew")
    public Result addArticle(@RequestHeader HttpHeaders headers, @RequestBody article article) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        aService.addArticle(u.getUserUUID(), article);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateArticle(@RequestHeader HttpHeaders headers, @RequestBody article article) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        aService.updateArticle(u.getUserUUID(), article);
        return Result.success();
    }

    @DeleteMapping("/del")
    public Result delArticle(@RequestHeader HttpHeaders headers, @RequestParam String articleUID) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        aService.deleteArticle(u.getUserUUID(), articleUID);
        return Result.success();
    }

    @GetMapping("/category")
    public Result getCategory(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        List<String> categories = aService.getArticleCategory(u.getUserUUID());

        return Result.success(categories);
    }
}
