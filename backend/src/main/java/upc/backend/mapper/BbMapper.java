package upc.backend.mapper;
import org.apache.ibatis.annotations.Param;
import upc.backend.entity.Reference;
import upc.backend.entity.Teacher;
import upc.backend.util.PageQueryUtil;

import java.util.List;

public interface BbMapper {
    int insertSelective(Teacher record);

    List<Teacher> findAllUserList(PageQueryUtil pageUtil);
    int getNumOfTotalUsers(PageQueryUtil pageUtil);
    int updateByPrimaryKeySelective(Teacher teacher);
    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
    int deleteBatch(Integer[] ids);
    Teacher selectByID(Integer idteacher);

}
