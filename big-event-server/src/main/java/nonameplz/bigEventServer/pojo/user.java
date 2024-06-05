package nonameplz.bigEventServer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    private String userUUID;
    private String userName;
    private String password;
    private String userNickName;
    private String email;
    private String phoneNumber;
    private String userImageURL;
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
