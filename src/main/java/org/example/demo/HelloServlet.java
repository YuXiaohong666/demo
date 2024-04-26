package org.example.demo;

import com.sun.istack.internal.Nullable;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        先判断上传的数据是否是多段的数据
        if (ServletFileUpload.isMultipartContent(req)) {
//            创建FileItemFactory工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
//            创建用于解析上传数据的工具类ServletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
//            调用用解析方法
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                list.forEach(item -> {
                    if (item.isFormField()) {
//                        普通表单项
                        System.out.println("表单项的name属性值是：" + item.getFieldName());
                        try {
                            System.out.println("上传的表单项名是：" + item.getString("GBK"));
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
//                        上传的文件
                        System.out.println("表单项的name属性值是：" + item.getFieldName());
                        servletFileUpload.setHeaderEncoding("GBK");
                        System.out.println("上传的文件名是：" + item.getName());
                        try {
                            item.write(new File("d:\\" + item.getName()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (FileUploadException e) {


            }
        }
    }
}