package cn.wolfcode.getip.service;

import cn.wolfcode.getip.domain.User;

public interface IIPService {
    /**
     * 用户的注册和放的Redis里面
     * @param ip ip地址
     * @param user 用户名称
     * @return
     */
    Boolean register(String ip, User user);
    /*
    * 把ip添加进redis
    * */

}
