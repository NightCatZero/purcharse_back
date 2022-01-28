package cn.kang.purcharseback.controller;

import cn.kang.purcharseback.pojo.Customer;
import cn.kang.purcharseback.service.CustService;
import cn.kang.purcharseback.service.impl.CustServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CustServlet", urlPatterns = "/qt/custsvl")
public class CustServlet extends HttpServlet {
    private CustService custService=new CustServiceImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bs=request.getParameter("bs");
        if("register".equals(bs)){
            registCust(request,response);
        }else if("login".equals(bs)){
            loginCust(request,response);
        }
    }

    private void loginCust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String custname=request.getParameter("username");
        String password=request.getParameter("password");
        String validate=request.getParameter("validate");
        if(custname==null || custname.equals("") || password==null || password.equals("")){
            request.setAttribute("errinfo","用户名及密码不能为空!");
            request.getRequestDispatcher("/qiantai/login.jsp").forward(request,response);
        }
        if(validate==null || validate.equals("")){
            request.setAttribute("errinfo","验证码不能为空!");
            request.getRequestDispatcher("/qiantai/login.jsp").forward(request,response);
        }
        Customer customer= custService.loginCust(custname,password);
        if(customer==null){
            request.setAttribute("errinfo","用户名或密码错误!");
            request.getRequestDispatcher("/qiantai/login.jsp").forward(request,response);
        }
        String validation_code = String.valueOf(request.getSession().getAttribute("validation_code"));
        if(!validation_code.equals(validate)){
            request.setAttribute("errinfo","验证码错误!");
            request.getRequestDispatcher("/qiantai/login.jsp").forward(request,response);
        }
        request.getSession().setAttribute("customer",customer);
        request.getSession().setAttribute("custname",customer.getCustname());
        Cookie ck_custname=new Cookie("custname",customer.getCustname());
        Cookie ck_password=new Cookie("custpwd", customer.getPassword());
        ck_custname.setMaxAge(60*60*24*7);
        ck_password.setMaxAge(60*60*24*7);
        ck_custname.setPath("/");
        ck_password.setPath("/");
        response.addCookie(ck_custname);
        response.addCookie(ck_password);
        response.sendRedirect("../qt/goodsvl?bs=open");
    }

    private void registCust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String custname=request.getParameter("username");
        String custmail=request.getParameter("email");
        String password1=request.getParameter("password");
        String password2=request.getParameter("confirm_password");
        String custqq=request.getParameter("qqcode");

        if(password1.equals(password2) && password1!=null && password2!=null && !password1.equals("") && !password2.equals("")){
            try {
                if(custService.checkName(custname)){
                    request.setAttribute("errinfo","此用户已存在");
                    request.getRequestDispatcher("../qiantai/register.jsp").forward(request,response);
                    return;
                }
                if(custService.checkMail(custmail)){
                    request.setAttribute("errinfo","此邮箱已被注册");
                    request.getRequestDispatcher("../qiantai/register.jsp").forward(request,response);
                    return;
                }
                custService.registCust(custname,custmail,password1,custqq);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        request.getRequestDispatcher("../qiantai/message.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
