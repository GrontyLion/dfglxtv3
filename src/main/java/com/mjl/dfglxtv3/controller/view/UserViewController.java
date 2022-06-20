package com.mjl.dfglxtv3.controller.view;

import cn.dev33.satoken.stp.StpUtil;
import com.mjl.dfglxtv3.domain.*;
import com.mjl.dfglxtv3.domain.vo.BuildingVo;
import com.mjl.dfglxtv3.domain.vo.DormVo;
import com.mjl.dfglxtv3.domain.vo.UserVO;
import com.mjl.dfglxtv3.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Slf4j
public class UserViewController {

    @Resource
    private DormService dormService;

    @Resource
    private DormTypeService dormTypeService;

    @Resource
    private BuildingService buildingService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private ElectrovalencyTypeService electrovalencyTypeService;
    @GetMapping(value = "/")
    public String index1() {
        try {
            if (!StpUtil.isLogin()) {
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping(value = "/index")
    public String index2() {
        try {
            if (!StpUtil.isLogin()) {
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    //@GetMapping(value = "/console/console1")
    //public ModelAndView console1() {
    //    ModelAndView mv = new ModelAndView();
    //    try {
    //        if (!StpUtil.isLogin()) {
    //            mv.setViewName("login");
    //            return mv;
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    List<Dorm> list = dormService.list();
    //    mv.setViewName("console/console1");
    //    mv.addObject("dormList", list);
    //    return mv;
    //}

    @GetMapping(value = "/login")
    public String login() {
        try {
            if (StpUtil.isLogin()) {
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView register = new ModelAndView("register");
        List<Dorm> list = dormService.list();
        register.addObject("dormList", list);
        return register;
    }

    @GetMapping(value = "/forget")
    public String forget() {
        return "forget";
    }

    @GetMapping(value = "/reset")
    public String reset() {
        return "reset";
    }


    @GetMapping(value = "/dorm/dorm")
    public ModelAndView dorm() {
        ModelAndView dorm = new ModelAndView("dorm/dorm");
        List<DormType> list = dormTypeService.list();
        dorm.addObject("dormTypeList", list);
        List<Building> list1 = buildingService.list();
        dorm.addObject("buildingList", list1);
        return dorm;
    }

    @GetMapping(value = "/dorm/operate/add")
    public ModelAndView dormOperateAdd() {
        ModelAndView operateAdd = new ModelAndView("dorm/operate/add");
        List<DormType> list = dormTypeService.list();
        operateAdd.addObject("dormTypeList", list);
        List<Building> list1 = buildingService.list();
        operateAdd.addObject("buildingList", list1);
        return operateAdd;
    }

    @GetMapping(value = "/dorm/operate/addDormType")
    public ModelAndView dormOperateAddDormType() {
        return new ModelAndView("dorm/operate/addDormType");
    }

    @GetMapping(value = "/dorm/operate/deleteDormType")
    public ModelAndView dormOperateDeleteDormType() {
        ModelAndView modelAndView = new ModelAndView("dorm/operate/deleteDormType");
        List<DormType> list = dormTypeService.list();
        modelAndView.addObject("dormTypeList", list);
        return modelAndView;
    }


    @GetMapping(value = "/dorm/operate/edit")
    public ModelAndView dormOperateEdit(Long id) {
        ModelAndView operateAdd = new ModelAndView("dorm/operate/edit");
        DormVo dormVo = new DormVo();
        Dorm dorm = dormService.getById(id);
        Building building = buildingService.getById(dorm.getBuildingId());
        DormType dormType = dormTypeService.getById(dorm.getDormTypeId());
        BeanUtils.copyProperties(dorm, dormVo);
        dormVo.setBuildingName(building.getName());
        dormVo.setDormTypeName(dormType.getName());
        List<DormType> list = dormTypeService.list();
        List<Building> list1 = buildingService.list();
        operateAdd.addObject("dormTypeList", list);
        operateAdd.addObject("buildingList", list1);
        operateAdd.addObject("dormVo", dormVo);
        return operateAdd;
    }


    @GetMapping(value = "/user/user")
    public ModelAndView user() {
        ModelAndView user = new ModelAndView("user/user");
        List<Role> list = roleService.list();
        List<Building> list1 = buildingService.list();
        List<Dorm> list2 = dormService.list();
        user.addObject("roleList", list);
        user.addObject("buildingList", list1);
        user.addObject("dormList", list2);
        return user;
    }

    @GetMapping(value = "/user/operate/add")
    public ModelAndView userOperateAdd() {
        ModelAndView operateAdd = new ModelAndView("/user/operate/add");
        List<Role> list = roleService.list();
        List<Dorm> list1 = dormService.list();
        operateAdd.addObject("roleList", list);
        operateAdd.addObject("dormList", list1);
        return operateAdd;
    }

    @GetMapping(value = "/user/operate/edit")
    public ModelAndView userOperateEdit(Long id) {
        log.info("id:{}", id);
        ModelAndView operateAdd = new ModelAndView("user/operate/edit");
        UserVO userVO = new UserVO();
        User user = userService.getById(id);
        Dorm dorm = dormService.getById(user.getDormId());
        Building building = buildingService.getById(dorm.getBuildingId());
        Role role = roleService.getById(user.getRoleId());
        BeanUtils.copyProperties(user, userVO);
        userVO.setBuildingName(building.getName());
        userVO.setDormName(dorm.getName());
        userVO.setRoleName(role.getName());
        userVO.setBuildingId(building.getId());
        log.info("userVO:{}", userVO);

        List<Role> list = roleService.list();
        List<Dorm> list1 = dormService.list();
        List<Building> list2 = buildingService.list();
        operateAdd.addObject("roleList", list);
        operateAdd.addObject("dormList", list1);
        operateAdd.addObject("buildingList", list2);
        operateAdd.addObject("userVO", userVO);
        return operateAdd;
    }

    @GetMapping(value = "/building/building")
    public ModelAndView building() {
        ModelAndView building = new ModelAndView("building/building");
        List<ElectrovalencyType> list = electrovalencyTypeService.list();
        building.addObject("electrovalencyTypeList", list);
        return building;
    }

    @GetMapping(value = "/building/operate/add")
    public ModelAndView buildingOperateAdd() {
        ModelAndView operateAdd = new ModelAndView("/building/operate/add");
        List<ElectrovalencyType> list = electrovalencyTypeService.list();
        operateAdd.addObject("electrovalencyTypeList", list);
        return operateAdd;
    }

    @GetMapping(value = "/building/operate/edit")
    public ModelAndView buildingOperateEdit(Long id) {
        ModelAndView operateAdd = new ModelAndView("building/operate/edit");
        BuildingVo buildingVo = new BuildingVo();
        Building building = buildingService.getById(id);
        ElectrovalencyType electrovalencyType = electrovalencyTypeService.getById(building.getElectrovalencyTypeId());
        BeanUtils.copyProperties(building, buildingVo);
        buildingVo.setElectrovalencyTypeName(electrovalencyType.getName());
        List<ElectrovalencyType> list = electrovalencyTypeService.list();
        operateAdd.addObject("electrovalencyTypeList", list);
        operateAdd.addObject("buildingVo", buildingVo);
        return operateAdd;
    }

    @GetMapping(value = "/building/operate/addElectrovalencyType")
    public ModelAndView buildingOperateAddElectrovalencyType() {
        return new ModelAndView("building/operate/addElectrovalencyType");
    }

    @GetMapping(value = "/building/operate/deleteElectrovalencyType")
    public ModelAndView buildingOperateDeleteDormType() {
        ModelAndView modelAndView = new ModelAndView("building/operate/deleteElectrovalencyType");
        List<ElectrovalencyType> list = electrovalencyTypeService.list();
        modelAndView.addObject("electrovalencyTypeList", list);
        return modelAndView;
    }

    @GetMapping(value = "/building/operate/updateElectrovalencyType")
    public ModelAndView buildingOperateUpdateDormType() {
        ModelAndView modelAndView = new ModelAndView("building/operate/updateElectrovalencyType");
        List<ElectrovalencyType> list = electrovalencyTypeService.list();
        modelAndView.addObject("electrovalencyTypeList", list);
        return modelAndView;
    }

    @GetMapping(value = "/electricityRecord/electricityRecord")
    public ModelAndView electricityRecord() {
        ModelAndView building = new ModelAndView("electricityRecord/electricityRecord");
        List<Dorm> list = dormService.list();
        List<Building> list1 = buildingService.list();
        building.addObject("dormList", list);
        building.addObject("buildingList", list1);
        return building;
    }

    @GetMapping(value = "/electricityRecord/operate/add")
    public ModelAndView electricityRecordOperateAdd() {
        ModelAndView building = new ModelAndView("electricityRecord/operate/add");
        List<Dorm> list = dormService.list();
        building.addObject("dormList", list);
        return building;
    }

    @GetMapping(value = "/rechargeRecord/rechargeRecord")
    public ModelAndView rechargeRecord() {
        ModelAndView building = new ModelAndView("rechargeRecord/rechargeRecord");
        List<Dorm> list = dormService.list();
        List<Building> list1 = buildingService.list();
        List<User> list2 = userService.list();
        building.addObject("dormList", list);
        building.addObject("buildingList", list1);
        building.addObject("userList", list2);
        return building;
    }

    @GetMapping(value = "/rechargeRecord/operate/add")
    public ModelAndView rechargeRecordOperateAdd() {
        ModelAndView rechargeRecord = new ModelAndView("rechargeRecord/operate/add");
        List<Dorm> list = dormService.list();
        rechargeRecord.addObject("dormList", list);
        return rechargeRecord;
    }
    @GetMapping(value = "/error/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping(value = "/error/404")
    public String error404() {
        return "/error/404";
    }

    @GetMapping(value = "/error/500")
    public String error500() {
        return "/error/500";
    }

    @GetMapping(value = "/result/error")
    public String resultError() {
        return "/result/error";
    }

    @GetMapping(value = "/result/success")
    public String resultSuccess() {
        return "/result/success";
    }

    @GetMapping(value = "/system/deptment")
    public String systemDeptment() {
        return "/system/deptment";
    }

}
