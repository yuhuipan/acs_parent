package com.yss.friend.client;

import com.yss.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户客户端
 */
@FeignClient("acs-user")
public interface UserClient {

    /**
     * 增加粉丝数
     *
     * @param userid
     * @param count
     * @return
     */
    @RequestMapping(value = "/user/incfans/{userid}/{count}", method = RequestMethod.POST)
    Result incFanscount(@PathVariable String userid, @PathVariable int count);


    /**
     * 增加关注数
     *
     * @param userid
     * @param count
     * @return
     */
    @RequestMapping(value = "/user/incfollow/{userid}/{count}", method = RequestMethod.POST)
    Result incFollowcount(@PathVariable String userid, @PathVariable int count);

}
