package nonameplz.bigEventServer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class token {
    private String userUUID;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
}
