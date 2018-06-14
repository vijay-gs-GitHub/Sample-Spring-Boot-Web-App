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
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
   /*  Submit form using Ajax */
   $('button[type=submit]').click(function(e) {
   
      //Prevent default submission of form
      e.preventDefault();
      
      //Remove all errors
      $('input').next().remove();
      
      $.post({
         url : 'validateUser',
         data : $('form[name=loginForm]').serialize(),
         success : function(res) {
         console.log('response ='+res.status);
            if(res.status == 200 && res.statusCode == "SUCCESS"){
                $('#resultContainer_fail').hide();

               //Set response
               $('#resultContainer pre code').text(JSON.stringify(res.message));
               $('#resultContainer').show();
            
            }else{
              //Set error messages
              $.each(res.errorMessages,function(key,value){
  	            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }
         },
         error : function(e){
             console.log('Error ='+e.status+'message'+e.message);
             
             if(e.status == 404){
                 $('#resultContainer').hide();

                 //Set response
                 $('#resultContainer_fail pre code').text(JSON.stringify('User Not Found !!'));
                 $('#resultContainer_fail').show();
              
              }

         }
      })
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
        <td><input name="userName" type="text" /></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input name="password" type="password" /></td>
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
  <pre style="color: green;">
    <code></code>
   </pre>
</div>

<div id="resultContainer_fail" style="display: none;">
 <hr/>
  <pre style="color: red;">
    <code></code>
   </pre>
</div>

</body>
</html>