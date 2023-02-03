<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring-Boot-POC</title>

<style type="text/css">
  span.error{
    color: red;
    margin-left: 5px;
  }
  .greenSuccess{
      color: green;
  }
  .redFail{
      color: red;
  }
  
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
   /*  Submit form using Ajax */
   $('button[type=submit]').click(function(e) {
   
      //Prevent default submission of form
      e.preventDefault();  
      
	  var pass= $('#password').val();
	     	  
      if(pass!=""){      
      $('#password').val(CryptoJS.MD5(pass));
      }
      
      //Remove all errors
      $('input').next().remove();
      
      $.post({
         url : 'validateUser',
         data : $('form[name=loginForm]').serialize(),
         success : function(res) {
         console.log('response ='+res.status);
            if(res.status == 200 && res.statusCode == "SUCCESS"){
               //Set response   
               $('#resultContainer pre code').text(JSON.stringify(res.message));
               $('#resultContainer pre').removeClass("redFail").addClass("greenSuccess");

               $('#resultContainer').show();  
            
            }else{
              //Set error messages
              $.each(res.errorMessages,function(key,value){
  	            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }
         },
         error : function(xhr, status, error){
        	 var r = jQuery.parseJSON(xhr.responseText);        	 
             console.log('Status = '+xhr.status+' Message  = '+r.message);                          
             if(xhr.status == 401){
                 //Set response
                 $('#resultContainer pre code').text(JSON.stringify(r.message));
                 $('#resultContainer pre').removeClass("greenSuccess").addClass("redFail");

                 $('#resultContainer').show();
              
              }
         }
         
      })

      $('#password').val(pass);

   });
});
</script>
</head>
<body>
  <h1>Login User Verification</h1>
  <hr />

  <form action="validateUser" method="post" name="loginForm">
    <table>
      <tr>
        <td>User Name</td>
        <td><input name="userName" id="userName" type="text" /></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input name="password" id="password" type="password" /></td>
      </tr>
      <tr>
        <td></td>
        <td><button type="submit">Verify</button></td>
      </tr>
    </table>
  </form>

<!-- Result Container  -->
<div id="resultContainer" style="display: none;">
 <hr/>
  <pre>
    <code></code>
   </pre>
</div>
</body>
</html>