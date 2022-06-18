package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.service.ElectrovalencyTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (electrovalency_type)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/api/electrovalency_type")
public class ElectrovalencyTypeController {
/**
* 服务对象
*/
@Resource
private ElectrovalencyTypeService electrovalencyTypeService;

}
