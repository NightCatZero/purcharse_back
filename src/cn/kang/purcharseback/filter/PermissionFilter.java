package cn.kang.purcharseback.filter;

import cn.kang.purcharseback.pojo.User;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            response.sendRedirect("index.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }
}
