package com.hotel.config;
import java.util.*;
import com.hotel.model.*;
import java.sql.*;
import java.io.*;

public class Constants{
	public static final String viewUri = "/HotelReservation/view/"; 
	public static final String baseUri = "/HotelReservation";
	public static final String cssUri = "http://localhost:8080/HotelReservation/css/";
	public static final String jsUri = "http://localhost:8080/HotelReservation/js/";
	public static final String imgUri = "http://localhost:8080/HotelReservation/img/";
	public static final String title = "Hotel Reservation System";
	public static final String countryName = "United States";
	public static final String cityName = "Chicago";
	public static final String dbUrl = "jdbc:mysql://localhost:3306/HotelReservation?useSSL=false";
	public static final String dbUser = "root";
	public static final String dbPass = "toor";
	public static final String telephoneNo = "128683134";
	public static final String address = "City Hall<br>121 N. LaSalle Street<br>Chicago, Illinois 60602<br>";
	public static final String mongoDbHost = "localhost";
	public static final int mongoDbPort = 27017;
	public static final String mongoDbUser = "vipul";
	public static final String mongoDbPass = "vipul";
	public static final String mongoDatabase = "HotelReservation";
	public static final String mongoColletion = "ReviewCollection";


	public static String getView(){
		return viewUri;
	}

	public static String getView(String uri){
                return viewUri + uri;
    }

    public static String pageHeader(){
    	String head = "<!DOCTYPE html>\n"+
"<html lang='en'>\n"+
    "<head>\n"+
     "<meta charset='utf-8'>\n"+
      "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"+
        "<title>"+Constants.title+"</title>\n"+
        "<meta name='viewport' content='width=device-width, initial-scale=1'>\n"+
        "<link rel='icon' href='" + Constants.imgUri+"favicon.ico' sizes='16x16'>\n"+
        "<link href='http://fonts.googleapis.com/css?family=Playfair+Display:400,700,900' rel='stylesheet' type='text/css'>\n"+
        "<link href='http://fonts.googleapis.com/css?family=Karla:700,400' rel='stylesheet' type='text/css'>\n"+
        "<link href='http://fonts.googleapis.com/css?family=Lora:400,700' rel='stylesheet' type='text/css'>\n"+
	"<link href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css' rel='stylesheet' type='text/css'>\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"font-awesome.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"bootstrap.min.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"uikit.min.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"animate.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"datepicker.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"owl.carousel.css'>\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"rev-slider/settings.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"lightslider.css'>\n"+
        //"<link rel='stylesheet' href='"+Constants.cssUri+"reset.css'>\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"style.css' />\n"+
        "<link rel='stylesheet' href='"+Constants.cssUri+"responsive.css' /></head>\n";

                return head;
    }

    public static String pageNav(int userId) throws IOException{
    	int role = UserModel.getUserRole(userId);
    	String content = "<body>\n"+
"        <div id='loader-wrapper'>\n"+
"            <div class='logo'><a href='"+Constants.baseUri+"'><span>Hotel</span>-Booking</a></div>\n"+
"            <div id='loader'>\n"+
"            </div>\n"+
"        </div>\n"+
"        <header class='header_area'>\n"+
"            <div class='main_header_area'>\n"+
"                <div class='container'>\n"+
"                    <div class='mainmenu'>\n"+
"                        <div id='nav'>\n"+
"                            <nav class='navbar navbar-default'>\n"+
"                                <div class='navbar-header'>\n"+
"                                  <button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'>\n"+
"                                    <span class='sr-only'>Toggle navigation</span>\n"+
"                                    <span class='icon-bar'></span>\n"+
"                                    <span class='icon-bar'></span>\n"+
"                                    <span class='icon-bar'></span>\n"+
"                                  </button>\n"+
"                                  <div class='site_logo fix'>\n"+
"                                      <a id='brand' class='clearfix navbar-brand border-right-whitesmoke' href='"+Constants.baseUri+"''><img src='"+Constants.imgUri+"site-logo.png' alt='Trips'></a>\n"+
"                                  </div>\n"+
"                                </div>\n"+
"                                <div class='collapse navbar-collapse navbar-right' id='bs-example-navbar-collapse-1'>\n"+
"									<ul class='nav navbar-nav'>\n"+
"                                    	<li role='presentation' class='dropdown'>\n"+
"                                        	<a  href='"+Constants.baseUri+"' role='button' aria-expanded='false'>\n"+
"                                          		Home\n"+
"                                        	</a>\n"+
"                                    	</li>\n";

if(userId < 1){
content+="                              <li><a href='Login'>Login</a></li>\n"+
"                                       <li><a href='Register'>Register</a></li>\n";
}else{
content+="                              <li><a href='Logout'>Logout</a></li>\n";
}

if(role <=2){
	content+= "<li><a href='ManageHotel?operation=view'>Admin</a></li>\n";
}
content+="                               <li><a href='Contact'>Contacts</a></li>\n"+
"                                 	 </ul>\n"+
"                                  <div class='emergency_number'>\n"+
"                                      <a href='tel:"+Constants.telephoneNo+"'><img src='"+Constants.imgUri+"call-icon.png' alt=''>"+Constants.telephoneNo+"</a>\n"+
"                                  </div>\n"+
"                                </div>\n"+
"                            </nav>\n"+
"                        </div>\n"+
"                    </div>\n<br />"+
"                </div>\n"+
"            </div>\n"+
"        </header>\n";

    	return content;
    }

