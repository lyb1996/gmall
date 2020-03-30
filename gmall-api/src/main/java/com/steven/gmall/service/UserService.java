package com.steven.gmall.service;

import com.steven.gmall.bean.UmsMember;
import com.steven.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    List<UmsMember> addUser(UmsMember user);

    List<UmsMember> deleteUser(String id);
}
