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

    //@RequestMapping("/list")
    //@ResponseBody
    //public Map<String, Object> list(String name, Long electrovalencyTypeId, String page, String limit) {
    //    Map<String, Object> map = new HashMap<>();
    //    Page<ElectrovalencyType> page1 = new Page<>();
    //    page1.setCurrent(Integer.parseInt(page));
    //    page1.setSize(Integer.parseInt(limit));
    //    QueryWrapper<ElectrovalencyType> electrovalencyTypeQueryWrapper = new QueryWrapper<>();
    //    if (name != null && !"".equals(name)) {
    //        electrovalencyTypeQueryWrapper.like("name", name);
    //    }
    //    if (electrovalencyTypeId != null) {
    //        electrovalencyTypeQueryWrapper.eq("electrovalency_type_id", electrovalencyTypeId);
    //    }
    //}
}
