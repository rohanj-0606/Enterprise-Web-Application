package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;

public class HomeServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		 int userId = -1;

		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		HomePage hp = new HomePage(userId);
		response.setContentType("text/html");

		if(request.getParameter("error")!=null){
				hp.setError(request.getParameter("error"));
		}

        PrintWriter pw=response.getWriter();
        pw.println(hp.getContent());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		String search = new String(request.getParameter("search"));
        String checkInDate = new String(request.getParameter("checkin"));
        String checkOutDate = new String(request.getParameter("checkout"));
        String numberOfGuests = new String(request.getParameter("people"));
        String error = "";


    	if(search.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty() || numberOfGuests.isEmpty()){
    		error = "Please fill all fields";
    	}else if(checkInDate.compareTo(checkOutDate) > 0){
    		error = "Check in date can not be greater than check out date";
    	}

    	if(error.isEmpty()){
			RequestDispatcher rd = request.getRequestDispatcher("Hotels");
			rd.forward(request, response);
		}else{
			response.sendRedirect(Constants.baseUri+"?error="+error);
		}
	}
}
