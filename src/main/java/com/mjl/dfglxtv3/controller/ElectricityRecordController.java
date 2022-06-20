package com.mjl.dfglxtv3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.domain.ElectricityRecord;
import com.mjl.dfglxtv3.domain.vo.ElectricityRecordVo;
import com.mjl.dfglxtv3.service.ElectricityRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (electricity_record)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/electricityRecord")
@Slf4j
public class ElectricityRecordController {
    /**
     * 服务对象
     */
    @Resource
    private ElectricityRecordService electricityRecordService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String dormId, String buildingId, String startDate, String endDate , String page, String limit) {
        log.info("page:{}", page);
        log.info("limit:{}", limit);
        Map<String, Object> map = new HashMap<>();
        Page<ElectricityRecord> electricityRecordPage = new Page<>();
        electricityRecordPage.setCurrent(Integer.parseInt(page));
        electricityRecordPage.setSize(Integer.parseInt(limit));
        QueryWrapper<ElectricityRecord> dormQueryWrapper = new QueryWrapper<>();
        if (dormId != null && !dormId.equals("")) {
            dormQueryWrapper.eq("dorm_id", dormId);
        }
        if (startDate != null && !startDate.equals("")) {
            dormQueryWrapper.ge("record_date", startDate);
        }
        if (endDate != null && !endDate.equals("")) {
            dormQueryWrapper.le("record_date", endDate);
        }
        List<ElectricityRecordVo> page1 = electricityRecordService.pageInfo(electricityRecordPage, dormQueryWrapper, buildingId);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", page1.size());
        map.put("data", page1);
        return map;
    }

}
