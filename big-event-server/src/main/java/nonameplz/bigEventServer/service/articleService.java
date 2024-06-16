package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.article;

import java.util.List;

public interface articleService {

    public List<article> getArticles(String userUUID);

    public void addArticle(String userUUID, article article);

    public void updateArticle(String userUUID, article article);

    public void deleteArticle(String userUUID, String articleUID);

    public List<String> getArticleCategory(String userUUID);
}
