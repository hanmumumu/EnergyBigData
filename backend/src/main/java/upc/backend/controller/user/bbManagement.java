package upc.backend.controller.user;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import upc.backend.common.BatchIdParam;
import upc.backend.common.Constants;
import upc.backend.common.ServiceResultEnum;
import upc.backend.controller.user.param.BbAddParam;
import upc.backend.controller.user.param.BbUpdateParam;
import upc.backend.entity.Teacher;
import upc.backend.entity.UserToken;
import upc.backend.service.BbService;
import upc.backend.util.BeanUtil;
import upc.backend.util.PageQueryUtil;
import upc.backend.util.Result;
import upc.backend.util.ResultGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import upc.backend.service.UserTokenService;

@RestController
@RequestMapping("/api")
public class bbManagement {
    private static final Logger logger = LoggerFactory.getLogger(bbManagement.class);
    @Resource
    private BbService bbService;
    @Resource
    private UserTokenService userTokenService;


    @RequestMapping(value = "/teachers/list", method = RequestMethod.GET)
    public Result list(@RequestParam(required = false) Integer pageNumber,
                       @RequestParam(required = false) Integer pageSize,
                       @RequestParam(required = false) Integer lockStatus) {
        //, @RequestHeader("token") String token
        // logger.info("this test tokon:{}", token);
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        if (lockStatus != null) {
            params.put("userStatus", lockStatus);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(bbService.getUsersPage(pageUtil));
    }

    //用户禁用与解除禁用(0-未锁定 1-已锁定)
    @RequestMapping(value = "/teachers/{lockStatus}", method = RequestMethod.PUT)
    public Result lockUser(@RequestBody BatchIdParam batchIdParam, @PathVariable int lockStatus) {
        //logger.info("user:{}", user.toString());
        if (batchIdParam==null||batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (bbService.lockUsers(batchIdParam.getIds(), lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }
    @RequestMapping(value = "/teachers", method = RequestMethod.DELETE)
    //@ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public Result delete(@RequestBody BatchIdParam batchIdParam) {

        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (bbService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @RequestMapping(value = "/teachers/teacher_add", method = RequestMethod.POST)
    public Result teacher_add(@RequestBody BbAddParam bbAddParam) {
        logger.info("teachername:{}", bbAddParam.getTeachername());
        if (bbAddParam.getTeachername()==null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        else {
            Teacher teacher = new Teacher();
            teacher.setTeachername(bbAddParam.getTeachername());
            teacher.setLocked((byte) 0);
            teacher.setDeleted((byte) 0);
            teacher.setRegister_time(new Date());
            if (bbService.add_Teacher(teacher)) {
                Result result = new ResultGenerator().genSuccessResult();
                result.setData(true);
                return result;
            }
            else return ResultGenerator.genFailResult("用户新增失败");
        }
    }
    @RequestMapping(value = "/teachers/{idteacher}", method = RequestMethod.GET)
    // "获取单条信息", "根据id查询"
    public Result info(@PathVariable("idteacher") Integer idteacher) {
        Teacher teacher = bbService.getTeacherById(idteacher);
        if (teacher == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(teacher);
    }
    //修改
    @RequestMapping(value = "/teachers/update", method = RequestMethod.PUT)
    // @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public Result update(@RequestBody BbUpdateParam bbUpdateParam, @RequestHeader("token") String str_token) {
        if (null != str_token && !"".equals(str_token) && str_token.length() == Constants.TOKEN_LENGTH) {
            UserToken userToken = userTokenService.selectByToken(str_token);
            if (userToken == null) {
                return ResultGenerator.genFailResult(ServiceResultEnum.NOT_LOGIN_ERROR.getResult());
            } else if (userToken.getExpire_time().getTime() <= System.currentTimeMillis()) {
                return ResultGenerator.genFailResult(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
            }
            else {
                Teacher teacher = new Teacher();
                BeanUtil.copyProperties(bbUpdateParam, teacher);
                bbService.updateTeacherInfo(teacher);
                Result result = new ResultGenerator().genSuccessResult();
                result.setData(true);
                return result;

            }
        }
        else {
            return ResultGenerator.genFailResult(ServiceResultEnum.NOT_LOGIN_ERROR.getResult());
        }
    }

}