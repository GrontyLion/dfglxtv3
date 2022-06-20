package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Building;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.ElectricityRecord;
import com.mjl.dfglxtv3.domain.User;
import com.mjl.dfglxtv3.domain.vo.ElectricityRecordVo;
import com.mjl.dfglxtv3.service.*;
import com.mjl.dfglxtv3.util.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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


    @Resource
    private ElectrovalencyTypeService electrovalencyTypeService;
    @Resource
    private UserService userService;

    @Resource
    private DormService dormService;

    @Resource
    private BuildingService buildingService;


    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String dormId, String buildingId, String startDate, String endDate, String page, String limit) {
        String loginId = (String) StpUtil.getLoginId();
        if (StpUtil.hasRole("USER")) {
            dormId = userService.getById(loginId).getDormId().toString();
            buildingId = null;
        }
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

    @PostMapping("/use")
    @ResponseBody
    @Transactional
    public Result<String> use(@RequestBody ElectricityRecordVo electricityRecordVo) {
        String loginId = (String) StpUtil.getLoginId();
        if (StpUtil.hasRole("USER")) {
            electricityRecordVo.setDormId(userService.getById(loginId).getDormId());
        }
        BigDecimal powerConsumption1 = electricityRecordVo.getPowerConsumption();
        if (powerConsumption1.compareTo(new BigDecimal(0)) < 0) {
            return Result.failed("用电量不能小于0");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("dorm_id", electricityRecordVo.getDormId());
        List<User> users = userService.list(userQueryWrapper);
        if (users.size() == 0) {
            return Result.failed("该宿舍没有用户");
        }
        // 扣费
        Dorm dorm = dormService.getById(electricityRecordVo.getDormId());
        if (dorm.getDeposit().compareTo(new BigDecimal(0)) < 0) {
            return Result.failed("当前宿舍已欠费，不能使用电");
        }

        // 获取当前宿舍的电费类型
        Long buildingId = dorm.getBuildingId();
        Building building = buildingService.getById(buildingId);
        Long electrovalencyTypeId = building.getElectrovalencyTypeId();
        BigDecimal price = electrovalencyTypeService.getById(electrovalencyTypeId).getPrice();

        BigDecimal totalPrice = powerConsumption1.multiply(price);

        BigDecimal deposit = dorm.getDeposit();
        dorm.setDeposit(deposit.subtract(totalPrice));
        boolean b = dormService.updateById(dorm);
        if (!b) {
            return Result.failed("扣费失败");
        }

        // 电费不足，向该宿舍所有用户发送邮件
        if (deposit.compareTo(powerConsumption1) < 0) {
            for (User user : users) {
                EmailUtils.sendEmail(user.getEmail(), "电费提醒", "您的宿舍电费已经用完," + "当前欠费" + dorm.getDeposit() + "，请及时充值 ");
            }
        }

        // 添加用电记录
        ElectricityRecord electricityRecord = new ElectricityRecord();
        electricityRecord.setDormId(electricityRecordVo.getDormId());
        electricityRecord.setPowerConsumption(powerConsumption1);
        electricityRecord.setPrice(price);
        electricityRecord.setRecordDate(LocalDateTime.now());
        boolean save = electricityRecordService.save(electricityRecord);
        if (save) {
            return Result.success("使用电费" + electricityRecordVo.getPowerConsumption() + "度成功");
        } else {
            return Result.failed("使用失败");
        }
    }
}
