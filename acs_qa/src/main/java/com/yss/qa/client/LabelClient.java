package com.yss.qa.client;

import com.yss.common.entity.Result;
import com.yss.qa.client.impl.LabelClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 标签客户端
 * <p>
 * <p>
 * * @FeignClient 注解用于指定从哪个服务中调用功能 ，***注意***  里面的名称与被调用的服务名保持一致，并且不能包含下划线。
 * * @RequestMapping 注解用于对被调用的微服务进行地址映射。***注意*** @PathVariable注解一定要指定参数名称，否则出错
 */
@FeignClient(value = "acs-base", fallback = LabelClientImpl.class)
public interface LabelClient {
    //根据id查询标签(方法需要和acs-base名字的方法名一致, 路径需要加上类上面的@RequestMapping)
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    Result findById(@PathVariable("labelId") String labelId);
}
