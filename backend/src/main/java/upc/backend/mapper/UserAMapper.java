package upc.backend.mapper;
import org.apache.ibatis.annotations.Param;
import upc.backend.entity.User;
import upc.backend.util.PageQueryUtil;

import java.util.List;

public interface UserAMapper {
    User selectByID(Integer userid);

    }
