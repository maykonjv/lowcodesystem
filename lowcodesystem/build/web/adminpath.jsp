
<%@page import="br.com.lowcodesystem.util.ProjectLoad"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if ((session.getAttribute("dev") == null || !(Boolean) session.getAttribute("dev"))) {
        response.sendRedirect("config.jsp");
        return;
    }
%>
<!DOCTYPE html><!--
* CoreUI - Free Bootstrap Admin Template
* @version v4.2.2
* @link https://coreui.io/product/free-bootstrap-admin-template/
* Copyright (c) 2023 creativeLabs Åukasz Holeczek
* Licensed under MIT (https://github.com/coreui/coreui-free-bootstrap-admin-template/blob/main/LICENSE)
-->
<html lang="en">
    <head>
        <base href="./">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><%=request.getAttribute("projectName")%></title>
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
        <!-- We use those styles to show code examples, you should remove them in your application.-->
        <link href="libs/css/examples.css" rel="stylesheet">
    </head>
    <body>
        <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
            <div class="container">
                <form   action="builder" method="post" id="formAdmin">
                    <input type="hidden" name="session" value="adminA"/>
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card mb-4 mx-4">
                                <div class="card-body p-4">
                                    <h1>Configurações do Projeto</h1>
                                    <% if (request.getAttribute("msg1") != null) {%>
                                    <div class="alert alert-success">
                                        <button type="button" class="close" data-dismiss="alert">×</button>
                                        <%=request.getAttribute("msg1")%>
                                    </div>
                                    <%}%>
                                    <br/>
                                    <p class="text-medium-emphasis">Configuração gerais do sistema.</p>
                                    <div class="mb-3">
                                        <label for="projectName" class="form-label">Nome do Projeto</label>
                                        <input type="text" class="form-control" id="projectName" name="projectName" placeholder="Sistema ABCD">
                                    </div>
                                    <div class="mb-3">
                                        <label for="projectID" class="form-label">ID do Projeto</label>
                                        <input type="text" class="form-control" id="projectID" name="projectID" placeholder="sistema_abcd">
                                    </div>
                                    <div class="mb-3">
                                        <label for="pathSchema" class="form-label">Pasta dos projetos</label>
                                        <input type="text" class="form-control" id="pathSchema" name="pathSchema" placeholder="/home/lowcodesystem/" value="<%=FormataTexto.toString(ManterXML.pasta)%>">
                                    </div>
                                     
                                    <div class="row">
                                        <div class="col-6">
                                            <button class="btn btn-block btn-light" type="button" onclick="history.go(-1)">Cancel</button>
                                        </div>
                                        <div class="col-6 text-end">
                                            <button class="btn btn-block btn-success" type="submit">Confirmar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <!-- CoreUI and necessary plugins-->
            <script src="libs/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
            <script src="libs/vendors/simplebar/js/simplebar.min.js"></script>
            <script>
            </script>

    </body>
</html>