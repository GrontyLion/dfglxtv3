package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.vo.DormVo;
import com.mjl.dfglxtv3.mapper.DormMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DormService extends ServiceImpl<DormMapper, Dorm> {

    @Resource
    private BuildingService buildingService;

    @Resource
    private DormTypeService dormTypeService;

    public List<DormVo> pageInfo(Page<Dorm> dormPage, QueryWrapper<Dorm> dormQueryWrapper) {
        List<Dorm> list = this.page(dormPage, dormQueryWrapper).getRecords();
        return list.stream().map(dorm -> {
            DormVo dormVo = new DormVo();
            BeanUtils.copyProperties(dorm, dormVo);
            Building building = buildingService.getById(dorm.getBuildingId());
            dormVo.setBuildingName(building.getName());
            dormVo.setDormTypeName(dormTypeService.getById(dorm.getDormTypeId()).getName());
            return dormVo;
        }).toList();
    }

    @Transactional
    public boolean add(Dorm dorm) {
        Building building = buildingService.getById(dorm.getBuildingId());
        QueryWrapper<Dorm> eq = new QueryWrapper<Dorm>().eq("building_id", dorm.getBuildingId())
                .eq("name", dorm.getName());
        if (this.count(eq) > 0) {
            return false;
        }
        return this.save(dorm);
    }
}
