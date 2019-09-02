package com.yss.friend.controller;

import com.yss.common.entity.Result;
import com.yss.common.entity.StatusCode;
import com.yss.friend.service.FriendSerevice;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendSerevice friendSerevice;

    @Autowired
    private HttpServletRequest request;


    /**
     * 添加好友和非好友
     * @param friendid  添加的好友/非好友id
     * @param type  1喜欢  (点击爱心,添加好友) 2不喜欢(点击错×,添加非好友)
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {

        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims != null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        // 如果是喜欢
        if ("1".equals(type)) {
            if (friendSerevice.addFriend(claims.getId(), friendid) == 0) {
                return new Result(false, StatusCode.REPERROR, "已添加此好友");
            }
        } else {
            // 不喜欢，向不喜欢列表中添加记录
            friendSerevice.addNoFriend(claims.getId(), friendid);

        }

        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 删除好友
     *
     * @return
     */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }

        friendSerevice.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
