package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.domain.vo.DormVo;
import com.mjl.dfglxtv3.service.DormService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (dorm)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/dorm")
public class DormController {
    /**
     * 服务对象
     */
    @Resource
    private DormService dormService;

    /**
     * 获取全部记录
     *
     * @return 所有
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list() {
        List<DormVo> list = dormService.listInfo();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "...");
        map.put("count", list.size());
        map.put("data", list);
        return map;
    }

}
