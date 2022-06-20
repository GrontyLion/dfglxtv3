package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.RechargeRecord;
import com.mjl.dfglxtv3.domain.vo.RechargeRecordVo;
import com.mjl.dfglxtv3.service.DormService;
import com.mjl.dfglxtv3.service.RechargeRecordService;
import com.mjl.dfglxtv3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (recharge_record)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/rechargeRecord")
@Slf4j
public class RechargeRecordController {
    /**
     * 服务对象
     */
    @Resource
    private RechargeRecordService rechargeRecordService;
    @Resource
    private DormService dormService;

    @Resource
    private UserService userService;
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String userId ,String dormId, String buildingId, String startDate, String endDate , String page, String limit) {
        log.info("page:{}", page);
        log.info("limit:{}", limit);
        Map<String, Object> map = new HashMap<>();
        Page<RechargeRecord> rechargeRecordPage = new Page<>();
        rechargeRecordPage.setCurrent(Integer.parseInt(page));
        rechargeRecordPage.setSize(Integer.parseInt(limit));
        QueryWrapper<RechargeRecord> rechargeRecordQueryWrapper = new QueryWrapper<>();
        if (userId != null && !userId.equals("")) {
            rechargeRecordQueryWrapper.eq("user_id", userId);
        }
        if (dormId != null && !dormId.equals("")) {
            rechargeRecordQueryWrapper.eq("dorm_id", dormId);
        }
        if (startDate != null && !startDate.equals("")) {
            rechargeRecordQueryWrapper.ge("payment_time", startDate);
        }
        if (endDate != null && !endDate.equals("")) {
            rechargeRecordQueryWrapper.le("payment_time", endDate);
        }
        List<RechargeRecordVo> page1 = rechargeRecordService.pageInfo(rechargeRecordPage, rechargeRecordQueryWrapper, buildingId);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", page1.size());
        map.put("data", page1);
        return map;
    }

    @PostMapping("/recharge")
    @ResponseBody
    @Transactional
    public Result<String> recharge(@RequestBody RechargeRecord rechargeRecord) {
        String userId = (String) StpUtil.getLoginId();
        log.info("userId:{}", userId);
        log.info("dormId:{}", rechargeRecord.getDormId());
        log.info("amount:{}", rechargeRecord.getAmount());

        if (rechargeRecord.getDormId() == null) {
           rechargeRecord.setDormId(userService.getById(userId).getDormId());
        }

        Dorm dorm = dormService.getById(rechargeRecord.getDormId());
        dorm.setDeposit(dorm.getDeposit().add(rechargeRecord.getAmount()));
        boolean b = dormService.updateById(dorm);
        if (!b) {
            return Result.failed("充值失败，请稍后再试");
        }
        rechargeRecord.setUserId(Long.valueOf(userId));
        rechargeRecord.setPaymentTime(LocalDateTime.now());
        boolean save = rechargeRecordService.save(rechargeRecord);
        if (save) {
            return Result.success("充值成功");
        } else {
            return Result.failed("充值记录生成失败，请稍后再试");
        }
    }

}
