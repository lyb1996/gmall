package com.steven.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.steven.gmall.bean.UmsMember;
import com.steven.gmall.bean.UmsMemberReceiveAddress;
import com.steven.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("getAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        List<UmsMemberReceiveAddress> UmsMemberReceiveAddresses = userService.getReceiveAddressByMemberId(memberId);
        return UmsMemberReceiveAddresses;
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }

    @RequestMapping("addUser")
    @ResponseBody
    public List<UmsMember> addUser(UmsMember user) {
        return userService.addUser(user);
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public List<UmsMember> deleteUser(String id) {
        return userService.deleteUser(id);
    }


    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "hello user";
    }

}
