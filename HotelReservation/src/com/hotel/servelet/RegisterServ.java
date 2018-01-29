package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;

public class RegisterServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		 int userId = -1;

		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		RegisterPage rp = new RegisterPage(userId);

		if(request.getParameter("error")!=null){
				rp.setError(request.getParameter("error"));
		}

		response.setContentType("text/html");

        PrintWriter pw=response.getWriter();
        pw.println(rp.getContent());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		String email = new String(request.getParameter("email"));
        String mobile = new String(request.getParameter("mobile"));
        String password = new String(request.getParameter("password"));
        int role = 3;
        String error = "";

    	if(email.isEmpty() || mobile.isEmpty() || password.isEmpty()){
    		error = "Please fill all fields";
    	}

    	if(error.isEmpty()){
    		request.setAttribute("role", "3");
    		
    		try{
				int userId = UserModel.addUser(email, password, role, mobile);
				if(userId < 0){
					error = "Unable to add user";	
				}
			}catch (SQLException e){
				throw new IOException(e.getMessage());
			}
		}

		if(!error.isEmpty()){
			response.sendRedirect(Constants.baseUri+"/Register?error="+error);
		}else{
			response.sendRedirect(Constants.baseUri+"/Login?error=Please Login with New Account");
		}
	}
}
