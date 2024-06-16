package nonameplz.bigEventServer.service.impl;

import nonameplz.bigEventServer.mapper.articleMapper;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.service.articleService;
import nonameplz.bigEventServer.utils.randomStringGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class articleServiceImpl implements articleService {

    @Autowired
    public articleMapper aMapper;

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

    @Override
    public List<article> getArticles(String userUUID) {
        return aMapper.getArticles(userUUID);
    }

    @Override
    public List<String> getArticleCategory(String userUUID) {
        return aMapper.getCategories(userUUID);
    }


}
