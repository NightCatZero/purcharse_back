package cn.kang.purcharseback.controller;

import cn.kang.purcharseback.pojo.Good;
import cn.kang.purcharseback.service.GoodService;
import cn.kang.purcharseback.service.impl.GoodServiceImp;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "GoodServlet",urlPatterns = "/good/goodsvl",initParams = {@WebInitParam(name = "pageSize",value = "10")})
public class GoodServlet extends HttpServlet {
    private GoodService goodService=new GoodServiceImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bs=request.getParameter("bs");
        if("querybycri".equals(bs)){
            queryByCri(request,response);
        }else if("queryall".equals(bs)){
            queryAllByPage(request,response,null);
        }else if("delete".equals(bs)){
            deleteById(request,response);
        }else if("mod".equals(bs)){
            modifiyById(request,response);
        }
    }

    private void modifiyById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("pid");
        String goodname=request.getParameter("pname");
        String goodtype=request.getParameter("ptype");
        String price=request.getParameter("pprice");
        String oldId=request.getParameter("oldId");
        String pageNow=request.getParameter("pageNow");
        if(goodname!=null || goodtype!=null || price!=null || !goodname.equals("") || !goodtype.equals("") || !price.equals("")){
            try {
                goodService.modifiyGood(id,goodname,goodtype,price,oldId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        queryAllByPage(request,response,pageNow);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        String pageNow=request.getParameter("pageNow");

        //调用业务逻辑，删除商品（待完成）
        try {
            goodService.deleteGood(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        queryAllByPage(request, response,pageNow);
    }

    private void queryAllByPage(HttpServletRequest request, HttpServletResponse response,String pn) throws ServletException, IOException {
        String pageSize=getServletConfig().getInitParameter("pageSize");
        String pageNow;
        if(pn==null){
            pageNow=request.getParameter("pageNow");
        }else {
            pageNow=pn;
        }
        try {
            int totalCount= goodService.queryTotalRow();

            int pageCount=0;
            if(totalCount%Integer.valueOf(pageSize)==0){
                pageCount=totalCount/Integer.valueOf(pageSize);
            }else {
                pageCount=totalCount/Integer.valueOf(pageSize)+1;
            }
            if(Integer.valueOf(pageNow)<1){
                pageNow="1";
            }
            if(Integer.valueOf(pageNow)>pageCount){
                pageNow=pageCount+"";
            }
            List<Good> goods= goodService.queryByPage(pageNow,pageSize);
            request.setAttribute("totalCount",totalCount);
            request.setAttribute("goods",goods);
            request.setAttribute("pageNow",pageNow);
            request.setAttribute("pageCount",pageCount);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.getRequestDispatcher("../houtai/productListUI.jsp").forward(request,response);
    }

    private void queryByCri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("pid");
        String goodname=request.getParameter("pname");
        String goodtype=request.getParameter("ptype");
        List<Good> goods=null;
        try {
            goods= goodService.queryByCri(id,goodname,goodtype);
            request.setAttribute("goods",goods);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.getRequestDispatcher("../houtai/productListUIbycri.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentType = request.getContentType();
        int index=contentType.indexOf("multipart/form-data");
        if(index!=-1){
            upload(request,response);
            return;
        }
        doGet(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Good good=new Good(null,
                (String) request.getAttribute("pname"),
                (String) request.getAttribute("ptype"),
                Double.valueOf((String)request.getAttribute("pprice")),
                (String) request.getAttribute("pimg"));
        try {
            goodService.add(good);
            response.sendRedirect("../houtai/addnewproduct.jsp");
            return;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        request.setAttribute("errinfo","添加商品失败!");
        request.getRequestDispatcher("../houtai/addnewproduct.jsp").forward(request,response);
    }

    public void upload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");    //设置编码

//    创建 FileItem 对象的工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件需要上传到的路径
        String path = getServletContext().getRealPath("/WEB-INF/upload");
        //指定临时文件目录
        factory.setRepository(new File(path));
        //设置内存缓冲区的大小
        factory.setSizeThreshold(1024*1024) ;
        //负责处理上传的文件数据，并将表单中每个输入项封装成一个 FileItem 对象中
        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> list;
        try {
            //调用Upload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
            list = (List<FileItem>)upload.parseRequest(request);
//      对list进行迭代，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
            for(FileItem item : list){
                String name = item.getFieldName();
                if(item.isFormField()){//为普通表单字段
                    String value = new String(item.getString().getBytes("iso-8859-1"),"utf-8") ;
                    request.setAttribute(name, value);
                }else{//为上传文件，则调用item.write方法写文件
                    String value = item.getName() ;
                    int start = value.lastIndexOf(".");
                    String expFilename = value.substring(start+1);
                    String filename= UUID.randomUUID().toString()+"."+expFilename;
                    request.setAttribute(name, filename);
                    item.write(new File(path,filename));

                }
            }
            add(request, response);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
