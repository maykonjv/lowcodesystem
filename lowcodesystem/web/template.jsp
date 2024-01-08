<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="br.com.lowcodesystem.ctrl.Upload"%>
<%
    if (session.getAttribute("authenticated") == null || !(Boolean) session.getAttribute("authenticated")) {
        response.sendRedirect("render");
        return;
    }
%>
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
        <link href="libs/css/lowcodesystem.css" rel="stylesheet">
        <script src="libs/js/jquery.min.js"></script>
        <link href="libs/vendors/datatable/datatables.min.css" rel="stylesheet" type="text/css"/>
        <script src="libs/vendors/datatable/datatables.min.js" type="text/javascript"></script>
        <link href="libs/vendors/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

        <script src="https://unpkg.com/htmx.org@1.9.9" integrity="sha384-QFjmbokDn2DjBjq+fM+8LUIVrAgqcNW2s0PjAxHETgRn9l4fvX31ZxDxvwQnyMOX" crossorigin="anonymous"></script>
        <style>
            <%=Render.project.getCssGeral()%>
        </style>        
    </head>
    <body>
        <div class="sidebar sidebar-dark sidebar-fixed" id="sidebar">
            <div class="sidebar-brand d-none d-md-flex">
                <!--                <svg class="sidebar-brand-full" width="118" height="46" alt="CoreUI Logo">
                                <use xlink:href="libs/assets/brand/coreui.svg#full"></use>
                                </svg>
                                <svg class="sidebar-brand-narrow" width="46" height="46" alt="CoreUI Logo">
                                <use xlink:href="libs/assets/brand/coreui.svg#signet"></use>
                                </svg>-->
                <a id="id_link_nome_projeto" class="sidebar-brand-full navbar-brand" href="render" style="<%=Render.project.getProjectNameCSS()%>">  
                    <span id="id_spn_nome_projeto"><%=Render.project.getProjectName()%></span>
                </a>
                <a id="id_link_nome_projeto" class="sidebar-brand-narrow" href="render" style="<%=Render.project.getProjectNameCSS()%>">  
                    <span id="id_spn_nome_projeto"> LCS </span>
                </a>
            </div>
            <%@include file="components/_menu.jsp" %>
            <button class="sidebar-toggler" type="button" data-coreui-toggle="unfoldable"></button>
        </div>
        <div class="wrapper d-flex flex-column min-vh-100 bg-light">
            <header class="header header-sticky mb-4">
                <div class="container-fluid">
                    <button class="header-toggler px-md-0 me-md-3" type="button" onclick="coreui.Sidebar.getInstance(document.querySelector('#sidebar')).toggle()">
                        <i class="fa fa-bars" aria-hidden="true"></i>
                    </button><a class="header-brand d-md-none" href="libs/#">
                        <svg width="118" height="46" alt="CoreUI Logo">
                        <use xlink:href="libs/assets/brand/coreui.svg#full"></use>
                        </svg></a>
                    <ul class="header-nav d-none d-md-flex">
                        <%@include file="components/header_config_geral.jsp" %>
                    </ul>
                    <ul class="header-nav ms-auto">
                        <!--                        <li class="nav-item"><a class="nav-link" href="libs/#">
                                                        <svg class="icon icon-lg">
                                                        <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-bell"></use>
                                                        </svg></a></li>
                                                <li class="nav-item"><a class="nav-link" href="libs/#">
                                                        <svg class="icon icon-lg">
                                                        <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-list-rich"></use>
                                                        </svg></a></li>
                                                <li class="nav-item"><a class="nav-link" href="libs/#">
                                                        <svg class="icon icon-lg">
                                                        <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-envelope-open"></use>
                                                        </svg></a></li>-->
                    </ul>
                    <ul class="header-nav ms-3">
                        <li class="nav-item dropdown"><a class="nav-link py-0" data-coreui-toggle="dropdown" href="libs/#" role="button" aria-haspopup="true" aria-expanded="false">
                                <div class="avatar avatar-md" style="background-color: rgb(235, 237, 239)">
                                    <!--<img class="avatar-img" src="libs/assets/img/avatars/8.jpg" alt="user@email.com">-->
                                    <svg class="sidebar-brand-narrow" width="46" height="46" alt="CoreUI Logo">
                                    <use xlink:href="libs/assets/brand/coreui.svg#signet"></use>
                                    </svg>
                                </div>
                            </a>
                            <div class="dropdown-menu dropdown-menu-end pt-0">
                                <div class="dropdown-header bg-light py-2">
                                    <div class="fw-semibold">Account</div>
                                </div>

                                <a class="dropdown-item" href="libs/#">
                                    <i class="fa fa-envelope-o" aria-hidden="true"></i> Messages<span class="badge badge-sm bg-success ms-2">42</span></a>
                                <a class="dropdown-item" href="libs/#">
                                    <i class="fa fa-tasks" aria-hidden="true"></i> Tasks<span class="badge badge-sm bg-danger ms-2">42</span></a>
                                <a class="dropdown-item" href="libs/#">
                                    <i class="fa fa-comments" aria-hidden="true"></i> Comments<span class="badge badge-sm bg-warning ms-2">42</span></a>
                                    <% if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
                                <div class="dropdown-header bg-light py-2">
                                    <div class="fw-semibold">Settings</div>
                                </div>
                                <a class="dropdown-item" href="render?page=page_log">
                                    <i class="fa fa-bug" aria-hidden="true"></i> Logs
                                </a>
                                <a class="dropdown-item" href="render?page=login">
                                    <i class="fa fa-user" aria-hidden="true"></i> Login</a>
                                <a class="dropdown-item" href="render?page=page_perfil">
                                    <i class="fa fa-users" aria-hidden="true"></i> Perfil</a>
                                <a class="dropdown-item" href="clientdb.jsp">
                                    <i class="fa fa-database" aria-hidden="true"></i> Cliente DB</a>
                                <a class="dropdown-item" href="monitoring" target="_blank">
                                    <i class="fa fa-area-chart" aria-hidden="true"></i> Monitoramento</a>
                                    <%}%>
                                <div class="dropdown-header bg-light py-2">
                                    <div class="fw-semibold">Perfil</div>
                                </div>
                                <span class="dropdown-item" href="#">
                                    <i class="fa fa-lock" aria-hidden="true"></i> <%=session.getAttribute("perfil")%></span>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="render?action=logout">
                                    <i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="header-divider"></div>
                <div class="container-fluid">
                    <% if (request.getAttribute("p") != null) {%>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb my-0 ms-2">
                            <% if (request.getAttribute("action") != null && !request.getAttribute("action").equals(Render.ACTION_BASE)) {%>
                            <li  class="breadcrumb-item">
                                <a class="text-decoration-none" href="render?page=<%=((Page) request.getAttribute("p")).getId()%>" id="bread_menu"><span><%=((Page) request.getAttribute("p")).getName()%></span></a>
                            </li>
                            <%} else {%>                            
                            <li  class="breadcrumb-item active">
                                <span><%=((Page) request.getAttribute("p")).getName()%></span>
                            </li>
                            <%}%>
                            <% if (request.getAttribute("action") != null && !request.getAttribute("action").equals(Render.ACTION_BASE)) {%>
                            <li  class="breadcrumb-item active">
                                <span><%=request.getAttribute("action")%></span>
                            </li>
                            <%}
                            %>
                        </ol>
                    </nav>
                    <%}
                    %>
                    <%@include file="components/header_config_page.jsp" %>
                </div>
            </header>
            <div class="body flex-grow-1 px-3">
                <div class="container-lg">
                    <%if (request.getParameter("page") != null && request.getParameter("page").equals("page_perfil")) {
                    %>
                    <%@include file="components/_perfil.jsp" %>
                    <%} else if (request.getParameter("page") != null && request.getParameter("page").equals("page_log")) {%>
                    <%@include file="components/_log.jsp" %>
                    <%} else {%>
                    <%@include file="components/_page.jsp" %>
                    <%}%>

                    <!-- /.row-->
                </div>
            </div>
            <footer class="footer">
                <div> © <a href="template.jsp"><%=request.getAttribute("projectName")%></a> <%=request.getAttribute("projectYear")%> - <%=request.getAttribute("projectVersion")%></div>
                <div class="ms-auto">Powered by&nbsp;<a href="#">LowcodeSystem</a></div>
            </footer>
        </div>
        <!-- CoreUI and necessary plugins-->
        <script src="libs/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
        <script src="libs/vendors/simplebar/js/simplebar.min.js"></script>
        <script src="libs/js/jquery.mask.min.js"></script>

        <script>
            <%=Render.project.getJsGeral()%>
        </script>         
        <%@include file="components/footer_config.jsp" %>
        <script src="libs/js/lowcodesystem.js"></script>
    </body>
</html>