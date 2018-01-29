package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import java.io.*;
import com.hotel.libraries.*;
import com.hotel.model.*;

public class HotelAddPage{
	private String error;
	private int userId;
	private String operation;

	public HotelAddPage(int usrId){

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}
		operation = "add";
		error = "";

	}



	private String getBody(){
		String content = "<div class='container'>\n"+
		"	<div class='col-sm-2 bg-info' style='border:1px solid #cecece;'>\n"+
		"	<div class='col-sm-12'>\n"+
		"	{admin_panel}\n"+
		"	</div></div>\n"+
		"	<div class='col-sm-10 bg-dark text-white'><div class='col-sm-12'>{error}</div>\n"+
		"<form class='form-horizontal' action='ManageHotel' method='POST'  enctype='multipart/form-data'>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"			<label>Hotel Main Image</label><br><label class='custom-file'>\n"+
		"  				<input type='file' id='imgfile' name='imgfile' class='custom-file-input'>\n"+
		"  				<span class='custom-file-control'></span>\n"+
		"			</label>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <input type='hidden' class='form-control' id='operation' name='operation' value='add'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"	  <label for='comment'>Hotel Name:</label>\n"+
		"          <input type='text' class='form-control' name='name' id='name' placeholder='Hotel Name'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Overview Summary:</label>\n"+
		"          <input type='text' class='form-control' name='description' id='description' placeholder='summary'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"                <label for='comment'>Overview:</label>\n"+
		"                <textarea class='form-control' rows='10' name='overview' id='overview'></textarea>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Display Price:</label>\n"+
		"          <input type='number' class='form-control' name='price' id='price' placeholder='price'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Address:</label>\n"+
		"          <input type='text' class='form-control' name='address' id='address' placeholder='address'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Area:</label>\n"+
		"          <input type='text' class='form-control' name='area' id='area' placeholder='area'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>City:</label>\n"+
		"          <input type='text' class='form-control' name='city' id='city' placeholder='city'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Country:</label>\n"+
		"          <input type='text' class='form-control' name='country' id='country' placeholder='country'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Check In Time:</label>\n"+
		"          <input type='text' class='form-control' name='checkin' id='checkin' placeholder='MM:SS'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"          <label for='comment'>Check Out Time:</label>\n"+
		"          <input type='text' class='form-control' name='checkout' id='checkout' placeholder='MM:SS'>\n"+
		"        </div>\n"+
		"    </div>\n"+
		"    <div class='form-group'>\n"+
		"        <div class='col-sm-6'>\n"+
		"		 <button type='submit' class='btn btn-primary btn-block'>Add Hotel</button>\n"+
		"      	</div>\n"+
		"    </div>\n"+
		"  </form>\n"+
		"    </div></div>\n";

		content = content.replace("{admin_panel}", Constants.getAdminPanel(userId));

		if(!error.isEmpty()){
			content = content.replace("{error}", error);
		}else{
			content = content.replace("{error}", "");
		}

		return content;
	}

	public void setError(String err){
		error = "<div class='alert alert-danger'>"+err+"</div>";
	}

	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+getBody()+Constants.pageFooter();
		
		return content;
	}
}

