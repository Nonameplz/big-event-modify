package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.article;

import java.util.List;

public interface articleService {
    public List<article> getArticleCategory(String userUUID);
}
