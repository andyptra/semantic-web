<%-- 
    Document   : search.jsp
    Created on : Aug 22, 2019, 1:26:37 PM
    Author     : asus
--%>
<%@page import= "myfunction.Process" %>
<%@page import = "myfunction.Library" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hasil Pencarian</title>
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
    <body>
  <%@include file="form.jsp" %>
   <div class="container-fluid">
  <div class="card card-outline-success">
<div class="card-body">
        <%
                    String keyword = request.getParameter("keyword").trim();
                    System.out.println(keyword);
                    Library.timeNeeded = System.nanoTime();
                    String normalis = Process.normalis(keyword);
                    String hasil = Process.getData(normalis);
                    boolean isOK = true;
                    if (hasil.compareToIgnoreCase("OK") == 0) {
        %>
        
        <br/><br/>
        <span id="query"></span>
        <div id="info">
            <b>Pertanyaan anda :</b> <%=keyword%><br/>
            <!--<b>Hasil Pencarian Untuk : "<i><%=Process.showMeaning()%></i>"</b><br/>-->
              
        </div>
        <%
//           out.print(Process.showList());
           out.print(Process.showResult());
//           out.print(Process.showQuery());
        } else {
         isOK = false;
        %>
        <span style="font-size:14px;">
            <b>Pertanyaan anda :</b> <%=keyword%><br/><br/>
            <b><span style="color:red;">Kalimat Pencarian Tidak Valid</span></b> <br/>
          <%  out.print(Process.showList()); %>
           
          
          <br/>
            <%=hasil%>
        </span>
        <%
 }
        %>
        <hr/>
 </div>
    </div>    
                <!-- End PAge Content -->
            </div>
    </div>
        
    </body>
</html>
