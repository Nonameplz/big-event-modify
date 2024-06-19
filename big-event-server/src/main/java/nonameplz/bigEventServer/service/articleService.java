package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.pageBean;

import java.util.List;
import java.util.Map;

public interface articleService {

    List<article> getArticles(String userUUID);

    pageBean getArticlesSelected(Integer pageNum, Integer pageSize, String userUUID, String category, String state);

    void addArticle(String userUUID, article article);

    void updateArticle(String userUUID, article article);

    void deleteArticle(String userUUID, String articleUID);

    List<String> getArticleCategory(String userUUID);

    void publishArticle(String userUUID, String title, String category, String description, String coverImage, String content, String state);

    void updatePublishArticle(String userUUID, String articleUID, String title, String category, String description, String coverImage, String content, String state);

    Map<String, Object> getCoverImgAndContent(String articleUID);

    String getCoverImgUrl(String articleUID);
}
