package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;
import java.sql.*;

public class HotelServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		int userId = -1;
        String hotelId = null;
        String searchId = "";
        String review = "";
        SingleHotelPage hotelPage = null;
        
		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		hotelPage = new SingleHotelPage(userId);
		if(request.getParameterMap().containsKey("writereview")){
			review = request.getParameter("writereview");
			hotelPage = new SingleHotelPage(userId, review);		
		}

		if(request.getParameterMap().containsKey("id")){
			hotelId = request.getParameter("id");
            if(!hotelId.isEmpty()){
            	try{
			 		hotelPage.setHotelId(Integer.parseInt(hotelId));
		    	} catch (SQLException e){
		    		PrintWriter pw=response.getWriter();
       				pw.println(e.getMessage());
       				return;
		    	}
		    }
        }

        if(request.getParameterMap().containsKey("searchid")){
			searchId = request.getParameter("searchid");
            if(!searchId.isEmpty()){
			 		hotelPage.setSearchId(Integer.parseInt(searchId));
		    }
        }
   
		response.setContentType("text/html");		
		PrintWriter pw=response.getWriter();
        pw.println(hotelPage.getContent());
       
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doGet(request, response);
	}
}
