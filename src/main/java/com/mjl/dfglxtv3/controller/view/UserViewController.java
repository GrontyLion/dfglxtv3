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

    @GetMapping(value = "/console/console1")
    public String console1() {
        try {
            if (!StpUtil.isLogin()) {
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "console/console1";
    }

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

    @GetMapping(value = "/console/console2")
    public String consoleConsole2() {
        return "/console/console2";
    }

    @GetMapping(value = "/demo/index")
    public String demoIndex() {
        return "/demo/index";
    }

    @GetMapping(value = "/document/area")
    public String documentArea() {
        return "/document/area";
    }

    @GetMapping(value = "/document/button")
    public String documentButton() {
        return "/document/button";
    }

    @GetMapping(value = "/document/card")
    public String documentCard() {
        return "/document/card";
    }

    @GetMapping(value = "/document/core")
    public String documentCore() {
        return "/document/core";
    }

    @GetMapping(value = "/document/count")
    public String documentCount() {
        return "/document/count";
    }

    @GetMapping(value = "/document/drawer")
    public String documentDrawer() {
        return "/document/drawer";
    }

    @GetMapping(value = "/document/drawerFragment")
    public String documentDrawerFragment() {
        return "/document/drawerFragment";
    }

    @GetMapping(value = "/document/dtree")
    public String documentDtree() {
        return "/document/dtree";
    }

    @GetMapping(value = "/document/encrypt")
    public String documentEncrypt() {
        return "/document/encrypt";
    }

    @GetMapping(value = "/document/form")
    public String documentForm() {
        return "/document/form";
    }

    @GetMapping(value = "/document/icon")
    public String documentIcon() {
        return "/document/icon";
    }

    @GetMapping(value = "/document/iconPicker")
    public String documentIconPicker() {
        return "/document/iconPicker";
    }

    @GetMapping(value = "/document/loading")
    public String documentLoading() {
        return "/document/loading";
    }

    @GetMapping(value = "/document/menu")
    public String documentMenu() {
        return "/document/menu";
    }

    @GetMapping(value = "/document/notice")
    public String documentNotice() {
        return "/document/notice";
    }

    @GetMapping(value = "/document/popup")
    public String documentPopup() {
        return "/document/popup";
    }

    @GetMapping(value = "/document/select")
    public String documentSelect() {
        return "/document/select";
    }

    @GetMapping(value = "/document/step")
    public String documentStep() {
        return "/document/step";
    }

    @GetMapping(value = "/document/tab")
    public String documentTab() {
        return "/document/tab";
    }

    @GetMapping(value = "/document/tabContent")
    public String documentTabContent() {
        return "/document/tabContent";
    }

    @GetMapping(value = "/document/table")
    public String documentTable() {
        return "/document/table";
    }

    @GetMapping(value = "/document/tag")
    public String documentTag() {
        return "/document/tag";
    }

    @GetMapping(value = "/document/tinymce")
    public String documentTinymce() {
        return "/document/tinymce";
    }

    @GetMapping(value = "/document/toast")
    public String documentToast() {
        return "/document/toast";
    }

    @GetMapping(value = "/document/topBar")
    public String documentTopBar() {
        return "/document/topBar";
    }

    @GetMapping(value = "/document/treetable")
    public String documentTreetable() {
        return "/document/treetable";
    }

    @GetMapping(value = "/echarts/column")
    public String echartsColumn() {
        return "/echarts/column";
    }

    @GetMapping(value = "/echarts/line")
    public String echartsLine() {
        return "/echarts/line";
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

    @GetMapping(value = "/system/dict")
    public String systemDict() {
        return "/system/dict";
    }

    @GetMapping(value = "/system/log")
    public String systemLog() {
        return "/system/log";
    }

    @GetMapping(value = "/system/operate/add")
    public String systemOperateAdd() {
        return "/system/operate/add";
    }

    @GetMapping(value = "/system/operate/edit")
    public String systemOperateEdit() {
        return "/system/operate/edit";
    }

    @GetMapping(value = "/system/operate/profile")
    public String systemOperateProfile() {
        return "/system/operate/profile";
    }

    @GetMapping(value = "/system/operate")
    public String systemOperate() {
        return "/system/operate";
    }

    @GetMapping(value = "/system/person")
    public String systemPerson() {
        return "/system/person";
    }

    @GetMapping(value = "/system/power")
    public String systemPower() {
        return "/system/power";
    }

    @GetMapping(value = "/system/profile")
    public String systemProfile() {
        return "/system/profile";
    }

    @GetMapping(value = "/system/role")
    public String systemRole() {
        return "/system/role";
    }

    @GetMapping(value = "/system/space")
    public String systemSpace() {
        return "/system/space";
    }

    @GetMapping(value = "/system/theme")
    public String systemTheme() {
        return "/system/theme";
    }

    @GetMapping(value = "/system/user")
    public String systemUser() {
        return "/system/user";
    }


}
