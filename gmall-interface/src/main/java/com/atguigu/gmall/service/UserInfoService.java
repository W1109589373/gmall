package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> getUserInfoList();

    public UserInfo login(UserInfo userInfo);


    UserInfo verify(String userId);
}
