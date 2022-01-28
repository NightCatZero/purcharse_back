package cn.kang.purcharseback.filter;

import cn.kang.purcharseback.pojo.Customer;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "QtCustFilter",urlPatterns = "/qiantai/flow.jsp",dispatcherTypes = {DispatcherType.FORWARD,DispatcherType.REQUEST})
public class QtCustFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1=(HttpServletRequest) request;
        HttpServletResponse response1=(HttpServletResponse) response;
        Customer customer=(Customer) request1.getSession().getAttribute("customer");
        if(customer==null){
            response1.sendRedirect("../qiantai/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
