package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.RechargeRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (recharge_record)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/recharge_record")
public class RechargeRecordController {
/**
* 服务对象
*/
@Resource
private RechargeRecordService rechargeRecordService;


}
