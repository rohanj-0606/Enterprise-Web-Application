package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;

public class AllHotelsServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		int userId = -1;
		String search = "";
        String checkInDate = "";
        String checkOutDate = "";
        int numberOfGuests = -1;
        int rating = -1;
        int priceStart = -1;
        int priceEnd = -1;

		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		HotelPage hotelPage = new HotelPage(userId);

		if(request.getParameterMap().containsKey("search")){
			search = request.getParameter("search");
			hotelPage.setSearchFilter(search);
		}

		if(request.getParameterMap().containsKey("checkin")){
        	checkInDate = request.getParameter("checkin");
        	hotelPage.setCheckInDate(checkInDate);
    	}

    	if(request.getParameterMap().containsKey("checkout")){
        	checkOutDate = request.getParameter("checkout");
        	hotelPage.setCheckOutDate(checkOutDate);
    	}

        if(request.getParameterMap().containsKey("people")){
        	if(!request.getParameter("people").isEmpty()){
        		numberOfGuests = Integer.parseInt(request.getParameter("people"));
        		hotelPage.setGuestCount(numberOfGuests);
    		}
    	}

    	if(request.getParameterMap().containsKey("pricestart")){
    		if(!request.getParameter("pricestart").isEmpty()){
        		priceStart = Integer.parseInt(request.getParameter("pricestart"));
        		hotelPage.setPriceStart(priceStart);
        	}
    	}

    	if(request.getParameterMap().containsKey("priceend")){
    		if(!request.getParameter("priceend").isEmpty()){
        		priceEnd = Integer.parseInt(request.getParameter("priceend"));
        		hotelPage.setPriceEnd(priceEnd);
        	}
    	}

    	if(request.getParameterMap().containsKey("rating")){
    		if(!request.getParameter("rating").isEmpty()){
        		rating = Integer.parseInt(request.getParameter("rating"));
        		hotelPage.setRating(rating);
        	}
    	}
    	hotelPage.addSearch();
   
		response.setContentType("text/html");		
		PrintWriter pw=response.getWriter();
        pw.println(hotelPage.getContent());
       
	}
}
