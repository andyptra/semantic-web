<%-- 
    Document   : form
    Created on : Aug 22, 2019, 12:48:23 PM
    Author     : asus
--%>
<%@page import = "myfunction.Library" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->

    <title>Ethnomedicine</title>
<link rel="stylesheet" href="assets/css/lib/html5-editor/bootstrap-wysihtml5.css" />
    <link href="assets/css/lib/chartist/chartist.min.css" rel="stylesheet">
	<link href="assets/css/lib/owl.carousel.min.css" rel="stylesheet" />
    <link href="assets/css/lib/owl.theme.default.min.css" rel="stylesheet" />
    <!-- Bootstrap Core CSS -->
    <link href="assets/css/lib/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="assets/css/helper.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:** -->
    <!--[if lt IE 9]>
    <script src="https:**oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https:**oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
    </head>
    <body>
       <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
      <h2 class="my-0 mr-md-auto font-weight-normal"> <b> Ethnomedicine </b> </h2>
      <nav class="my-2 my-md-0 mr-md-3">
         <a class="p-2 text-dark" href="index.jsp">Home</a>
        <a class="p-2 text-dark" href="daftar_tanaman.jsp">Daftar Tanaman</a>
        <a class="p-2 text-dark" href="petunjuk.jsp">Petunjuk Penggunaan</a>
     
      </nav>
     
    </div>
        <div >
            <!-- Bread crumb -->
           
            <!-- End Bread crumb -->
            <!-- Container fluid  -->
            <div class="container-fluid">
                <!-- Start Page Content -->
 
<div class="card card-outline-success">
                            <div class="card-header">
                            <h2 class="m-b-0 text-white text-center">Masukkan Keyword Pencarian</h2>
                            </div>
                            <div class="card-body">
                                

                                <form action="search.jsp" method="post" class="form-horizontal">
                                    <div class="form-body">
                                      
                                        <hr class="m-t-0 m-b-40">
                                          
                                                <div class="form-group row">
                                                   
                                                    <div class="col-md-11">
                                                        <input type="text" name="keyword" class="form-control" autocomplete="on" placeholder="Keyword">
                                                        </div>
                                                        
                                                    <div class="col-md-1">
                                                         <button style="float:right" type="submit" class="btn btn-info">Search</button>
                                                        </div>
                                                </div>
                                    </div>
                                </form>
             
                            </div>
                        </div>    
                
                
                
                
                
             
                <!-- End PAge Content -->
            </div>
            <!-- End Container fluid  -->
            <!-- footer -->
            
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
          <script src="assets/js/lib/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="assets/js/lib/bootstrap/js/popper.min.js"></script>
    <script src="assets/js/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/custom.min.js"></script>
     <script src="assets/js/lib/html5-editor/wysihtml5-0.3.0.js"></script>
    <script src="assets/js/lib/html5-editor/bootstrap-wysihtml5.js"></script>
    <script src="assets/js/lib/html5-editor/wysihtml5-init.js"></script>
    <!--Custom JavaScript -->

    </body>
</html>
