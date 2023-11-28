<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("dev") == null || !(Boolean) session.getAttribute("dev")) {
        response.sendRedirect("render");
        return;
    }
%>
<html lang="en">
    <head>
        <base href="./">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Lowcodesystem</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="CMS auto gerenciavel.">
        <meta name="author" content="Maykon Vaz">
        <link rel="apple-touch-icon" sizes="57x57" href="libs/assets/favicon/apple-icon-57x57.png">
        <link rel="apple-touch-icon" sizes="60x60" href="libs/assets/favicon/apple-icon-60x60.png">
        <link rel="apple-touch-icon" sizes="72x72" href="libs/assets/favicon/apple-icon-72x72.png">
        <link rel="apple-touch-icon" sizes="76x76" href="libs/assets/favicon/apple-icon-76x76.png">
        <link rel="apple-touch-icon" sizes="114x114" href="libs/assets/favicon/apple-icon-114x114.png">
        <link rel="apple-touch-icon" sizes="120x120" href="libs/assets/favicon/apple-icon-120x120.png">
        <link rel="apple-touch-icon" sizes="144x144" href="libs/assets/favicon/apple-icon-144x144.png">
        <link rel="apple-touch-icon" sizes="152x152" href="libs/assets/favicon/apple-icon-152x152.png">
        <link rel="apple-touch-icon" sizes="180x180" href="libs/assets/favicon/apple-icon-180x180.png">
        <link rel="icon" type="image/png" sizes="192x192" href="libs/assets/favicon/android-icon-192x192.png">
        <link rel="icon" type="image/png" sizes="32x32" href="libs/assets/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="96x96" href="libs/assets/favicon/favicon-96x96.png">
        <link rel="icon" type="image/png" sizes="16x16" href="libs/assets/favicon/favicon-16x16.png">
        <link rel="manifest" href="libs/assets/favicon/manifest.json">
        <meta name="msapplication-TileColor" content="#ffffff">
        <meta name="msapplication-TileImage" content="assets/favicon/ms-icon-144x144.png">
        <meta name="theme-color" content="#ffffff">
        <!-- Vendors styles-->
        <link rel="stylesheet" href="libs/vendors/simplebar/css/simplebar.css">
        <link rel="stylesheet" href="libs/css/vendors/simplebar.css">
        <!-- Main styles for this application-->
        <link href="libs/css/style.css" rel="stylesheet">
        <link href="libs/css/lowcodesystem.css" rel="stylesheet">
        <script src="libs/js/jquery.min.js"></script>
    </head>
    <body>
        <div class="container py-3">
            <div class="card">
                <h5 class="card-header">Database Admin</h5>
                <div class="card-body">
                    <form id="formSQL" action="builder" method="post">
                        <input type="hidden" name="session" value="script"/>
                        <div class="row">
                            <div class="well col-md-12">
                                <% if (request.getAttribute("msg") != null) {%>
                                <div class="alert alert-success">
                                    <%=request.getAttribute("msg")%>
                                </div>
                                <%}%>
                                <div id="div_datasource">
                                    <div class="mb-3 col-md-4">
                                        <label for="scriptDS" class="form-label">Datasource</label>
                                        <select id="scriptDS" name="scriptDS" class="form-select" size="1" style="height: 34px">	
                                            <% for (String key : DataSource.database.keySet()) {
                                                    out.println("<option value=\"" + key + "\" " + (request.getParameter("scriptDS") != null && request.getParameter("scriptDS").equals(key) ? "selected" : "") + ">" + key + "</option>");
                                                }%>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="mb-3 col-md-12">
                                        <label for="script" class="form-label">Script</label>
                                        <textarea id="script" name="script" class="form-control" autofocus placeholder="Digite seu script SQL.                  [CTRL+ENTER] executa" rows="5"><%=(request.getParameter("script") == null) ? "" : request.getParameter("script")%></textarea>
                                    </div>
                                </div>

                                <br clear="all"/>

                                <a href="render" class="btn btn-secondary" title="Voltar para a Aplicação">Voltar</a>
                                <input type="submit"  class="btn btn-primary" value="Executar" style="float: right">
                                <input type="submit"  class="btn btn-primary mx-3" name="btn_db_info" value="Database Info" style="float:right;">
                                <br>
                                <br>
                                <%=request.getAttribute("result") != null ? request.getAttribute("result") : ""%>
                            </div>
                        </div> 
                    </form>
                </div> 
            </div>
            <!--/.fluid-container-->
            <script type="text/javascript">
                $('#script').keydown(function (e) {
                    if (e.ctrlKey && e.keyCode == 13) {
                        document.getElementById('formSQL').submit();
                    }
                });
            </script>
            <!-- CoreUI and necessary plugins-->
            <script src="libs/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
            <script src="libs/vendors/simplebar/js/simplebar.min.js"></script>
            <script src="libs/js/lowcodesystem.js"></script>
    </body>
</html>