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
    private String articleUID;
    private String title;
    private String description;
    private String coverImage;
    private String content;
    private String category;
    private Integer isPublish;
    private Integer likes;
    private LocalDateTime CreateTime;
    private LocalDateTime ModifyTime;

}
