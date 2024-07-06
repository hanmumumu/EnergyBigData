package upc.backend.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import upc.backend.entity.Reference;
import upc.backend.entity.Teacher;
import upc.backend.entity.User;
import upc.backend.util.PageQueryUtil;
import upc.backend.util.PageResult;
import upc.backend.mapper.BbMapper;
import java.util.List;

@Service
public class BbService {
    @Resource
    private BbMapper bbMapper;
    public Boolean lockUsers(Integer[] ids, int lockStatus){
        if (ids.length < 1) {
            return false;
        }
        return bbMapper.lockUserBatch(ids, lockStatus) > 0;
    }
    public PageResult getUsersPage(PageQueryUtil pageUtil){
        List<Teacher> users = bbMapper.findAllUserList(pageUtil);
        int total = bbMapper.getNumOfTotalUsers(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    // 获取用户信息
    public Teacher getTeacherById(Integer idteacher){
        return bbMapper.selectByID(idteacher);
    }
    //更新文献信息
    public Boolean updateTeacherInfo(Teacher teacher){
        return bbMapper.updateByPrimaryKeySelective(teacher) > 0;
    }

    public Boolean add_Teacher(Teacher teacher){
        return bbMapper.insertSelective(teacher) > 0;
    }
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除分类数据
        return bbMapper.deleteBatch(ids) > 0;
    }
}
