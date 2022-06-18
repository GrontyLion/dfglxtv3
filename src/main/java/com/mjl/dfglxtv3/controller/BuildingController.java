package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.BuildingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (building)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/building")
public class BuildingController {
/**
* 服务对象
*/
@Resource
private BuildingService buildingService;


}
