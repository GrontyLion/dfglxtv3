package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.SettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (setting)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/setting")
public class SettingController {
/**
* 服务对象
*/
@Resource
private SettingService settingService;


}
