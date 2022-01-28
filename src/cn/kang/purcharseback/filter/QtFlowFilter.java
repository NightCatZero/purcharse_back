package cn.kang.purcharseback.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "QtFlowFilter",urlPatterns = "/qiantai/index.jsp",dispatcherTypes = {DispatcherType.FORWARD,DispatcherType.REQUEST})
public class QtFlowFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1=(HttpServletRequest) request;
        HttpServletResponse response1=(HttpServletResponse) response;
        Integer toAccount = (Integer) request1.getSession().getAttribute("toAccount");
        Integer toAmount = (Integer) request1.getSession().getAttribute("toAmount");
        if(toAccount==null || toAmount==null){
            request1.getSession().setAttribute("toAccount",0);
            request1.getSession().setAttribute("toAmount",0);
        }
        chain.doFilter(request, response);
    }
}
