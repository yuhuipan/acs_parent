package com.yss.user.controller;

import com.yss.common.entity.PageResult;
import com.yss.common.entity.Result;
import com.yss.common.entity.StatusCode;
import com.yss.common.util.JwtUtil;
import com.yss.user.entity.Admin;
import com.yss.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     * @param map
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        Admin admin = adminService.login(map.get("loginname"), map.get("password"));
        if (admin != null) {
            //登录成功, 要签发token
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            //用map 封装token, 登录名
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("name", admin.getLoginname());
            return new Result(true, StatusCode.OK, "登录成功", resultMap);
        } else {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }


    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
