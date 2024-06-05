package nonameplz.bigEventServer.mapper;

import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface userMapper {
    @Select("select * from big_event_database.users where userName = #{userName}")
    user register_isExist(user u);

    @Insert("insert into big_event_database.users(userUUID, userName, userPassword, email, userImageURL,createTime, modifyTime) " +
            "values(#{userUUID},#{userName},#{password},#{email},#{userImageURL},#{createTime},#{updateTime})")
    void register(user u);

    @Select("select * from big_event_database.users where userName = #{userName} and userPassword = #{password}")
    user userLogin(user u);

}
