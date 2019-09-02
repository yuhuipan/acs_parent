package com.yss.friend.service;

import com.yss.friend.client.UserClient;
import com.yss.friend.dao.FriendDao;
import com.yss.friend.dao.NoFriendDao;
import com.yss.friend.entity.Friend;
import com.yss.friend.entity.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendSerevice {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
     * 添加喜欢
     *
     * @param userid
     * @param friendid
     * @return
     */
    @Transactional
    public int addFriend(String userid, String friendid) {
        // 判断如果用户已经添加了这个好友，则不进行任何操作，返回0
        if (friendDao.selectCount(userid, friendid) > 0) {
            return 0;
        }
        // 向喜欢表中添加记录
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        // 判断对方是否喜欢你，如果喜欢你，将islike设置为1
        if (friendDao.selectCount(friendid, userid) > 0) {
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
        }

        //调用acs_user微服务, 变更粉丝数和关注数 (自己的关注数+1,对方的粉丝数+1 )
        userClient.incFollowcount(userid,1);
        userClient.incFanscount(friendid, 1);
        return 1;
    }

    /**
     * 向不喜欢列表中添加记录
     *
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     *
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid, String friendid) {
        //tb_friend表中当前记录删除;
        friendDao.deleteFriend(userid, friendid);

        //如果是双向喜欢,则另外一方的islike置为0
        friendDao.updateLike(friendid, userid, "0");
        // 向不喜欢列表中添加记录
        addNoFriend(userid, friendid);

        //调用tensquare-user微服务, 粉丝数和关注数变更  (自己的关注数-1,对方的粉丝数-1 )
        userClient.incFollowcount(userid,-1);
        userClient.incFanscount(friendid,-1);
    }
}
