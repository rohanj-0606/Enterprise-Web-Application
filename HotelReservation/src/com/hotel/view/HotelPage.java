package com.hotel.view;
import com.hotel.config.*;
import java.util.*;  
import com.hotel.libraries.*;
import com.hotel.model.*;
import java.io.*;

public class HotelPage{
	private String error;
	private int userId;
	private String search;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfGuests;
    private int rating;
    private int priceStart;
    private int priceEnd;
    private int searchId;

	public HotelPage(int usrId){
		error = "";
		userId = 0;
		search = "";
		checkInDate = "";
		checkOutDate = "";
		numberOfGuests = -1;
		rating = -1;
		priceStart = 0;
		priceEnd = 9999999;

		if(usrId > 0){
			userId = usrId;
		}else{
			userId = 0;
		}

	}

	public HotelPage(){
		error = "";
		userId = 0;
		search = "";
		checkInDate = "";
		checkOutDate = "";
		numberOfGuests = -1;
		rating = -1;
		priceStart = 0;
		priceEnd = 9999999;
	}

	public int getSearchId(){
		return searchId;
	}

	public void setSearchFilter(String srch){
		if(!srch.isEmpty()){
			search = srch;
		}
	}

	public void setCheckInDate(String str){
		if(!str.isEmpty()){
			checkInDate = str;
		}
	}

	public void setCheckOutDate(String str){
		if(!str.isEmpty()){
			checkOutDate = str;
		}

		if(checkInDate.compareTo(checkOutDate) > 0){
			addError("Check out date should be greater than check in date");
		}
	}

	public void setGuestCount(int val){
		if(val > 0){
			numberOfGuests = val;
		}
	}

	public void setPriceStart(int val){
		if(val > 0){
			priceStart = val;
		}
	}

	public void setRating(int val){
		if(val >= 0 && val <5){
			rating = val;
		}else if(val >= 5){
			rating = 5;
		}
	}

	public void setPriceEnd(int val){
		if(val > 0){
			priceEnd = val;
		}

		if(priceEnd < priceStart){
			addError("Start range is greater than end range");
		}
	}

	private String getBody(){
		String content = "<div class='container-fluid'>\n"+
		"	<div class='col-sm-2 bg-info' style='border:1px solid #cecece;'>\n"+
		"	<div class='col-sm-12'>\n"+
		"		<div class='page-header'>\n"+
		"    			<h2>Filters</h2>      \n"+
		"  		</div>\n"+
		"		<form class='form-horizontal' action='Hotels'>\n"+
		"    			<div class='form-group'>\n"+
		"				<label>Search Query:</label>\n"+
		"        			<input type='text' class='form-control' id='search' name='search' value='{search}'>\n"+
		"    			</div>\n"+
		"    			<div class='form-group'>\n"+
		"				<label>Check In:</label>\n"+
		"				<div class='input-group date datepicker'>\n"+
		"                    			<input type='text' class='form-control' id='checkin' name='checkin' value='{check_in}'/>\n"+
		"                    			<span class='input-group-addon'>\n"+
		"                        			<span class='glyphicon glyphicon-calendar'></span>\n"+
		"                    			</span>\n"+
		"				</div>\n"+
		"                	</div>\n"+
		"	\n"+
		"			<div class='form-group'>	\n"+
		"				<label>Check Out:</label>\n"+
		"				<div class='input-group date datepicker'>\n"+
		"                    			<input type='text' class='form-control' id='checkout' name='checkout' value='{check_out}'/>\n"+
		"                    			<span class='input-group-addon'>\n"+
		"                        			<span class='glyphicon glyphicon-calendar'></span>\n"+
		"                    			</span>\n"+
		"                		</div>\n"+
		"    			</div>\n"+
		"    \n"+
		"			<div class='form-group'>        \n"+
		"				<label>Required Room:</label>\n"+
		"                		<input type='number' class='form-control' id='people' name='people' value='{total_guest}'/>\n"+
		"    			</div>\n"+
		"\n"+
		"			<div class='form-group'>\n"+
		"                                <label>Min Price:</label>\n"+
		"                                <input type='number' class='form-control' id='pricestart' name='pricestart' value='{price_start}'/>\n"+
		"                        </div>\n"+
		"\n"+
		"			<div class='form-group'>\n"+
		"                                <label>Max Price:</label>\n"+
		"                                <input type='number' class='form-control' id='priceend' name='priceend' value='{price_end}'/>\n"+
		"                        </div>\n"+
		"\n"+
		"			<div class='form-group'>\n"+
		"                                <label>Rating:</label>\n"+
		"                                <input type='number' class='form-control' id='rating' name='rating' value='{rating}'/>\n"+
		"                        </div>\n"+
		"\n"+
		"    			<div class='form-group'>\n"+
		"                 		<button type='submit' class='btn btn-primary btn-block'>Refine</button>\n"+
		"    			</div>\n"+
		"\n"+
		"  		</form>\n"+
		"	</div>\n"+
		"	</div>\n"+
		"\n"+
		"	<div class='col-sm-10 bg-dark text-white'>\n"+
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

		content = content.replace("{search}", search);
		content = content.replace("{check_in}", checkInDate);
		content = content.replace("{check_out}", checkOutDate);
		content = content.replace("{total_guest}", Integer.toString(numberOfGuests));
		content = content.replace("{price_start}", Integer.toString(priceStart));
		content = content.replace("{price_end}", Integer.toString(priceEnd));
		content = content.replace("{rating}", Integer.toString(rating));
		content = content.replace("{inner_page}", getHotelList());

		return content;
	}

	private String getHotelList(){
		String content = "";
		String eachDiv = "<div class='row well well-sm'>\n"+
			"         <a href='Hotel?id={id}&searchid="+searchId+"'>\n"+
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

		if(!error.isEmpty()){
			return error;
		}

		try{
			ArrayList<HotelLib> allHotels = HotelModel.getHotel(search, rating, priceStart, priceEnd);

			if(allHotels.isEmpty()){
				addError("No hotels available");
			}

			for(HotelLib htl : allHotels){
					String tempVal = null;
	    			tempVal = eachDiv.replace("{id}", String.valueOf(htl.getId())).replace("{image_src}", htl.getImage()).replace("{name}", htl.getName()).replace("{description}", htl.getDescription());
	    			tempVal = tempVal.replace("{rating}", String.valueOf(htl.getRating())).replace("{price}", String.valueOf(htl.getPrice()));  
	 		 		
	 		 		if(!tempVal.isEmpty()){
	 		 			content += tempVal;
	 		 		}
	 		}
 		}catch (Exception e){
 			addError(e.getMessage());
 		}
		
		if(!error.isEmpty()){
			return error;
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

	public void addSearch(){
		try{
			searchId = SearchModel.addSearch(checkInDate, checkOutDate, String.valueOf(numberOfGuests));
		} catch (Exception e){
			addError(e.getMessage());
		}
	}

}

