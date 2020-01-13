package cn.wolfcode.getip.service.impl;

import cn.wolfcode.getip.domain.User;
import cn.wolfcode.getip.service.IIPService;
import cn.wolfcode.getip.service.IUserService;
import cn.wolfcode.getip.util.RedisKeys;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class IPServiceImpl implements IIPService {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private IUserService userService;
    //MQ的发送消息
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Boolean register(String ip, User user) {
        //判断这个Ip在今天内注册多少次
        //1判断有没有没有添加 1 悠久再加1
        String key = RedisKeys.IP_KEY.join(ip);
        System.err.println(key);
        String msg = user.getUsername();
        if (!template.hasKey(key)){
            //没有的时候 添加到Redis
            template.opsForValue().set(key,"1",60*60*24,TimeUnit.MINUTES);

            //todo 操作Mysql数据库...
            user.setTime(new Date());
            userService.save(user);
            rabbitTemplate.convertAndSend("","Register_Mail",msg);
            return true;
        }else {
            //有的情况下得到数值,判断时候大于3 大于就返回false;
            String countStr = template.opsForValue().get(key);
            int coutn = Integer.parseInt(countStr);
            if (coutn <3){
                //让这个Ip继续注册 加1
                String  newCount = String.valueOf(coutn+1);
                template.opsForValue().set(key,newCount,60*60*24,TimeUnit.MINUTES);

                //todo 操作Mysql数据库...
                user.setTime(new Date());
                userService.save(user);

                rabbitTemplate.convertAndSend("","Register_Mail",msg);
                return true;
            }else {
                return false;
            }
        }
    }
/*
    @Override
    public void setIpCode(String ip, String username) {
        if(ip != null && username != null){
            String key = RedisKeys.IP_KEY.join(ip,username);
            template.opsForValue().set(key,username,60*60*24, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean getIpCode(String ip,String username) {
        String key = template.opsForValue().get(RedisKeys.IP_KEY.join(ip,username));
        if(key == null){
            this.setIpCode(ip,username);
        }
        String key1 = template.opsForValue().get(RedisKeys.IP_KEY.join(ip));
        Set<String> keys = template.keys(key1);
        if (keys.size()<3){
                this.setIpCode(ip,username);

        }
        if(keys.size()>3){
            return false;
        }
        return true;
    }*/
}
