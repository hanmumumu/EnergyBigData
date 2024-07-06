package upc.backend.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class Teacher {
    private Integer idteacher;
    private String teachername;
    private Byte locked;
    private Byte deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date register_time;
}
