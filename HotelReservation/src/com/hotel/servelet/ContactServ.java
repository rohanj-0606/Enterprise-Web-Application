package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;


public class ContactServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
        int userId = -1;

        if(request.getSession(false) != null){
            userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
        }

        PrintWriter pw=response.getWriter();
        pw.println(new ContactPage(userId).getContent());
	}
}