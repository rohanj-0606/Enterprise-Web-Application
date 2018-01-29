package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;
import java.net.*;

public class BookingServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		String hotelId = new String(request.getParameter("hotelid"));
        String searchId = new String(request.getParameter("searchid"));
        String roomId = new String(request.getParameter("roomtype"));
        int userId = -1;

		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}	

		if(userId < 1){
			response.sendRedirect(Constants.baseUri+"/Login?error=Please Login before booking&redirect="+Constants.baseUri+"/Hotel"+URLEncoder.encode("?id="+hotelId+"&searchid="+searchId, "UTF-8"));
		}else{
			BookingSummaryPage summaryPage = new BookingSummaryPage(userId, Integer.parseInt(searchId), Integer.parseInt(hotelId), Integer.parseInt(roomId));

			if(request.getParameter("guest_name") != null && request.getParameter("guest_mobile") != null){
				if(!request.getParameter("guest_name").isEmpty() && !request.getParameter("guest_mobile").isEmpty()){
					summaryPage.book(request.getParameter("guest_name"), request.getParameter("guest_mobile"));
				}
			}
			PrintWriter pw=response.getWriter();
        	pw.println(summaryPage.getContent());
		}
	}
}
