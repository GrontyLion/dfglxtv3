package com.mjl.dfglxtv3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.common.ResultCode;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.vo.DormVo;
import com.mjl.dfglxtv3.service.DormService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (dorm)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/dorm")
public class DormController {
    /**
     * 服务对象
     */
    @Resource
    private DormService dormService;

    /**
     * 获取全部记录
     *
     * @return 所有
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String name, String dormTypeId,String buildingId , String page, String limit) {
        Map<String, Object> map = new HashMap<>();
        Page<Dorm> dormPage = new Page<>();
        dormPage.setCurrent(Integer.parseInt(page));
        dormPage.setSize(Integer.parseInt(limit));
        QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
        if (name != null && !name.equals("")) {
            dormQueryWrapper.like("name", name);
        }
        if (dormTypeId != null && !dormTypeId.equals("")) {
            dormQueryWrapper.like("dorm_type_id", dormTypeId);
        }
        if (buildingId != null && !buildingId.equals("")) {
            dormQueryWrapper.like("building_id", buildingId);
        }
        List<DormVo> page1 = dormService.pageInfo(dormPage, dormQueryWrapper);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", page1.size());
        map.put("data", page1);
        return map;
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Dorm dorm) {
        QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
        dormQueryWrapper.eq("name", dorm.getName());
        dormQueryWrapper.or().eq("building_id", dorm.getBuildingId());
        if (dormService.count(dormQueryWrapper) > 0) {
            return Result.failed(ResultCode.DORM_EXIST);
        }
        boolean save = dormService.add(dorm);
        if (save) {
            return Result.success("添加成功");
        } else {
            return Result.failed(ResultCode.DORM_EXIST);
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Dorm dorm) {
        QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
        dormQueryWrapper.eq("name", dorm.getName());
        dormQueryWrapper.or().eq("building_id", dorm.getBuildingId());
        dormQueryWrapper.or().ne("id", dorm.getId());
        if (dormService.count(dormQueryWrapper) > 0) {
            return Result.failed(ResultCode.DORM_EXIST);
        }
        System.out.println(dorm);
        boolean update = dormService.updateById(dorm);
        if (update) {
            return Result.success("修改成功");
        } else {
            return Result.failed(ResultCode.DORM_EXIST);
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(Long id) {
        boolean delete = dormService.removeById(id);
        if (delete) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public Result<String> batchRemove(@RequestBody List<Long> ids) {
        boolean delete = dormService.removeBatchByIds(ids);
        if (delete) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

}
