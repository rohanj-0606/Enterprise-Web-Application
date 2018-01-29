package com.hotel.view;
import com.hotel.config.*;
import com.hotel.model.*;
import java.io.*;

public class LoginPage{
	private String loginError;
	private String redirectPage;

	public void setRedirect(String str){
		redirectPage = str;
	}

	private String getBody(){
		String content ="<div class='container'>\n"+
			"    <div class='row'>\n"+
			"               <div class='span12'>\n";

		if(loginError!=null){
			content+=loginError;
		}

		content+="                       <form class='form-horizontal' action='Login' method='POST'>\n"+
		"                         <fieldset>\n"+
		"                           <div id='legend'>\n"+
		"                             <legend class=''>Login</legend>\n"+
		"                           </div>\n"+
		"                           <div class='control-group'>\n"+
		"                             <label class='control-label'  for='username'>Email</label>\n"+
		"                             <div class='controls'>\n"+
		"                               <input type='text' id='email' name='email' placeholder='' class='input-xlarge'>\n"+
		"                             </div>\n"+
		"                           </div>\n";

		if(redirectPage!=null){
				content+="                           <div class='control-group'>\n"+
				"                             <div class='controls'>\n"+
				"                               <input type='hidden' id='redirect' name='redirect' value='"+redirectPage+"'>\n"+
				"                             </div>\n"+
				"                           </div>\n";
		}
		content+="                           <div class='control-group'>\n"+
		"                             <label class='control-label' for='password'>Password</label>\n"+
		"                             <div class='controls'>\n"+
		"                               <input type='password' id='password' name='password' placeholder='' class='input-xlarge'>\n"+
		"                             </div>\n"+
		"                           </div>\n"+
		"                           <div class='control-group'>\n"+
		"                             <div class='controls'>\n<br>"+
		"                               <button class='btn btn-success'>Login</button>\n"+
		"                             </div>\n"+
		"                           </div>\n"+
		"                         </fieldset>\n"+
		"                       </form>\n"+
		"               </div>\n"+
		"       </div>\n"+
		"</div>\n";
		loginError = null;
		return content;
	}

	public void setError(String err){
		loginError = "<div class='alert alert-danger'><strong>Alert!</strong>"+err+"</div>";
	}
	
	public String getContent() throws IOException{
		String content = Constants.pageHeader()+Constants.pageNav(-1)+getBody()+Constants.pageFooter();
		return content;
	}
}

