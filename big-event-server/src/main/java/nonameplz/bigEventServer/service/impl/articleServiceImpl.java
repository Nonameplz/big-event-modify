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

import java.util.List;

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
