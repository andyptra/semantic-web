
<%@page import= "com.hp.hpl.jena.ontology.OntModel" %>
<%@page import= "java.io.InputStream" %>
<%@page import= "org.topbraid.spin.arq.ARQ2SPIN" %>
<%@page import= "org.topbraid.spin.arq.ARQFactory" %>
<%@page import= "org.topbraid.spin.model.Select" %>
<%@page import= "org.topbraid.spin.system.SPINModuleRegistry" %>

<%@page import= "org.topbraid.spin.constraints.ConstraintViolation"%>
<%@page import= "org.topbraid.spin.constraints.SPINConstraints"%>
<%@page import= "org.topbraid.spin.inference.SPINInferences"%>
<%@page import= "org.topbraid.spin.system.SPINLabels"%>
<%@page import= "org.topbraid.spin.system.SPINModuleRegistry"%>


<%@page import= "com.hp.hpl.jena.query.*" %>
<%@page import= "com.hp.hpl.jena.ontology.OntModelSpec" %>
<%@page import= "com.hp.hpl.jena.util.FileManager"%>
<%@page import= "com.hp.hpl.jena.rdf.model.Model"%>
<%@page import= "com.hp.hpl.jena.rdf.model.ModelFactory"%>
<%@page import= "com.hp.hpl.jena.shared.ReificationStyle" %>
<% //@page import= "myfunction.Global" %>
<%@page import= "myfunction.Library" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
          Library.start();
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
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

<body class="fix-header fix-sidebar" onload="search.keyword.focus()">
    <!-- Preloader - style you can find in spinners.css -->
    
    <!-- Main wrapper  -->
    <div id="main-wrapper">
          <%@include file="form.jsp" %>
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