package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.DormTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (dorm_type)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/dorm_type")
public class DormTypeController {
/**
* 服务对象
*/
@Resource
private DormTypeService dormTypeService;

}
