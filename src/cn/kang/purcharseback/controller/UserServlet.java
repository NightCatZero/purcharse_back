package cn.kang.purcharseback.controller;

import cn.kang.purcharseback.pojo.User;
import cn.kang.purcharseback.service.UserService;
import cn.kang.purcharseback.service.impl.UserServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = "/user/usersvl")
public class UserServlet extends HttpServlet {
    private UserService userService=new UserServiceImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("user");
        String password=request.getParameter("pwd");

        if(username==null || username.equals("") || password==null || password.equals("")){
            request.setAttribute("errinfo","用户名及密码不能为空!");
            request.getRequestDispatcher("/houtai/index.jsp").forward(request,response);
        }
        User user=new User(null,username,password);
        User user1=userService.login(user);
        if(user1==null){
            request.setAttribute("errinfo","用户名或密码错误!");
            request.getRequestDispatcher("/houtai/index.jsp").forward(request,response);
        }
        request.getSession().setAttribute("user",user1);
        Cookie ck_username=new Cookie("username",user1.getUsername());
        Cookie ck_password=new Cookie("password",user1.getPassword());
        ck_username.setMaxAge(60*60*24*7);
        ck_password.setMaxAge(60*60*24*7);
        ck_username.setPath("/");
        ck_password.setPath("/");
        response.addCookie(ck_username);
        response.addCookie(ck_password);
        response.sendRedirect("../houtai/main.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
