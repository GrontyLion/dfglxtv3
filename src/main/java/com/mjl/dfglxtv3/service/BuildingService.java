package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.ElectrovalencyType;
import com.mjl.dfglxtv3.domain.vo.BuildingVo;
import com.mjl.dfglxtv3.mapper.BuildingMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildingService extends ServiceImpl<BuildingMapper, Building> {

    @Resource
    private ElectrovalencyTypeService electrovalencyTypeService;


    public List<BuildingVo> pageInfo(Page<Building> page1, QueryWrapper<Building> buildingQueryWrapper) {
        List<Building> list = this.page(page1, buildingQueryWrapper).getRecords();
        return list.stream().map(building -> {
            BuildingVo buildingVo = new BuildingVo();
            BeanUtils.copyProperties(building, buildingVo);
            ElectrovalencyType electrovalencyType = electrovalencyTypeService.getById(building.getElectrovalencyTypeId());
            buildingVo.setElectrovalencyTypeName(electrovalencyType.getName());
            buildingVo.setPrice(electrovalencyType.getPrice());
            return buildingVo;
        }).toList();
    }
}
