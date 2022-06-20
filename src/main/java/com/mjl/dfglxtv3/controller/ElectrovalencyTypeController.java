package com.mjl.dfglxtv3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.ElectrovalencyType;
import com.mjl.dfglxtv3.service.ElectrovalencyTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (electrovalency_type)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/electrovalencyType")
public class ElectrovalencyTypeController {
    /**
     * 服务对象
     */
    @Resource
    private ElectrovalencyTypeService electrovalencyTypeService;

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody ElectrovalencyType electrovalencyType) {
        if (electrovalencyType != null && electrovalencyType.getName() != null && electrovalencyType.getName().length() > 0) {
            QueryWrapper<ElectrovalencyType> electrovalencyTypeQueryWrapper = new QueryWrapper<>();
            electrovalencyTypeQueryWrapper.eq("name", electrovalencyType.getName());
            if (electrovalencyTypeService.count(electrovalencyTypeQueryWrapper) > 0) {
                return Result.failed("该电费类型已存在");
            }
            boolean save = electrovalencyTypeService.save(electrovalencyType);
            if (save) {
                return Result.success("添加成功");
            } else {
                return Result.failed("添加失败");
            }
        }
        return Result.failed("添加失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(@RequestBody ElectrovalencyType electrovalencyType) {
        if (electrovalencyType != null && electrovalencyType.getId() != null) {
            boolean remove = electrovalencyTypeService.removeById(electrovalencyType.getId());
            if (remove) {
                return Result.success("删除成功");
            } else {
                return Result.failed("删除失败");
            }
        }
        return Result.failed("删除失败");
    }

    @PostMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody ElectrovalencyType electrovalencyType) {
        if (electrovalencyType != null && electrovalencyType.getId() != null) {
            boolean update = electrovalencyTypeService.updateById(electrovalencyType);
            if (update) {
                return Result.success("修改成功");
            } else {
                return Result.failed("修改失败");
            }
        }
        return Result.failed("修改失败");
    }
}
