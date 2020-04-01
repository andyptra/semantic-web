<%--
    Document   : search1
    Created on : Aug 22, 2019, 1:35:14 PM
    Author     : asus
--%>
<%@page import="myfunction.Library"%>
<%@page import="com.hp.hpl.jena.query.*;"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <%
                    String query = "SELECT DISTINCT ?namaTanaman ?namaLatin WHERE {?s <http://example.org/ethnomed#namaTanaman> ?namaTanaman . ?s <http://example.org/ethnomed#namaLatin> ?namaLatin .  } ORDER BY ?namaTanaman";
                    ResultSet rsfood = Library.runQuery(query);
                 
        %>

<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->

    <title>Daftar Tanaman</title>
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
    	<style>
		tbody tr  td:last-child{
		text-align: left !important;
		}
		thead tr th:last-child {
		text-align: left !important;
		}
		.table-borderless td,
		.table-borderless th {
		border: 0;
		}
	</style>
</head>

<body class="fix-header fix-sidebar">
    <!-- Preloader - style you can find in spinners.css -->
    
    <!-- Main wrapper  -->
    <div id="main-wrapper">
  
        <!-- End header header -->
        <!-- Left Sidebar  -->
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
                            <h2 class="m-b-0 text-white text-center">Daftar Tanaman</h2>
                            </div>
    <br/>
                            <div class="card-body">
                                

                                <table class="table table-bordered">
                                  <thead>
                                    <tr>
                                      <th scope="col">No</th>
                                      <th scope="col">Nama Tanaman</th>
                                      <th scope="col">Nama latin</th>
                                   
                                    </tr>
                                  </thead>
                                  <tbody>
                                     <%
                        int no = 0;
                        while (rsfood.hasNext()) {
                            no++;
                            QuerySolution qs = rsfood.nextSolution();
                 
            %>
            <tr>
               <td><%=no%></td>
                                    <td>
                                        <%=qs.getLiteral("namaTanaman").getValue().toString()%>
                                    </td>
                                     <td>
                                        <%=qs.getLiteral("namaLatin").getValue().toString()%>
                                    </td>
            </tr>
             <%
                        }
            %>
                                      
                                    
                                  </tbody>
                                </table>
                            </div>
                        </div>    
                
                
                
                
                
             
                <!-- End PAge Content -->
            </div>
            <!-- End Container fluid  -->
            <!-- footer -->
            
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>
    <!-- End Wrapper -->
    <!-- All Jquery -->
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