    public static String getAdminPanel(int userId){

    	String content ="<div class='collapse navbar-collapse'><ul class='nav navbar-nav navbar-right'>";
    	content+="<li><a href='ManageHotel?operation=update' class='btn btn-info' role='button'>Update Hotel</a></li><br>";
    	content +="<li><a href='ManageHotel?operation=add' class='btn btn-info' role='button'>Add Hotel</a></li><br>";
    	content +="<li><a href='ManageHotel?operation=delete' class='btn btn-info' role='button'>Delete Hotel</a></li><br></ul></div>";

		return content;
    }

    public static String defaultImageCarausel() throws IOException{
    	try{
    	ArrayList<String> images = DefaultCarauselModel.getImage();

    	String content = "<div class='container'><div class='col-sm-12'><div class='carousel slide' data-ride='carousel'><div class='carousel-inner' style='width: 100%; max-height: 250px !important;'>";

		if(images.isEmpty()){
			return content;
		}

		content += "<div class='item active'><img src='"+images.get(0)+"' style='width:100%;'></div>";

		for (int i = 1; i < images.size(); i++) {
			content += "<div class='item'><img src='"+images.get(i)+"' style='width:100%;'></div>";
		}
		content += "</div></div><hr /><div><div>";

		return content;
		}catch (SQLException e){
			throw new IOException(e.getMessage());
		}
    }

    public static String pageFooter(){
			String content = "<hr /> <footer class='footer_area'>\n"+
			"<div class='container'>\n"+
			"<div class='footer>\n"+
			"<div class='footer_top padding-top-80 clearfix'>\n"+
			"<div class='col-lg-4 col-md-4 col-sm-4'>\n"+
			"<div class='footer_widget'>\n"+
			"<div class='footer_logo'>\n"+
			"<a href='/'><img src='"+Constants.imgUri+"footer-logo-one.png'></a>\n"+
			"</div>\n"+
			"<p>One of the best and hassle free booking system. </p>\n"+
			"<ul>\n"+
			"<li>\n"+
			"<P><i class='fa fa-map-marker'></i>"+Constants.countryName+", <br>"+ Constants.cityName+"</P>\n"+
			"</li>\n"+
			"</ul>\n"+
			"</div>\n"+
			"</div>\n"+
			"<div class='col-lg-4 col-md-4 col-sm-4'>\n"+
			"<div class='row'>\n"+
			"<div class='footer_widget clearfix'>\n"+
			"<h5 class='padding-left-15'>Quick Links</h5>\n"+
			"<div class='col-lg-6 col-md-6 col-sm-6'>\n"+
			"<ul>\n"+
			"<li><a href='Contact'>Contact Us</a></li>\n"+
			"<li><a href='About'>About Us</a></li>\n"+
			"<li><a href='Complaint'>Complaints</a></li>\n"+
			"</ul>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"<div class='col-lg-4 col-md-4 col-sm-4'>\n"+
			"<div class='footer_widget'>\n"+
			"<h5>We Are Global</h5>\n"+
			"<div class='footer_map'>\n"+
			"<a href='Contact'><img src='"+Constants.imgUri+"footer-map-two.jpg'></a>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"<div class='row'>\n"+
			"<div class='container'>\n"+
			"<div class='footer_copyright margin-tb-50 content-center'>\n"+
			"<p>© 2017 Hotelbooking. All rights reserved</p>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"</div>\n"+
			"</footer>\n"+
			"<script src='"+Constants.jsUri+"vendor/jquery-1.11.2.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"bootstrap.min.js'></script>\n"+
			//"<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"rev-slider/rs-plugin/jquery.themepunch.plugins.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"rev-slider/rs-plugin/jquery.themepunch.revolution.js'></script>\n"+
			"<script src='"+Constants.jsUri+"rev-slider/rs.home.js'></script>\n"+
			"<script src='"+Constants.jsUri+"uikit.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"jquery.easing.1.3.min.js'></script>\n"+
			//"<script src='"+Constants.jsUri+"datepicker.js'></script>\n"+
			"<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"jquery.scrollUp.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"owl.carousel.min.js'></script>\n"+
			"<script src='"+Constants.jsUri+"lightslider.js'></script>\n"+
			"<script src='"+Constants.jsUri+"wow.min.js'></script>\n"+
			"<script type='text/javascript'>new WOW().init();</script>\n"+
			"<script src='"+Constants.jsUri+"main.js'></script>\n";
			content+=Constants.additionalScript();
			content+="</body></html>";
			return content;
    }

	public static String additionalScript(){
		String content = null;
		content = "<script>$('.datepicker').datepicker({format: 'yyyy-mm-dd',startDate: '+1d'});</script>";
		return content;
	}

}
