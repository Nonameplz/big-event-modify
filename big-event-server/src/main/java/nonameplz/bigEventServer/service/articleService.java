package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.pojo.pageBean;

import java.util.List;
import java.util.Map;

public interface articleService {

    public List<article> getArticles(String userUUID);

    public pageBean getArticlesSelected(Integer pageNum, Integer pageSize, String userUUID, String category, String state);

    public void addArticle(String userUUID, article article);

    public void updateArticle(String userUUID, article article);

    public void deleteArticle(String userUUID, String articleUID);

    public List<String> getArticleCategory(String userUUID);

    public void publishArticle(String userUUID, String title, String category, String description, String coverImage, String content, String state);

    public void updatePublishArticle(String userUUID, String articleUID, String title, String category, String description, String coverImage, String content, String state);

    public Map<String, Object> getCoverImgAndContent(String articleUID);

    public String getCoverImgUrl(String articleUID);
}
