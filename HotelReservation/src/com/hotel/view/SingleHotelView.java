package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import java.io.*;
import com.hotel.libraries.*;
import com.hotel.model.*;

public class SingleHotelView{
	private String error;
	private int userId;
	private String operation;
	private String hotelId;

	public SingleHotelView(int usrId, String hotelId){

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}
		this.hotelId = hotelId;

		error = "";

	}


	private String getBody() throws IOException{
		String content = "<div class='container'>\n"+
		"	<div class='col-sm-2 bg-info' style='border:1px solid #cecece;'>\n"+
		"	<div class='col-sm-12'>\n"+
		"	{admin_panel}\n"+
		"	</div></div>\n"+
		"	<div class='col-sm-10 bg-dark text-white'><div class='col-sm-12'>{error}</div>\n"+
		"	<div class='col-sm-12'>\n"+
		"		<div class='well well-lg'>\n"+
		"                       	<h1>Hotel Info</h1>    \n"+
		"                </div>\n"+
		"		<hr />\n"+
		"		<div class='col-sm-12'>"+
		"			{hotel_images}	\n"+
		"		</div><hr />"+
		"		<div class='col-sm-12'>"+
		"			{hotel_price}	\n"+
		"		</div> <hr />"+
		"		<div class='col-sm-12'>"+
		"			{additional_info}	\n"+
		"		</div>"+
		"	</div>\n"+
		"       </div>\n"+
		"    </div>\n";

		content = content.replace("{admin_panel}", Constants.getAdminPanel(userId));
		content = content.replace("{hotel_images}", getImageForm());
		content = content.replace("{hotel_price}", getPriceForm());	
		content = content.replace("{additional_info}", getHotelForm());		

		if(!error.isEmpty()){
			content = content.replace("{error}", error);
		}else{
			content = content.replace("{error}", "");
		}

		return content;
	}


	private String getHotelForm() throws IOException{
			HotelLib htl = HotelModel.getHotel(Integer.parseInt(hotelId));
			String content =
			"	<div class='col-sm-12'>\n"+
			"<form class='form-horizontal' action='ManageHotel' method='POST'  enctype='multipart/form-data'>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"  				<input type='hidden' id='id' name='id' value='"+hotelId+"'>"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <input type='hidden' class='form-control' id='operation' name='operation' value='update'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"	  <label for='comment'>Hotel Name:</label>\n"+
			"          <input type='text' class='form-control' name='name' id='name' value ='"+htl.getName()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Overview Summary:</label>\n"+
			"          <input type='text' class='form-control' name='description' id='description' value ='"+htl.getOverview()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"                <label for='comment'>Overview:</label>\n"+
			"                <textarea class='form-control' rows='10' name='overview' id='overview'>"+htl.getDescription()+"</textarea>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Display Price:</label>\n"+
			"          <input type='number' class='form-control' name='price' id='price'  value ='"+htl.getPrice()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Address:</label>\n"+
			"          <input type='text' class='form-control' name='address' id='address'  value ='"+htl.getAddress()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Area:</label>\n"+
			"          <input type='text' class='form-control' name='area' id='area'  value ='"+htl.getArea()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>City:</label>\n"+
			"          <input type='text' class='form-control' name='city' id='city'  value ='"+htl.getCity()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Country:</label>\n"+
			"          <input type='text' class='form-control' name='country' id='country'  value ='"+htl.getCountry()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Check In Time:</label>\n"+
			"          <input type='text' class='form-control' name='checkin' id='checkin'  value ='"+htl.getCheckInTime()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <label for='comment'>Check Out Time:</label>\n"+
			"          <input type='text' class='form-control' name='checkout' id='checkout' value ='"+htl.getCheckOutTime()+"'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"		 <button type='submit' class='btn btn-primary btn-block'>Update Hotel</button>\n"+
			"      	</div>\n"+
			"    </div>\n"+
			"  </form>\n"+
			"    </div></div>\n";

		return content;
	}

	private String getImageForm() throws IOException{
		String content = "<div>{existing_image}</div><div><form class='form-horizontal' action='ManageHotel' method='post' enctype='multipart/form-data'>"+
			"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                                "<input type='hidden' id='id' name='id' value='"+hotelId+"'>"+
        		"</div>"+
    		"</div>"+
    		"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                                "<input type='hidden' id='operation' name='operation' value='addImage'>"+
        		"</div>"+
    		"</div>"+
			"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                        "<label class='custom-file'>"+
                                "<input type='file' id='imgfile' name='imgfile' class='custom-file-input'>"+
                                "<span class='custom-file-control'></span>"+
                        "</label>"+
        		"</div>"+
    		"</div>"+
    		"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                 	"<button type='submit' class='btn btn-primary btn-block'>Add Image</button>"+
    			"</div>"+
    		"</div>"+
		"</form></div><hr />";

		content = content.replace("{existing_image}", getExisingImage());

		return content;

	}

	private String getPriceForm() throws IOException{
		String content = "<div><br /><br /><h3>Price Management</h3></div><div>{existing_price}</div><div><h3>Add New Price</h3></div><div><form class='form-horizontal' action='ManageHotel' method='post' >"+
			"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                                "<input type='hidden' id='id' name='id' value='"+hotelId+"'>"+
        		"</div>"+
    		"</div>"+
    		"<div class='form-group'>"+
        		"<div class='col-sm-6'>"+
                                "<input type='hidden' id='operation' name='operation' value='addPrice'>"+
        		"</div>"+
    		"</div>"+
			"<div class='form-group'>"+
        		"<div class='col-sm-4'>"+
                                "<input type='text' id='name' name='name' placeholder='Room Name'>"+
        		"</div>"+
    		"</div>"+
    		"<div class='form-group'>"+
        		"<div class='col-sm-4'>"+
                                "<input type='number' id='price' name='price' placeholder='Room Price'>"+
        		"</div>"+
    		"</div>"+
    		"<div class='form-group'>"+
        		"<div class='col-sm-4'>"+
                 	"<button type='submit' class='btn btn-primary btn-block'>Add Price</button>"+
    			"</div>"+
    		"</div>"+
		"</form></div></hr>";

		content = content.replace("{existing_price}", getExisingPrice());

		return content;		
	}


	private String getExisingPrice() throws IOException{ 
		ArrayList<HotelPrice> prices= HotelModel.getHotel(Integer.parseInt(hotelId)).getAllPrice();
		String content = "<div class='row'>";

		if(prices.isEmpty()){
			return content;
		}

		for (int i = 0; i < prices.size(); i++) {
			content += "<div class='col-sm-12'><div class='col-sm-4'>"; 
         		content += "<form class='form-horizontal' action='ManageHotel' method='post'>"+
         					"<div class='form-group'>"+
        						"<div class='col-sm-4'>"+
                                	"<input type='hidden'  name='id' value='"+prices.get(i).getId()+"'>"+
 	                            "</div></div>"+
 	                            "<div class='form-group'>"+
        							"<div class='col-sm-4'>"+
 	                               "<input type='hidden' id='operation' name='operation' value='deletePrice'>"+
 	                            "</div></div>"+
 	                            "<div class='form-group'>"+
        							"<div class='col-sm-4'>"+
 	                               	"<input type='text' id='price' name='price'  value='"+prices.get(i).getPrice()+"' readonly>"+
 	                            "</div></div>"+
 	                               "<div class='form-group'>"+
        							"<div class='col-sm-4'>"+
 	                               		"<input type='text' id='name' name='name'  value='"+prices.get(i).getName()+"' readonly>"+
                 				"</div></div>"+	
                 				"<div class='form-group'>"+
        							"<div class='col-sm-12'>"+
                 						"<button type='submit' class='btn btn-primary btn-block'>Delete Price</button>"+
    							"</div></div>"+
    					"</form></div></div>";
		
		}

		content += "</div>";
		return content;
	}


	private String getExisingImage() throws IOException{ 
		ArrayList<String> images= HotelModel.getHotel(Integer.parseInt(hotelId)).getAllImage();
		String content = "<div class='row'>";

		if(images.isEmpty()){
			return content;
		}

		for (int i = 0; i < images.size(); i++) {
			content += "<div class='col-md-4'> <div class='thumbnail'> <img src='"+images.get(i)+"' style='width:100%'> <div class='caption'>";
         	content += "<form action='ManageHotel' method='post'>"+
                                	"<input type='hidden'  name='imgsrc' value='"+images.get(i)+"'>"+
                                	"<input type='hidden'  name='id' value='"+hotelId+"'>"+
 	                               "<input type='hidden' id='operation' name='operation' value='deleteImage'>"+
                 					"<button type='submit' class='btn btn-primary btn-block'>Delete Image</button>"+
    					"</form></div></div></div>";
		}
		content += "</div>";
		return content;
	}

	
	public void setError(String err){
		error = "<div class='alert alert-danger'>"+err+"</div>";
	}

	public void operate(){

	}

	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+getBody()+Constants.pageFooter();
		
		return content;
	}

	
}

