package com.yss.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    /*
    - pre：	可以在请求被路由之前调用
    - route：在路由请求时候被调用
    - post：在route和error过滤器之后被调用
    - error：处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
        return "pre"; //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0; // 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否执行过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器执行了");
        // 在过滤器中接收header，转发给微服务
        // 向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 获取header
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }

        return null;
    }
}
