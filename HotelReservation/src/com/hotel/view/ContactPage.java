package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import com.hotel.libraries.*;
import com.hotel.model.*;
import java.sql.*;
import java.io.*;

public class ContactPage{
	private int userId;

	public ContactPage(int usrId) throws IOException{
		userId = usrId;
	}

	private String getBody(){
		String content = "<div class='container'>\n"+
                "        <div class='container'>\n"+
                "        <div class='col-sm-12'>\n"+
                "                <h3 class='text-uppercase'><em>Telephone No</em></h3></div><div class='col-sm-12'><p>\n"+
                Constants.telephoneNo+
                " </p>\n"+
                "        </div>\n"+
                "<div class='col-sm-12'>\n"+
                "                <h3 class='text-uppercase'><em>Address</em></h3></div><div class='col-sm-12'><p>\n"+
                Constants.address+
                " </p>\n"+
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
