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
    </head>
    <body>
        <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
            <form method="post" action="auth">
                <input type="hidden" id="action" name="action" value="login-admin"/>
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-md-6">
                            <div class="card mb-4 mx-4">
                                <div class="card-body p-4">
                                    <h1>Register</h1>
                                    <p class="text-medium-emphasis">Create your account</p>
                                    <div class="input-group mb-3"><span class="input-group-text">
                                            <svg class="icon">
                                            <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-user"></use>

                                            </svg></span>
                                        <input class="form-control" type="text" placeholder="Username">
                                    </div>
                                    <div class="input-group mb-3"><span class="input-group-text">
                                            <svg class="icon">
                                            <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-envelope-open"></use>
                                            </svg></span>
                                        <input class="form-control" type="text" placeholder="Email">
                                    </div>
                                    <div class="input-group mb-3"><span class="input-group-text">
                                            <svg class="icon">
                                            <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-lock-locked"></use>
                                            </svg></span>
                                        <input class="form-control" type="password" placeholder="Password">
                                    </div>
                                    <div class="input-group mb-4"><span class="input-group-text">
                                            <svg class="icon">
                                            <use xlink:href="libs/vendors/@coreui/icons/svg/free.svg#cil-lock-locked"></use>
                                            </svg></span>
                                        <input class="form-control" type="password" placeholder="Repeat password">
                                    </div>
                                    <div class="row">
                                        <div class="col-6">
                                            <button class="btn btn-block btn-light" type="button" onclick="history.go(-1)">Cancel</button>
                                        </div>
                                        <div class="col-6 text-end">
                                            <button class="btn btn-block btn-success" type="button">Create Account</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <!-- CoreUI and necessary plugins-->
            <script src="libs/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
            <script src="libs/vendors/simplebar/js/simplebar.min.js"></script>
            <script>
            </script>

    </body>
</html>