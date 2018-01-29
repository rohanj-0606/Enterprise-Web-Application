package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;


public class ReviewServ extends HttpServlet{


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        int userId = -1;
        String review = request.getParameter("review");
        String rating = request.getParameter("rating");
        String hotelId = request.getParameter("id");
        String searchId = request.getParameter("searchid");

        MongoConnection.addMongoDocument(hotelId, rating, review);
   
        RequestDispatcher rd=request.getRequestDispatcher("Hotel"); 
        response.sendRedirect(Constants.baseUri+"/Hotel?id="+hotelId+"&searchid="+searchId); 
        
    }
}
