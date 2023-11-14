<%@page import="java.util.Date"%>
<%@page import="br.com.lowcodesystem.ctrl.Upload"%>
<!DOCTYPE html><!--
* CoreUI - Free Bootstrap Admin Template
* @version v4.2.2
* @link https://coreui.io/product/free-bootstrap-admin-template/
* Copyright (c) 2023 creativeLabs Łukasz Holeczek
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
        <style>
            <%=Render.project.getCssGeral()%>
        </style>
    </head>
    <body>
        <%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
        <a href="config.jsp" class="btn btn-primary" style="margin:10px; width: 150px;"><i class="glyphicon glyphicon-cog" style="margin-right: 5px;"></i>Configura��o</a>
        <%}%>
        <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="card-group d-block d-md-flex row">
                            <div class="card col-md-7 p-4 mb-0">
                                <div class="card-body">
                                    <% Object error = request.getAttribute("error");
                                        if (error != null && !error.toString().isEmpty()) {
                                            out.println("<div class=\"alert alert-danger\">");
                                            out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">�</button>");
                                            out.println(error);
                                            out.println("</div>");
                                        }
                                    %>
                                    <form action="auth" method="post">
                                        <input type="hidden" name="action" value="login"/>
                                        <h1>Login</h1>
                                        <p class="text-medium-emphasis">Inicie sess�o na sua conta</p>
                                        <div class="input-group mb-3"><span class="input-group-text">
                                                <svg class="icon">
                                                <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-user"></use>
                                                </svg></span>
                                            <input name="username" class="form-control" type="text" placeholder="Username">
                                        </div>
                                        <div class="input-group mb-4"><span class="input-group-text">
                                                <svg class="icon">
                                                <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-lock-locked"></use>
                                                </svg></span>
                                            <input name="password" id="password" class="form-control" type="password" placeholder="Password">
                                        </div>
                                        <div class="row">
                                            <div class="col-6">
                                                <button class="btn btn-primary px-4" type="submit">Login</button>
                                            </div>
                                            <div class="col-6 text-end">
                                                <button class="btn btn-link px-0" type="button">Esqueceu a senha?</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="card col-md-5 text-white bg-primary py-5">
                                <form method="get" action="register.jsp">
                                    <div class="card-body text-center">
                                        <div>
                                            <h2>Incri��o</h2>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                            <button class="btn btn-lg btn-outline-light mt-3" type="submit">Increva-se agora!</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="modal_login.jsp" %>
        <!-- CoreUI and necessary plugins-->
        <script src="libs/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
        <script src="libs/vendors/simplebar/js/simplebar.min.js"></script>
        <script>
        </script>

    </body>
</html>