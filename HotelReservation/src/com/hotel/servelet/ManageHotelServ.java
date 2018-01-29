package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;
import com.hotel.libraries.*;
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

public class ManageHotelServ extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		int userId = -1;
        String operation = request.getParameter("operation");
        String error = "";
        if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		if(operation.equals("add")){
				HotelAddPage ho = new HotelAddPage(userId);
				response.setContentType("text/html");
				PrintWriter pw=response.getWriter();
        		pw.println(ho.getContent());		
		}else if(operation.equals("update")){
				String hotelId = "";

				if(request.getParameterMap().containsKey("id")){
					hotelId = request.getParameter("id");	
				}

				if(hotelId.isEmpty()){
					HotelView ho = new HotelView(userId);
					ho.setOperation("update");
					response.setContentType("text/html");
					PrintWriter pw=response.getWriter();
			        pw.println(ho.getContent());
		        }else{
		        	SingleHotelView ho = new SingleHotelView(userId, hotelId);
					response.setContentType("text/html");
					PrintWriter pw=response.getWriter();
			        pw.println(ho.getContent());
		        }
		}else if(operation.equals("delete")){
				HotelView ho = new HotelView(userId);
				String hotelId = "";
				ho.setOperation("delete");
				
				if(request.getParameterMap().containsKey("id")){
					hotelId = request.getParameter("id");	
				}

				if(hotelId.isEmpty()){
					response.setContentType("text/html");
					PrintWriter pw=response.getWriter();
			        pw.println(ho.getContent());
		        }else{
		        	doPost(request, response);
		        }
		}else if(operation.equals("view")){
				HotelView ho = new HotelView(userId);
				error = request.getParameter("error");
				ho.setOperation("update");
				
				if(error != null && !error.isEmpty() && error!="null"){
					ho.setError(error);
				}
				response.setContentType("text/html");
				PrintWriter pw=response.getWriter();
		       	pw.println(ho.getContent());
		}

		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		int userId = -1;
		String operation = request.getParameter("operation");
		String error = "";

		if(request.getSession(false) != null){
			userId = Integer.parseInt(request.getSession(false).getAttribute("userId").toString());
		}

		
		if(operation.equals("add")){
			HotelLib htl = new HotelLib(request.getParameter("name"), request.getParameter("description"), request.getParameter("overview"), Double.parseDouble(request.getParameter("price")), request.getParameter("address"), request.getParameter("area"), request.getParameter("city"), request.getParameter("country"), request.getParameter("checkin"), request.getParameter("checkout"));

			HotelModel.addHotel(htl);

			response.setContentType("text/html;charset=UTF-8");

			// Create path components to save the file
			String path = request.getServletContext().getRealPath("");
			Part filePart = request.getPart("imgfile");
			String fileName = htl.getDefaultImage();

			OutputStream out = null;
			InputStream filecontent = null;

			try {
				out = new FileOutputStream(new File(path + File.separator + fileName));
				filecontent = filePart.getInputStream();

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Hotel added succefully. Please update price and carausel image"+"#"+htl.getId());
			} catch (FileNotFoundException fne) {
				response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error="+fne.getMessage());

			} finally {
				if (out != null) {
					out.close();
				}
				if (filecontent != null) {
					filecontent.close();
				}
			}
		}if(operation.equals("update")){
			HotelLib htl = new HotelLib(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("description"), request.getParameter("overview"), Double.parseDouble(request.getParameter("price")), request.getParameter("address"), request.getParameter("area"), request.getParameter("city"), request.getParameter("country"), request.getParameter("checkin"), request.getParameter("checkout"));

			HotelModel.updateHotel(htl);
			response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Hotel updated succefully. Please update price and carausel image"+"#"+htl.getId());
		
		}else if(operation.equals("addImage")){
			String hotelId = request.getParameter("id");
			
			String path = request.getServletContext().getRealPath("");
			Part filePart = request.getPart("imgfile");
			String fileName = "img"+File.separator+hotelId+UUID.randomUUID().toString().replace("-", "")+"-htl.jpg";
			response.setContentType("text/html;charset=UTF-8");

			OutputStream out = null;
			InputStream filecontent = null;

			try {
				out = new FileOutputStream(new File(path + File.separator + fileName));
				filecontent = filePart.getInputStream();

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				HotelModel.addHotelImage(hotelId, fileName);
				response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Image added succesfully"+"#"+hotelId);
			} catch (FileNotFoundException fne) {
				response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error="+fne.getMessage());

			} finally {
				if (out != null) {
					out.close();
				}
				if (filecontent != null) {
					filecontent.close();
				}
			}
		}else if(operation.equals("addPrice")){
			String hotelId = request.getParameter("id");
			String price = request.getParameter("price");
			String name = request.getParameter("name");

			HotelModel.addHotelPrice(hotelId, price, name);
			response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Price added succesfully"+"#"+hotelId);
			
		}else if(operation.equals("deleteImage")){
			String imgSrc = request.getParameter("imgsrc");
			int hotelId = Integer.parseInt(request.getParameter("id"));
			
			HotelModel.deleteHotelImage(hotelId, imgSrc);
			response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Image deleted succesfully"+"#"+hotelId);	
		}else if(operation.equals("deletePrice")){
			String imageId = request.getParameter("id");
			
			HotelModel.deleteHotelPrice(imageId);
			response.sendRedirect(Constants.baseUri+"/ManageHotel?operation=view&error=Price deleted succesfully");
			
		}

	}

}
