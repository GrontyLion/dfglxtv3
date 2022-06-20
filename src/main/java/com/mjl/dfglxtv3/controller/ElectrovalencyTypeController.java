package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.ElectrovalencyType;
import com.mjl.dfglxtv3.service.BuildingService;
import com.mjl.dfglxtv3.service.ElectrovalencyTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

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

    @Resource
    private BuildingService buildingService;

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody ElectrovalencyType electrovalencyType) {
        StpUtil.checkRoleOr( "ADMIN");
        if (electrovalencyType != null && electrovalencyType.getName() != null && electrovalencyType.getName().length() > 0) {
            if (electrovalencyType.getPrice().compareTo(new BigDecimal(0)) <= 0) {
                return Result.failed("电费价格不能小等于0");
            }
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
        StpUtil.checkRoleOr( "ADMIN");
        if (electrovalencyType != null && electrovalencyType.getId() != null) {
            // 删除前先判断该电费类型是否被使用
            QueryWrapper<Building> buildingQueryWrapper = new QueryWrapper<>();
            buildingQueryWrapper.eq("electrovalency_type_id", electrovalencyType.getId());
            if (buildingService.count(buildingQueryWrapper) > 0) {
                return Result.failed("该电费类型已被使用，不能删除");
            }
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
        StpUtil.checkRoleOr( "ADMIN");
        if (electrovalencyType != null && electrovalencyType.getId() != null) {
            if (electrovalencyType.getPrice().compareTo(new BigDecimal(0)) <= 0) {
                return Result.failed("电费价格不能小等于0");
            }
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
