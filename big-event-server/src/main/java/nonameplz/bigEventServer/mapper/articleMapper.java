package nonameplz.bigEventServer.mapper;

import nonameplz.bigEventServer.pojo.article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface articleMapper {
    @Select("select title,description,category,likes from big_event_database.article where userUUID = #{userUUID}")
    List<article> getArticleCategory(String userUUID);
}
