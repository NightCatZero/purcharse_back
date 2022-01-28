package cn.kang.purcharseback.controller;

import cn.kang.purcharseback.pojo.CarBean;
import cn.kang.purcharseback.pojo.Customer;
import cn.kang.purcharseback.pojo.Good;
import cn.kang.purcharseback.service.GoodService;
import cn.kang.purcharseback.service.impl.GoodServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "QtGoodServlet", urlPatterns = "/qt/goodsvl")
public class QtGoodServlet extends HttpServlet {
    private GoodService goodService=new GoodServiceImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bs=request.getParameter("bs");
        if("open".equals(bs)){
            open(request,response);
        }else if("getImg".equals(bs)){
            getImg(request,response);
        }else if ("addGood".equals(bs)){
            addGood(request,response);
        }else if("delGood".equals(bs)){
            delGood(request,response);
        }else if("clear".equals(bs)){
            clearGood(request,response);
        }else if("updateGood".equals(bs)){
            updateGood(request,response);
        }
    }

    private void updateGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] id=request.getParameterValues("id");
        String[] amounts=request.getParameterValues("amount");
        CarBean car=(CarBean) request.getSession().getAttribute("car");
        if(car==null){
            car=new CarBean();
        }
        car.modify(id,amounts);
        request.getSession().setAttribute("car",car);
        List<Good> goods= car.toList();
        request.setAttribute("goods",goods);
        int toAccount = car.totalAccount();
        int toAmount = car.totalAmount();
        request.getSession().setAttribute("toAccount",toAccount);
        request.getSession().setAttribute("toAmount",toAmount);
        request.getRequestDispatcher("../qiantai/flow.jsp").forward(request,response);
    }

    private void clearGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarBean car=(CarBean) request.getSession().getAttribute("car");
        if(car==null){
            car=new CarBean();
        }
        car.clear();
        request.getSession().setAttribute("car",car);
        List<Good> goods= car.toList();
        request.setAttribute("goods",goods);
        int toAccount = car.totalAccount();
        int toAmount = car.totalAmount();
        request.getSession().setAttribute("toAccount",toAccount);
        request.getSession().setAttribute("toAmount",toAmount);
        request.getRequestDispatcher("../qiantai/flow.jsp").forward(request,response);
    }

    private void delGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        CarBean car=(CarBean) request.getSession().getAttribute("car");
        if(car==null){
            car=new CarBean();
        }
        if(id!=null){
            car.removeGood(Integer.valueOf(id));
        }
        request.getSession().setAttribute("car",car);
        List<Good> goods= car.toList();
        request.setAttribute("goods",goods);
        int toAccount = car.totalAccount();
        int toAmount = car.totalAmount();
        request.getSession().setAttribute("toAccount",toAccount);
        request.getSession().setAttribute("toAmount",toAmount);
        request.getRequestDispatcher("../qiantai/flow.jsp").forward(request,response);
    }

    private void addGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        Customer customer=(Customer) request.getSession().getAttribute("customer");
        CarBean car=(CarBean) request.getSession().getAttribute("car");
        if(customer==null){
            response.sendRedirect("../qiantai/login.jsp");
            return;
        }
        if(car==null){
            car=new CarBean();
        }
        if(id!=null){
            car.addGood(id);
        }
        request.getSession().setAttribute("car",car);
        if(car.getCar()==null){
            request.getRequestDispatcher("../qiantai/flow.jsp").forward(request,response);
            return;
        }
        List<Good> goods= car.toList();
        request.setAttribute("goods",goods);
        int toAccount = car.totalAccount();
        int toAmount = car.totalAmount();
        request.getSession().setAttribute("toAccount",toAccount);
        request.getSession().setAttribute("toAmount",toAmount);
        request.getRequestDispatcher("../qiantai/flow.jsp").forward(request,response);
    }

    private void getImg(HttpServletRequest request, HttpServletResponse response) {
        String picname=request.getParameter("pic");
        String path=request.getServletContext().getRealPath("/WEB-INF/upload/"+picname);
        FileInputStream fis=null;
        OutputStream os=null;
        try {
            fis=new FileInputStream(path);
            os= response.getOutputStream();
            int len=-1;
            byte[] b=new byte[1024];
            while ((len= fis.read(b))!=-1){
                os.write(b,0,len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void open(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type=request.getParameter("type");
        try {
            List<Good> goods=null;
            List<String> types=goodService.queryAllType();
            request.setAttribute("types",types);
            if(types.size()>0){
                if(type==null || type.trim().equals("")){
                    goods= goodService.findGoodByType(types.get(0));
                }else {
                    goods= goodService.findGoodByType(type);
                }
            }
            request.setAttribute("goods",goods);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.getRequestDispatcher("../qiantai/index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
