package com.mjl.dfglxtv3.controller.view;

import cn.dev33.satoken.stp.StpUtil;
import com.mjl.dfglxtv3.domain.Dorm;
import com.mjl.dfglxtv3.domain.DormType;
import com.mjl.dfglxtv3.service.DormService;
import com.mjl.dfglxtv3.service.DormTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserViewController {

    @Resource
    private DormService dormService;

    @Resource
    private DormTypeService dormTypeService;

    @GetMapping(value = "/")
    public String index1(){
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
    public String index2(){
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
    public String console1(){
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
    public String login(){
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
    public ModelAndView register(){
        ModelAndView register = new ModelAndView("register");
        List<Dorm> list = dormService.list();
        register.addObject("dormList", list);
        return register;
    }

    @GetMapping(value = "/dorm/dorm")
    public ModelAndView dorm(){
        ModelAndView dorm = new ModelAndView("dorm/dorm");
        List<DormType> list = dormTypeService.list();
        dorm.addObject("dormTypeList", list);
        return dorm;
    }




    @GetMapping(value = "/console/console2")
    public String consoleConsole2(){
        return "/console/console2";
    }

    @GetMapping(value = "/demo/index")
    public String demoIndex(){
        return "/demo/index";
    }

    @GetMapping(value = "/document/area")
    public String documentArea(){
        return "/document/area";
    }

    @GetMapping(value = "/document/button")
    public String documentButton(){
        return "/document/button";
    }

    @GetMapping(value = "/document/card")
    public String documentCard(){
        return "/document/card";
    }

    @GetMapping(value = "/document/core")
    public String documentCore(){
        return "/document/core";
    }

    @GetMapping(value = "/document/count")
    public String documentCount(){
        return "/document/count";
    }

    @GetMapping(value = "/document/drawer")
    public String documentDrawer(){
        return "/document/drawer";
    }

    @GetMapping(value = "/document/drawerFragment")
    public String documentDrawerFragment(){
        return "/document/drawerFragment";
    }

    @GetMapping(value = "/document/dtree")
    public String documentDtree(){
        return "/document/dtree";
    }

    @GetMapping(value = "/document/encrypt")
    public String documentEncrypt(){
        return "/document/encrypt";
    }

    @GetMapping(value = "/document/form")
    public String documentForm(){
        return "/document/form";
    }

    @GetMapping(value = "/document/icon")
    public String documentIcon(){
        return "/document/icon";
    }

    @GetMapping(value = "/document/iconPicker")
    public String documentIconPicker(){
        return "/document/iconPicker";
    }

    @GetMapping(value = "/document/loading")
    public String documentLoading(){
        return "/document/loading";
    }

    @GetMapping(value = "/document/menu")
    public String documentMenu(){
        return "/document/menu";
    }

    @GetMapping(value = "/document/notice")
    public String documentNotice(){
        return "/document/notice";
    }

    @GetMapping(value = "/document/popup")
    public String documentPopup(){
        return "/document/popup";
    }

    @GetMapping(value = "/document/select")
    public String documentSelect(){
        return "/document/select";
    }

    @GetMapping(value = "/document/step")
    public String documentStep(){
        return "/document/step";
    }

    @GetMapping(value = "/document/tab")
    public String documentTab(){
        return "/document/tab";
    }

    @GetMapping(value = "/document/tabContent")
    public String documentTabContent(){
        return "/document/tabContent";
    }

    @GetMapping(value = "/document/table")
    public String documentTable(){
        return "/document/table";
    }

    @GetMapping(value = "/document/tag")
    public String documentTag(){
        return "/document/tag";
    }

    @GetMapping(value = "/document/tinymce")
    public String documentTinymce(){
        return "/document/tinymce";
    }

    @GetMapping(value = "/document/toast")
    public String documentToast(){
        return "/document/toast";
    }

    @GetMapping(value = "/document/topBar")
    public String documentTopBar(){
        return "/document/topBar";
    }

    @GetMapping(value = "/document/treetable")
    public String documentTreetable(){
        return "/document/treetable";
    }

    @GetMapping(value = "/echarts/column")
    public String echartsColumn(){
        return "/echarts/column";
    }

    @GetMapping(value = "/echarts/line")
    public String echartsLine(){
        return "/echarts/line";
    }

    @GetMapping(value = "/error/403")
    public String error403(){
        return "/error/403";
    }

    @GetMapping(value = "/error/404")
    public String error404(){
        return "/error/404";
    }

    @GetMapping(value = "/error/500")
    public String error500(){
        return "/error/500";
    }


    @GetMapping(value = "/result/error")
    public String resultError(){
        return "/result/error";
    }

    @GetMapping(value = "/result/success")
    public String resultSuccess(){
        return "/result/success";
    }

    @GetMapping(value = "/system/deptment")
    public String systemDeptment(){
        return "/system/deptment";
    }

    @GetMapping(value = "/system/dict")
    public String systemDict(){
        return "/system/dict";
    }

    @GetMapping(value = "/system/log")
    public String systemLog(){
        return "/system/log";
    }

    @GetMapping(value = "/system/operate/add")
    public String systemOperateAdd(){
        return "/system/operate/add";
    }

    @GetMapping(value = "/system/operate/edit")
    public String systemOperateEdit(){
        return "/system/operate/edit";
    }

    @GetMapping(value = "/system/operate/profile")
    public String systemOperateProfile(){
        return "/system/operate/profile";
    }

    @GetMapping(value = "/system/operate")
    public String systemOperate(){
        return "/system/operate";
    }

    @GetMapping(value = "/system/person")
    public String systemPerson(){
        return "/system/person";
    }

    @GetMapping(value = "/system/power")
    public String systemPower(){
        return "/system/power";
    }

    @GetMapping(value = "/system/profile")
    public String systemProfile(){
        return "/system/profile";
    }

    @GetMapping(value = "/system/role")
    public String systemRole(){
        return "/system/role";
    }

    @GetMapping(value = "/system/space")
    public String systemSpace(){
        return "/system/space";
    }

    @GetMapping(value = "/system/theme")
    public String systemTheme(){
        return "/system/theme";
    }

    @GetMapping(value = "/system/user")
    public String systemUser(){
        return "/system/user";
    }




}
