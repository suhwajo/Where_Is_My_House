package com.ssafy.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/page/*"})
public class PageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/index.html";

        String pathInfo = req.getPathInfo();

        String queryString = req.getQueryString();

        if(pathInfo != null && !pathInfo.equals("/")) {
            String root = pathInfo.split("/")[1];

            switch (root) {
                case "user":
                    path = user(pathInfo);
                    break;
                case "home":
                    path = "/home/find_homes.html";
                    break;
                case "area":
                    path = "/area/focus_area.html";
                    break;
                case "notice":
                    path = notice(pathInfo);
                    break;
                case "board":
                    path = board(pathInfo);
                    break;
                case "article":
                    path = article(pathInfo);
                    break;
                case "admin":
                    path = admin(pathInfo);
                    break;
            }

        }
        
        if(queryString != null)
        	path = path + "?" + queryString;
        

        if(path.startsWith("redirect:"))
            redirect(req, resp, path.split(":")[1]);
        else {
            path += "?" + queryString;
            forward(req, resp, path);
        }
    }
    private String user(String pathInfo) {
        String path = "login_page.html";

        if(pathInfo.split("/").length > 2) {
            String act = pathInfo.split("/")[2];

            switch (act) {
                case "login":
                    path = "login_page.html";
                    break;
                case "join":
                    path = "join.html";
                    break;
                case "findPw":
                    path = "findPw.html";
                    break;
                case "userInfo":
                    path = "userInfo.html";
                    break;
                case "list":
                    path = "userList.html";
                    break;
            }
        }

        return "/user/" + path;
    }

    private String notice(String pathInfo) {
        String path = "notice_list.html";

        if(pathInfo.split("/").length > 2) {
            String act = pathInfo.split("/")[2];

            switch (act) {
                case "list":
                    path = "notice_list.html";
                    break;
                case "detail":
                    path = "notice_detail.html";
                    break;
                case "write":
                    path = "notice_write.html";
                    break;
                case "modify":
                    path = "notice_modify.html";
                    break;
            }
        }

        return "/notice/" + path;
    }

    private String board(String pathInfo) {
        String path = "/";

        return path;
    }

    private String article(String pathInfo) {
        String path = "/";

        return path;
    }

    private String admin(String pathInfo) {
        String path = "/";

        return path;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
