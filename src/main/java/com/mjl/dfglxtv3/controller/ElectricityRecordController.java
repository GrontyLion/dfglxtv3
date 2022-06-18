package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.ElectricityRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (electricity_record)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/electricity_record")
public class ElectricityRecordController {
/**
* 服务对象
*/
@Resource
private ElectricityRecordService electricityRecordService;

}
