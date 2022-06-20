package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.ElectricityRecord;
import com.mjl.dfglxtv3.domain.vo.ElectricityRecordVo;
import com.mjl.dfglxtv3.mapper.ElectricityRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ElectricityRecordService extends ServiceImpl<ElectricityRecordMapper, ElectricityRecord> {
    @Resource
    private BuildingService buildingService;

    @Resource
    private DormService dormService;


    public List<ElectricityRecordVo> pageInfo(Page<ElectricityRecord> electricityRecordPage, QueryWrapper<ElectricityRecord> dormQueryWrapper, String buildingId) {
        List<ElectricityRecord> list = this.page(electricityRecordPage, dormQueryWrapper).getRecords();
        return list.stream().filter(ele -> {
            if  (buildingId == null || buildingId.equals("")) {
                return true;
            }
            Dorm dorm = dormService.getById(ele.getDormId());
            return dorm.getBuildingId().toString().equals(buildingId);
        }).map(ele -> {
            ElectricityRecordVo electricityRecordVo = new ElectricityRecordVo();
            BeanUtils.copyProperties(ele, electricityRecordVo);
            Dorm dorm = dormService.getById(ele.getDormId());
            electricityRecordVo.setDormName(dorm.getName());
            electricityRecordVo.setBuildingId(dorm.getBuildingId());
            electricityRecordVo.setBuildingName(buildingService.getById(dorm.getBuildingId()).getName());
            electricityRecordVo.setTotalPrice(electricityRecordVo.getPrice().multiply(electricityRecordVo.getPowerConsumption()));
            return electricityRecordVo;
        }).toList();
    }
}
