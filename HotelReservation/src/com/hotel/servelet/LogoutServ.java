package com.hotel.servelet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.hotel.config.*;
import com.hotel.view.*;
import com.hotel.model.*;


public class LogoutServ extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		req.getSession(false).removeAttribute("userId");
        req.getSession(false).invalidate();
        res.sendRedirect(Constants.baseUri);
	}

}
