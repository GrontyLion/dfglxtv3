package com.mjl.dfglxtv3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.DormType;
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

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody DormType dormType) {
        log.info("add dormType: {}", dormType);
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
        log.info("delete dormType: {}", dormType);
        boolean remove = dormTypeService.removeById(dormType.getId());
        if (remove) {
            return Result.success("删除成功");
        }
        return Result.failed("删除失败");
    }
}
