package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.RechargeRecord;
import com.mjl.dfglxtv3.domain.vo.RechargeRecordVo;
import com.mjl.dfglxtv3.mapper.RechargeRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RechargeRecordService extends ServiceImpl<RechargeRecordMapper, RechargeRecord> {

    @Resource
    private DormService dormService;

    @Resource
    private BuildingService buildingService;

    @Resource
    private UserService userService;

    public List<RechargeRecordVo> pageInfo(Page<RechargeRecord> rechargeRecordPage, QueryWrapper<RechargeRecord> rechargeRecordQueryWrapper, String buildingId) {
        List<RechargeRecord> list = this.page(rechargeRecordPage, rechargeRecordQueryWrapper).getRecords();
        return list.stream().filter(ele -> {
            if (buildingId == null || buildingId.equals("")) {
                return true;
            }
            Dorm dorm = dormService.getById(ele.getDormId());
            return dorm.getBuildingId().toString().equals(buildingId);
        }).map(record -> {
            RechargeRecordVo rechargeRecordVo = new RechargeRecordVo();
            BeanUtils.copyProperties(record, rechargeRecordVo);
            Dorm dorm = dormService.getById(record.getDormId());
            rechargeRecordVo.setDormName(dorm.getName());
            rechargeRecordVo.setBuildingId(dorm.getBuildingId());
            rechargeRecordVo.setBuildingName(buildingService.getById(dorm.getBuildingId()).getName());
            rechargeRecordVo.setUsername(userService.getById(record.getUserId()).getName());
            return rechargeRecordVo;
        }).toList();
    }
}
