package nonameplz.bigEventServer.controller;

import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.pageBean;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.articleService;
import nonameplz.bigEventServer.service.userService;
import nonameplz.bigEventServer.utils.AliOSSUtils;
import nonameplz.bigEventServer.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("my/article/")
@CrossOrigin(origins = "*")
public class articleController {

    @Autowired
    private articleService aService;
    @Autowired
    private userService uService;
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @GetMapping("/list")
    public Result getArticles(@RequestHeader HttpHeaders headers,
                              @RequestParam(defaultValue = "0") Integer pageNum,
                              @RequestParam(defaultValue = "-1") Integer pageSize,
                              @RequestParam(defaultValue = "all") String category,
                              @RequestParam(defaultValue = "all") String state
    ) {
        log.info("查询第{}页的{}条数据:筛选类别为{},发布状态为{}", pageNum, pageSize == -1 ? "all" : pageSize, category, state);
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);
        List<String> articleCategoryJwt = new ArrayList<>();

        if (pageNum == 0) {
            List<article> articles = aService.getArticles(u.getUserUUID());
            for (article article : articles) {
                Map<String, Object> claim = getArticleMap(article);
                String jwt = JwtUtils.generateJwtNoExp(claim);

                articleCategoryJwt.add(jwt);
            }
            ;
            return Result.success(articleCategoryJwt);
        }

        if (pageSize != 0) {
            pageBean pagebean = aService.getArticlesSelected(pageNum, pageSize, u.getUserUUID(), category, state);
            var rows = pagebean.getRows();
            for (Object row : rows) {
                Map<String, Object> claim = getArticleMap((article) row);
                String jwt = JwtUtils.generateJwtNoExp(claim);

                articleCategoryJwt.add(jwt);
            }

            articleCategoryJwt.add(String.valueOf(pagebean.getTotal()));
            return Result.success(articleCategoryJwt);
        }
        return Result.error("unKnowError");
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

    @PostMapping("/add")
    public Result publishArticle(@RequestHeader HttpHeaders headers,
                                 @RequestParam("title") String title,
                                 @RequestParam("category") String category,
                                 @RequestParam("description") String description,
                                 @RequestParam("cover_image") MultipartFile cover_image,
                                 @RequestParam("content") String content,
                                 @RequestParam("state") String state
    ) throws IOException {
        log.info("上传图片:{}", cover_image.getOriginalFilename());
        String dir = "img-cover/";
        String url = aliOSSUtils.upload(cover_image, dir);
        log.info(url);
        log.info("{}", url.length());

        aService.publishArticle(uService.getUserByToken(headers.getFirst("Authorization")).getUserUUID(), title, category, description, url, content, state);
        return Result.success();
    }

    private static Map<String, Object> getArticleMap(article article) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("articleUID", article.getArticleUID());
        claim.put("title", article.getTitle());
        claim.put("description", article.getDescription());
        claim.put("category", article.getCategory());
        claim.put("likes", article.getLikes());
        claim.put("state", article.getIsPublish());
        claim.put("createTime", article.getCreateTime().toString().replace("T", "/"));
        claim.put("modifyTime", article.getModifyTime().toString().replace("T", "/"));
        return claim;
    }
}
