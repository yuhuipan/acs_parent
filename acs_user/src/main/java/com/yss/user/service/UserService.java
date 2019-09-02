package com.yss.user.service;

import com.yss.user.dao.UserDao;
import com.yss.user.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号
     */
    public void sendSms(String mobile) {
        // 生成6位短信验证码
        Random random = new Random();
        int max = 999999;
        int min = 100000;
        int code = random.nextInt(max);
        if (code < min) {
            code = code + min;
        }
        System.out.println(mobile + "收到的验证码是：" + code);
        // 将验证码放到redis, 5分钟过期
        redisTemplate.opsForValue().set("smscode" + mobile, code + "", 5, TimeUnit.MINUTES);
        // 将验证码和手机号发送到rabbitMQ中
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code + "");
        rabbitTemplate.convertAndSend("sms", map);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    public User findByMobileAndPassword(String mobile, String password) {
        return null;
    }

    /**
     * 更新粉丝数
     *
     * @param userid
     * @param count
     */
    @Transactional
    public void incFanscount(String userid, int count) {
        userDao.incFanscount(userid, count);
    }

    /**
     * 更新关注数
     *
     * @param userid
     * @param count
     */
    @Transactional
    public void incFollowcount(String userid, int count) {
        userDao.incFollowcount(userid, count);
    }
}
