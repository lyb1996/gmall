package com.steven.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.steven.gmall.bean.UmsMember;
import com.steven.gmall.bean.UmsMemberReceiveAddress;
import com.steven.gmall.service.UserService;
import com.steven.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.steven.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMemberList = userMapper.selectAll();
        return umsMemberList;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress ums = new UmsMemberReceiveAddress();
        ums.setMemberId(memberId);
        List<UmsMemberReceiveAddress> UmsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(ums);
        return UmsMemberReceiveAddresses;
    }

    @Override
    public List<UmsMember> addUser(UmsMember user) {
        System.out.printf(user.toString());
        userMapper.insert(user);
        List<UmsMember> umsMemberList = userMapper.selectAll();
        return umsMemberList;
    }

    @Override
    public List<UmsMember> deleteUser(String id) {
        UmsMember user = new UmsMember();
        user.setId(id);
        userMapper.delete(user);
        List<UmsMember> umsMemberList = userMapper.selectAll();
        return umsMemberList;
    }
}
