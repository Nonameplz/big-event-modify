package nonameplz.bigEventServer.mapper;

import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface tokenMapper {
    @Select("select * from big_event_database.token where token = #{token}")
    token getToken(String token);

    @Select("select * from big_event_database.token where userUUID = #{userUUID}")
    token getUserToken(user u);

    @Insert("insert into big_event_database.token(userUUID, expireTime, token, createTime) " +
            "values(#{userUUID},#{expireTime},#{token},NOW())")
    void saveUserToken(token t);

    @Update("update big_event_database.token set expireTime = #{expireTime},token = #{token},createTime = NOW() where userUUID = #{userUUID}")
    void updateUserToken(token t);

    @Select("select * from big_event_database.users where userUUID = (select userUUID from big_event_database.token where token = #{token})")
    user getUserByToken(String token);

    @Select("select userUUID from big_event_database.token where token = #{token}")
    String getUserUUIDByToken(String token);
}
