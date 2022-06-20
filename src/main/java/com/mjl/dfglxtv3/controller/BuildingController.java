package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.vo.BuildingVo;
import com.mjl.dfglxtv3.service.BuildingService;
import com.mjl.dfglxtv3.service.DormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (building)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/building")
@Slf4j
public class BuildingController {
    /**
     * 服务对象
     */
    @Resource
    private BuildingService buildingService;

    @Resource
    private DormService dormService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String name, Long electrovalencyTypeId, String page, String limit) {
        StpUtil.checkLogin();
        Map<String, Object> map = new HashMap<>();
        Page<Building> page1 = new Page<>();
        page1.setCurrent(Integer.parseInt(page));
        page1.setSize(Integer.parseInt(limit));
        QueryWrapper<Building> buildingQueryWrapper = new QueryWrapper<>();
        if (name != null && !"".equals(name)) {
            buildingQueryWrapper.like("name", name);
        }
        if (electrovalencyTypeId != null) {
            buildingQueryWrapper.eq("electrovalency_type_id", electrovalencyTypeId);
        }
        List<BuildingVo> buildingVoList = buildingService.pageInfo(page1, buildingQueryWrapper);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", buildingVoList.size());
        map.put("data", buildingVoList);
        return map;
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Building building) {
        StpUtil.checkRole("ADMIN");
        QueryWrapper<Building> buildingQueryWrapper = new QueryWrapper<>();
        if (building.getName() != null && !"".equals(building.getName())) {
            buildingQueryWrapper.eq("name", building.getName());
        }
        if (building.getElectrovalencyTypeId() != null) {
            buildingQueryWrapper.eq("electrovalency_type_id", building.getElectrovalencyTypeId());
        }
        List<Building> buildingList = buildingService.list(buildingQueryWrapper);
        if (buildingList.size() > 0) {
            return Result.failed("该宿舍楼已存在");
        }
        boolean save = buildingService.save(building);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.failed("添加失败");
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Building building) {
        StpUtil.checkRole("ADMIN");
        QueryWrapper<Building> buildingQueryWrapper = new QueryWrapper<>();
        if (building.getName() != null && !"".equals(building.getName())) {
            buildingQueryWrapper.eq("name", building.getName());
        }
        if (building.getElectrovalencyTypeId() != null) {
            buildingQueryWrapper.eq("electrovalency_type_id", building.getElectrovalencyTypeId());
        }
        buildingQueryWrapper.ne("id", building.getId());
        List<Building> buildingList = buildingService.list(buildingQueryWrapper);
        if (buildingList.size() > 0) {
            return Result.failed("该宿舍楼已存在");
        }
        boolean update = buildingService.updateById(building);
        if (update) {
            return Result.success("修改成功");
        } else {
            return Result.failed("修改失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(String id) {
        StpUtil.checkRole("ADMIN");
        // 获取该宿舍楼下的所有宿舍
        QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
        dormQueryWrapper.eq("building_id", id);
        List<Dorm> dormList = dormService.list(dormQueryWrapper);
        if (dormList.size() > 0) {
            return Result.failed("该宿舍楼下存在宿舍，不能删除");
        }
        boolean remove = buildingService.removeById(id);
        if (remove) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public Result<String> batchRemove(@RequestBody List<Long> ids) {
        StpUtil.checkRole("ADMIN");
        for (Long id : ids) {
            // 获取该宿舍楼下的所有宿舍
            QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
            dormQueryWrapper.eq("building_id", id);
            List<Dorm> dormList = dormService.list(dormQueryWrapper);
            if (dormList.size() > 0) {
                return Result.failed("宿舍楼：" + buildingService.getById(id).getName() + ", 该宿舍楼下存在宿舍，不能删除");
            }
        }
        boolean remove = buildingService.removeByIds(ids);
        if (remove) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }
}
