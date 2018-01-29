package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;


public class LoginServ extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
        String redirect = "";
		if(req.getSession(false) != null){
			res.sendRedirect(Constants.baseUri);
		}
		LoginPage loginPage = new LoginPage();

		if(req.getParameter("error")!=null){
				loginPage.setError(req.getParameter("error"));
		}

        if(req.getParameter("redirect")!=null){
                redirect = req.getParameter("redirect");
        }

        if(!redirect.isEmpty()){
              loginPage.setRedirect(redirect);  
        } 

        res.setContentType("text/html");   
        PrintWriter pw = res.getWriter();
        pw.println(loginPage.getContent());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String userId = null; 

        try{
        	userId = UserModel.authenticateUser(email, password);
        	if (userId != null) {
            	request.getSession().setAttribute("userId", userId);

                if(request.getParameter("redirect")!=null){
                    response.sendRedirect(request.getParameter("redirect"));    
                }else {
            	   response.sendRedirect(Constants.baseUri);
                }
        	} else {
        		response.sendRedirect(Constants.baseUri+"/Login?error=Authentication failed");
        	}
        } catch (Exception e){
        	PrintWriter pw = response.getWriter();
        	pw.println("Login Exception:"+e.getMessage());
        }
        
    }
}
