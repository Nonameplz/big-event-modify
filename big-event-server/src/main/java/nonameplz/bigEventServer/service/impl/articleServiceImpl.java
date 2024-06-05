package nonameplz.bigEventServer.service.impl;

import nonameplz.bigEventServer.mapper.articleMapper;
import nonameplz.bigEventServer.pojo.article;
import nonameplz.bigEventServer.service.articleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class articleServiceImpl implements articleService {

    @Autowired
    public articleMapper aMapper;

    @Override
    public List<article> getArticleCategory(String userUUID) {
        return aMapper.getArticleCategory(userUUID);
    }
}
