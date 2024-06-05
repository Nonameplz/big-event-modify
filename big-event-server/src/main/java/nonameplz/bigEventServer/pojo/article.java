package nonameplz.bigEventServer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class article {
    private String userUUID;
    private String title;
    private String description;
    private String content;
    private String category;
    private Integer likes;
    private LocalDateTime CreateTime;
    private LocalDateTime ModifyTime;

}
