package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.DormType;
import com.mjl.dfglxtv3.service.DormService;
import com.mjl.dfglxtv3.service.DormTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (dorm_type)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/dormType")
@Slf4j
public class DormTypeController {
    /**
     * 服务对象
     */
    @Resource
    private DormTypeService dormTypeService;

    @Resource
    private DormService dormService;

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody DormType dormType) {
        StpUtil.checkRole("ADMIN");
        QueryWrapper<DormType> dormTypeQueryWrapper = new QueryWrapper<>();
        if (dormType != null && !"".equals(dormType.getName())) {
            dormTypeQueryWrapper.eq("name", dormType.getName());
        }
        List<DormType> list = dormTypeService.list(dormTypeQueryWrapper);
        if (list.size() > 0) {
            return Result.failed("该宿舍类型已存在");
        }
        boolean save = dormTypeService.save(dormType);
        if (save) {
            return Result.success("添加成功");
        }
        return Result.failed("添加失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(@RequestBody DormType dormType) {
        StpUtil.checkRole("ADMIN");
        // 删除前先判断该宿舍类型是否被使用
        QueryWrapper<Dorm> dormTypeQueryWrapper = new QueryWrapper<>();
        dormTypeQueryWrapper.eq("dorm_type_id", dormType.getId());
        List<Dorm> list = dormService.list(dormTypeQueryWrapper);
        if (list.size() > 0) {
            return Result.failed("该宿舍类型已被使用，不能删除");
        }
        boolean remove = dormTypeService.removeById(dormType.getId());
        if (remove) {
            return Result.success("删除成功");
        }
        return Result.failed("删除失败");
    }
}
