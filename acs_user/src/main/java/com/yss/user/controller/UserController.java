package com.yss.user.controller;

import com.yss.common.entity.Result;
import com.yss.common.entity.StatusCode;
import com.yss.common.util.JwtUtil;
import com.yss.user.entity.User;
import com.yss.user.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 用户登录
     *
     * @param loginMap
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        User user = userService.findByMobileAndPassword(loginMap.get("mobile"), loginMap.get("password"));
        if (user != null) {
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("name", user.getNickname());
            map.put("avater", user.getAvatar());
            return new Result(true, StatusCode.OK, "登录成功", map);
        } else {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Claims claims = (Claims) request.getAttribute("admin_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 增加粉丝数
     *
     * @param userid
     * @param count
     * @return
     */
    @RequestMapping(value = "/incfans/{userid}/{count}", method = RequestMethod.POST)
    public Result incFanscount(@PathVariable String userid, @PathVariable int count) {
        userService.incFanscount(userid, count);
        return new Result(true, StatusCode.OK, "粉丝数增加成功");
    }

    /**
     * 增加关注数
     *
     * @param userid
     * @param count
     * @return
     */
    @RequestMapping(value = "/incfollow/{userid}/{count}", method = RequestMethod.POST)
    public Result incFollowcount(@PathVariable String userid, @PathVariable int count) {
        userService.incFollowcount(userid, count);
        return new Result(true, StatusCode.OK, "关注数增加成功");
    }
}
