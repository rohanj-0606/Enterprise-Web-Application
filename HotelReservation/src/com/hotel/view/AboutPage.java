package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import com.hotel.libraries.*;
import com.hotel.model.*;
import java.sql.*;
import java.io.*;

public class AboutPage{
	private int userId;

	public AboutPage(int usrId) throws IOException{
		userId = usrId;
	}

	private String getBody(){
		String content = "<div class='container'>\n"+
                "        <div class='container'>\n"+
                "        <div class='col-sm-12'>\n"+
                "                <h3 class='text-uppercase'><em>About Us</em></h3></div><div class='col-sm-12'>\n"+
                "                <blockquote>If you are not afraid to pursue your dreams, live on the edge, experiment, discover and learn afresh. If you have a passion for the offbeat, if you are seeking adventure, if novelty excites you : Get ready for Hotel Booking.\n"+
                " </blockquote>\n"+
                "        </div>\n"+
                "</div>\n"+
                "</ hr>\n"+
                "</div>\n";

		return content;
	}


	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+getBody()+Constants.pageFooter();
		return content;
	}
}
