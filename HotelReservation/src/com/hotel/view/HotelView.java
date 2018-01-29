package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import java.io.*;
import com.hotel.libraries.*;
import com.hotel.model.*;

public class HotelView{
	private String error;
	private int userId;
	private String operation;

	public HotelView(int usrId){

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}

		error = "";

	}

	public void setOperation(String op){
		operation = op;
	}


	private String getBody(){
		String content = "<div class='container'>\n"+
		"	<div class='col-sm-2 bg-info' style='border:1px solid #cecece;'>\n"+
		"	<div class='col-sm-12'>\n"+
		"	{admin_panel}\n"+
		"	</div></div>\n"+
		"	<div class='col-sm-10 bg-dark text-white'><div class='col-sm-12'>{error}</div>\n"+
		"	<div class='col-sm-12'>\n"+
		"		<div class='well well-lg'>\n"+
		"                       	<h1>Hotel List</h1>    \n"+
		"                </div>\n"+
		"		<hr />\n"+
		"		<div class='col-sm-12'>"+
		"			{inner_page}	\n"+
		"		</div>"+
		"	</div>\n"+
		"       </div>\n"+
		"    </div>\n";

		content = content.replace("{admin_panel}", Constants.getAdminPanel(userId));
		content = content.replace("{inner_page}", getHotelList());

		if(!error.isEmpty()){
			content = content.replace("{error}", error);
		}else{
			content = content.replace("{error}", "");
		}

		return content;
	}

	private String getHotelList(){
		String content = "";
		String eachDiv = "<div class='row well well-sm' id={hid}>\n"+
			" <a href='ManageHotel?operation={operation}&id={id}'>"+         
			"<div class='col-sm-12'>\n"+
			"    <div class='col-sm-4'>\n"+
			"            <img src='{image_src}' class='img-thumbnail' />\n"+
			"    </div>\n"+
			"    <div class='col-sm-8'>\n"+
			"            <div class='col-sm-12'>\n"+
			"                    <h3 class='text-danger'> {name} </h3>\n"+
			"            </div>\n"+
			"            <div class='col-sm-12'>\n"+
			"                    <blockquote><p class='text-justify'> {description} </p></blockquote>\n"+
			"            </div>\n"+
			"            <div class='col-sm-12'>\n"+
			"                    <div class='col-md-4 col-md-push-8'>\n"+
			"                            <p>Rating: {rating}</p>\n"+
			"                    </div>\n"+
			"                    <div class='col-md-8 col-md-pull-4'>\n"+
			"                            <p>Price: {price}</p>\n"+
			"                    </div>  \n"+
			"            </div>\n"+
			"    </div>\n"+
			"</div>\n"+
			"         </a>\n"+
			"</div>\n";

		try{
			ArrayList<HotelLib> allHotels = HotelModel.getAllHotel();

			if(allHotels.isEmpty()){
				setError("No hotels available");
			}

			for(HotelLib htl : allHotels){
					String tempVal = null;
	    			tempVal = eachDiv.replace("{hid}", String.valueOf(htl.getId())).replace("{id}", String.valueOf(htl.getId())).replace("{image_src}", htl.getImage()).replace("{name}", htl.getName()).replace("{description}", htl.getDescription());
	    			tempVal = tempVal.replace("{rating}", String.valueOf(htl.getRating())).replace("{price}", String.valueOf(htl.getPrice()));  
	 		 		tempVal = tempVal.replace("{operation}", operation);
	 		 		if(!tempVal.isEmpty()){
	 		 			content += tempVal;
	 		 		}
	 		}
 		}catch (Exception e){
 			setError(e.getMessage());
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

