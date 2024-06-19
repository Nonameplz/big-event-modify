package nonameplz.bigEventServer.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import nonameplz.bigEventServer.mapper.articleMapper;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.pageBean;
import nonameplz.bigEventServer.service.articleService;
import nonameplz.bigEventServer.utils.randomStringGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class articleServiceImpl implements articleService {

    @Autowired
    public articleMapper aMapper;

    @Override
    public List<article> getArticles(String userUUID) {
        return aMapper.getArticles(userUUID,null,null);
    }

    @Override
    public pageBean getArticlesSelected(Integer pageNum, Integer pageSize,String userUUID, String category, String state) {
        PageHelper.startPage(pageNum,pageSize);

        List<article> articleList = aMapper.getArticles(userUUID, category, (short) (state.equals("已发布") ? 1 : state.equals("未发布") ? 0 : -1));

        Page<article> p = (Page<article>) articleList;

        return new pageBean(p.getTotal(),p.getResult());
    }

    //
    @Override
    public List<String> getArticleCategory(String userUUID) {
        return aMapper.getCategories(userUUID);
    }

    @Override
    public void publishArticle(String userUUID, String title, String category, String description, String coverImage, String content, String state) {
        article article = new article();
        article.setUserUUID(userUUID);
        article.setArticleUID(randomStringGetter.generateUUIDRandom());
        article.setTitle(title);
        article.setCategory(category);
        article.setDescription(description);
        article.setCoverImage(coverImage);
        article.setContent(content);
        article.setIsPublish(state.equals("已发布")?1:0);
        article.setLikes(0);

        aMapper.publishArticle(article);
    }

    @Override
    public void updatePublishArticle(String userUUID, String articleUID, String title, String category, String description, String coverImage, String content, String state) {
        article article = aMapper.getArticle(articleUID);
        article.setTitle(title);
        article.setCategory(category);
        article.setDescription(description);
        article.setCoverImage(coverImage);
        article.setContent(content);
        article.setIsPublish(state.equals("已发布")?1:0);

        aMapper.updatePublishArticle(article);
    }

    @Override
    public Map<String, Object> getCoverImgAndContent(String articleUID) {
        article contentAttribute = aMapper.getCoverImgAndContent(articleUID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("coverImg","");
        map.put("content","");
        if (contentAttribute != null) {
            if (contentAttribute.getCoverImage() != null) {
                map.put("coverImg",contentAttribute.getCoverImage());
            }
            if (contentAttribute.getContent() != null) {
                map.put("content", contentAttribute.getContent());
            }
        }

        return map;
    }

    @Override
    public String getCoverImgUrl(String articleUID) {
        return aMapper.getCoverImgUrl(articleUID);
    }


    @Override
    public void addArticle(String userUUID, article article) {
        article.setUserUUID(userUUID);
        article.setArticleUID(randomStringGetter.generateUUIDRandom());
        article.setIsPublish(0);
        aMapper.insertArticle(article);
    }

    @Override
    public void updateArticle(String userUUID, article article) {
        article.setUserUUID(userUUID);
        aMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(String userUUID, String articleUID) {
        aMapper.deleteArticle(userUUID, articleUID);
    }


}
