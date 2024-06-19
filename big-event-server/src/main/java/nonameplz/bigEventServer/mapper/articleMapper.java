package nonameplz.bigEventServer.mapper;

import nonameplz.bigEventServer.pojo.article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface articleMapper {
    //    @Select("select articleUID,title,description,category,isPublish,likes,createTime,modifyTime from big_event_database.article where userUUID = #{userUUID}")
    List<article> getArticles(String userUUID, String category, Short state);

    @Select("select userUUID, articleUID, title, description, coverImage, content, category, isPublish, likes, createTime, modifyTime from big_event_database.article where articleUID = #{articleUID}")
    article getArticle(String articleUUID);

    @Insert("insert into big_event_database.article(userUUID, articleUID, title, description, content, category, isPublish,createTime, modifyTime) " +
            "values(#{userUUID},#{articleUID},#{title},#{description},#{content},#{category},#{isPublish},NOW(),NOW())")
    void insertArticle(article article);

    @Update("update big_event_database.article set title = #{title},description = #{description},category = #{category},modifyTime = NOW() " +
            "where userUUID = #{userUUID} and articleUID = #{articleUID}")
    void updateArticle(article article);

    @Delete("delete from big_event_database.article where articleUID = #{articleUID} and userUUID = #{userUUID}")
    void deleteArticle(String userUUID, String articleUID);

    @Select("select DISTINCT category from big_event_database.article where userUUID =#{userUUID}")
    List<String> getCategories(String userUUID);

    @Insert("insert into big_event_database.article(userUUID, articleUID, title, description,coverImage, content, category, isPublish,createTime, modifyTime) " +
            "values(#{userUUID},#{articleUID},#{title},#{description},#{coverImage},#{content},#{category},#{isPublish},NOW(),NOW())")
    void publishArticle(article article);

    @Select("Select coverImage,content from big_event_database.article where articleUID = #{articleUID}")
    article getCoverImgAndContent(String articleUID);

    @Select("select coverImage from big_event_database.article where articleUID = #{articleUID}")
    String getCoverImgUrl(String articleUID);

    @Update("update big_event_database.article set title = #{title},description = #{description},category = #{category},coverImage = #{coverImage},content = #{content},isPublish = #{isPublish},modifyTime = NOW() " +
            "where userUUID = #{userUUID} and articleUID = #{articleUID}")
    void updatePublishArticle(article article);
}
