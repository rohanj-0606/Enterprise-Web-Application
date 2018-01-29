package com.hotel.view;
import com.hotel.config.*;
import java.io.*;

public class RequestPage{
	private int userId;
	private String error;
	
	public RequestPage(int id){
		userId = id;	
	}

	public void setError(String str){
		error = "<div class='alert alert-danger'><strong>Error!</strong>"+str+"</div>";
	}

	private String generateBody(){
			String content = "<div class='container'><h2 class='text-center'>Search hotels</h2>\n";

			if(error!=null){
				content+=error;
			}

			content+="<form class='form-horizontal' action='' method='post'>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-12'>\n"+
			"        <input type='text' class='form-control' id='search' name='search' placeholder='search by hotel name, location or city.....'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"      <div class='col-sm-6'>          \n"+
			"                <div class='input-group date datepicker' >\n"+
			"                    <input type='text' class='form-control' id='checkin' name='checkin' placeholder='Check In'/>\n"+
			"                    <span class='input-group-addon'>\n"+
			"                        <span class='glyphicon glyphicon-calendar'></span>\n"+
			"                    </span>\n"+
			"                </div>\n"+
			"      </div>\n"+
			"      <div class='col-sm-6'>                \n"+
			"                <div class='input-group date datepicker'  >\n"+
			"                    <input type='text' class='form-control' id='checkout' name='checkout' placeholder='Check Out'/>\n"+
			"                    <span class='input-group-addon'>\n"+
			"                        <span class='glyphicon glyphicon-calendar'></span>\n"+
			"                    </span>\n"+
			"                </div>\n"+
			"      </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>        \n"+
			"        <div class='col-sm-6'>                \n"+
			"                <input type='number' class='form-control' id='people' name='people' placeholder='Number of rooms'>\n"+
			"        </div>\n"+
			"        <div class='col-sm-6'>\n"+
			"                 <button type='submit' class='btn btn-primary btn-block'>Find Hotel</button>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"  </form>\n"+
			"</div> \n";
			error = null;
			return content;
	}

	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+Constants.defaultImageCarausel()+generateBody()+Constants.pageFooter();
		return content;
	}
}
