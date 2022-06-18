package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.vo.DormVo;
import com.mjl.dfglxtv3.mapper.DormMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DormService extends ServiceImpl<DormMapper, Dorm> {

    @Resource
    private BuildingService buildingService;

    @Resource
    private DormTypeService dormTypeService;

    public List<DormVo> listInfo() {
        List<Dorm> list = this.list();
        return list.stream().map(dorm -> {
            DormVo dormVo = new DormVo();
            BeanUtils.copyProperties(dorm, dormVo);
            Building building = buildingService.getById(dorm.getBuildingId());
            dormVo.setBuildingName(building.getName());
            dormVo.setDormTypeName(dormTypeService.getById(dorm.getDormTypeId()).getName());
            return dormVo;
        }).toList();
    }
}
