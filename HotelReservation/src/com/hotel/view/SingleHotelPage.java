package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import com.hotel.libraries.*;
import com.hotel.model.*;
import java.sql.*;
import java.io.*;
import com.mongodb.DBObject;

public class SingleHotelPage{
	private String error;
	private int userId;
    private int hotelId;
    private int searchId;
    private int writeReview;
    private HotelLib hotel;


	public SingleHotelPage(int usrId){
		error = "";
		userId = 0;
		hotelId = -1;
		searchId = -1;
		hotel = null;
		writeReview = 0;
		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}

	}

	public SingleHotelPage(int usrId, int write){
		error = "";
		userId = 0;
		hotelId = -1;
		searchId = -1;
		hotel = null;
		writeReview = write;

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}

	}

	public SingleHotelPage(int usrId, String write){
		error = "";
		userId = 0;
		hotelId = -1;
		searchId = -1;
		hotel = null;
		writeReview = Integer.parseInt(write);

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}

	}

	public SingleHotelPage(){
		error = "";
		userId = 0;
		hotelId = -1;
		searchId = -1;
		hotel = null;
		writeReview = 0;
	}

	public void setSearchId(int srchId){
		searchId = srchId;
	}

	public void setHotelId(int id) throws IOException, SQLException{
		if(id > 0){
			hotelId = id;
			hotel = HotelModel.getHotel(id);
		}
	}

	private String getBody() throws IOException{
		String content = "";

		if(hotelId < 1){
			addError("Nothing to display");
		}


		if(!error.isEmpty()){
			return error;
		}

		content = " <div class='container'>\n"+
			"	<div class='col-sm-12'>\n"+
			"               <h2>{name}</h2>    \n"+
			"		<hr />\n"+
			"			<div class='carousel slide' data-ride='carousel'>\n"+
			"\n"+
			"    				<div class='carousel-inner' style='width: 100%; max-height: 250px !important;'>\n"+
			"					{image_carausel}\n"+
			"    				</div>\n"+
			"   			</div>\n"+
			"		<hr />\n"+
			"		<div>\n"+
			"		    <blockquote>\n"+
			"                        <p>{overview}</p>\n"+
			"         	    </blockquote>\n"+
			"        	</div>\n"+
			"		<div>\n"+
			"           <hr />"+
			"			<h2>Book Room</h2>"+
			"			<form class='form-horizontal' action='Booking'>\n"+
			"				 <div class='form-group'>\n"+
			"				        <div class='col-sm-12'>\n"+
			"        					<input type='hidden' class='form-control' id='hotelid' name='hotelid' value='{hotelid}' readonly />\n"+
			"        				</div>\n"+
			"    				 </div>\n"+
			"				<div class='form-group'>\n"+
			"                                        <div class='col-sm-12'>\n"+
			"                                                <input type='hidden' class='form-control' id='searchid' name='searchid' value='{searchid}' readonly />\n"+
			"                                        </div>\n"+
			"                                 </div>\n"+
			"				<div class='form-group'>\n"+
			"					<div class='col-sm-12'>\n"+
			"						{room_info}\n"+
			"					</div>\n"+
			"				</div>\n"+
			"				<div class='form-group'>\n"+
			"					<div class='col-sm-4'>\n"+
			"						<button type='submit' class='btn btn-primary btn-block'>Book Now</button>\n"+
			"					</div>\n"+
			"				</div>\n"+
			"			</form>	\n"+
			"       </div>\n"+
			"		<div>\n"+
			"			<hr /><h2>Review</h2>{review_section}\n<hr />"+
			"		</div>\n"+
			"	</div>\n"+
			"    </div>\n";


		content = content.replace("{name}", this.hotel.getName());
		content = content.replace("{image_carausel}", getCarauselSection());
		content = content.replace("{overview}", this.hotel.getOverview());
		content = content.replace("{hotelid}", String.valueOf(this.hotel.getId()));
		content = content.replace("{searchid}", String.valueOf(this.searchId));
		content = content.replace("{room_info}", this.getRoomInfoSection());
		content = content.replace("{review_section}", getReviewSection());
		
		if(!error.isEmpty()){
			return error;
		}

		return content;
	}

	private String getRoomInfoSection(){
		String activeDiv = "<div class='radio'><label><input type='radio' name='roomtype' value='{priceid}' checked /> <span><strong>{room_name}</strong></span>  for <span><em>{price}</em></span></label></div>";
		String nonActiveDiv = "<div class='radio'><label><input type='radio' name='roomtype' value='{priceid}' /> <span><strong>{room_name}</strong></span>  for <span><em>{price}</em></span></label></div>";	
		String content = "";
		ArrayList<HotelPrice> priceList = this.hotel.getAllPrice();

		if(priceList.isEmpty()){
			addError("Room info missing");
			return content;
		}

		content += activeDiv.replace("{priceid}", String.valueOf(priceList.get(0).getId())).replace("{room_name}", priceList.get(0).getName()).replace("{price}", String.valueOf(priceList.get(0).getPrice()));
	
		for (int i = 1; i < priceList.size(); i++) {
			content += nonActiveDiv.replace("{priceid}", String.valueOf(priceList.get(i).getId())).replace("{room_name}", priceList.get(i).getName()).replace("{price}", String.valueOf(priceList.get(i).getPrice()));
		}

		return content;
	}

	private String getReviewSection() throws IOException{
		String content = "";
		String writeForm = "<div id='review_section'>"+
			"<form class='form-horizontal' action='Review' method='POST'>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"        <input type='hidden' class='form-control' id='id' name='id' value='{id}'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"        <input type='hidden' class='form-control' id='id' name='searchid' value='{searchid}'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"   <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"        <input type='hidden' class='form-control' id='givereview' name='givereview' value='1'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"     <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <input type='text' class='form-control' id='review' name='review' placeholder='Write Review'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <input type='number' class='form-control' id='ratingreview' name='rating' placeholder='Rating'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"     <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"                 <button type='submit' class='btn btn-primary btn-block'>Submit</button>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"  </form>\n"+
			"</div>";

			writeForm = writeForm.replace("{id}", String.valueOf(this.hotel.getId())).replace("{searchid}", String.valueOf(searchId));

		String eachDiv = "<div class='row well well-sm'>\n"+
			"         <a href='Hotel?id={id}&searchid="+searchId+"'>\n"+
			"<div class='col-sm-12'>\n"+
			"    <div class='col-sm-12'>\n"+
			"            <blockquote>{review}</blockquote>\n"+
			"    </div>\n"+
			"</div>\n"+
			"<div class='col-sm-12'>\n"+
			"	<div class='col-sm-12'>\n"+
			"    <div class='col-sm-4 pull-right'>\n"+
			"            <p class='text-danger'> {rating} </p>\n"+
			"    </div>\n"+
			"    </div>\n"+
			"</div>\n"+
			"</div>\n";


		ArrayList<Rating> lists = MongoConnection.retrieveDocument(String.valueOf(this.hotel.getId()));
		
		for(Rating list : lists){
			content += eachDiv.replace("{review}", list.getReview()).replace("{rating}", "Rating:"+list.getRating());
		}

		if(content.isEmpty()){
			content = "No reviews to display";
		}

		if(writeReview == 1){
				content=writeForm+content;
		}else{
			content="<div><a href='Hotel?id="+this.hotel.getId()+"&searchid="+searchId+"&writereview=1#review_section' class='btn btn-primary' role='button'>Give Review</a></div>" + content;
		}
		return content;
	}

	private String getCarauselSection(){
		ArrayList<String> images= this.hotel.getAllImage();
		String content = "";

		if(images.isEmpty()){
			return content;
		}

		content += "<div class='item active'><img src='"+images.get(0)+"' style='width:100%;'></div>";

		for (int i = 1; i < images.size(); i++) {
			content += "<div class='item'><img src='"+images.get(i)+"' style='width:100%;'></div>";
		}
		return content;
	}

	public void setError(String err){
		error += "<div class='alert alert-danger'><strong>Error!</strong>"+err+"</div>";
	}

	private void addError(String err){
		error += "<div class='alert alert-danger'><strong>Error!</strong>"+err+"</div>";
	}

	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+getBody()+Constants.pageFooter();
		return content;
	}
}

