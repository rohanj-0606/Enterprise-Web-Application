package com.hotel.view;
import com.hotel.config.*;
import java.io.*;

public class RegisterPage{
	private int userId;
	private String error;
	
	public RegisterPage(int id){
		userId = id;	
	}

	public void setError(String str){
		error = "<div class='alert alert-danger'><strong>Error!</strong>"+str+"</div>";
	}

	private String generateBody(){
			String content = "<div class='container'><div class='col-sm-12'><div class='col-sm-6'><h2 class='text-center'>Create Account</h2></div></div>\n";

			if(error!=null){
				content+=error;
			}

			content+="<form class='form-horizontal' action='Register' method='POST'>\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"        <input type='text' class='form-control' id='email' name='email' placeholder='Email Id'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"    <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"        <input type='text' class='form-control' id='mobile' name='mobile' placeholder='Mobile Number'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"     <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"          <input type='password' class='form-control' id='password' name='password' placeholder='Passsword'>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"\n"+
			"     <div class='form-group'>\n"+
			"        <div class='col-sm-6'>\n"+
			"                 <button type='submit' class='btn btn-primary btn-block'>Register</button>\n"+
			"        </div>\n"+
			"    </div>\n"+
			"  </form>\n"+
			"    </div>\n";

			return content;
	}

	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(userId)+Constants.defaultImageCarausel()+generateBody()+Constants.pageFooter();
		return content;
	}
}
