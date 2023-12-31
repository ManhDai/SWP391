<%-- 
    Document   : login
    Created on : May 30, 2023, 11:00:58 AM
    Author     : Trung Kien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>V-HomeClean - Login Admin</title>

        <link rel="shortcut icon" type="image/x-icon" href="css/assets/img/icon.png">

        <link rel="stylesheet" href="css/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="css/assets/css/font-awesome.min.css">

        <link rel="stylesheet" href="css/assets/css/feathericon.min.css">

        <link rel="stylesheet" href="css/assets/plugins/morris/morris.css">

        <link rel="stylesheet" href="css/assets/css/style.css">
    </head>
    <body>
        <div class="main-wrapper login-body">
            <div class="login-wrapper">
                <div class="container">
                    <div class="loginbox">
                        <div class="login-right">
                            <div class="login-right-wrap">
                                <h1>Đăng Nhập</h1>
                                <p style="color:red;">${ERROR}</p>
                                <p class="account-subtitle">Truy cập vào bảng điều khiển</p>
                                <form action="../DashboardLoginController" method="post">
                                    <div class="form-group">
                                        <input name="email" class="form-control" type="text" placeholder="Email">
                                    </div>
                                    <div class="form-group">
                                        <input name="password" class="form-control" type="password" placeholder="Mật Khẩu">
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" class="btn btn-primary btn-block" name="action" value="Đăng Nhập" />
                                    </div>
                                </form>
                                <div class="text-center forgotpass"><a href="../HomePageController">Forgot Password?</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="css/assets/js/jquery-3.6.0.min.js"></script>

        <script src="css/assets/js/bootstrap.bundle.min.js"></script>

        <script src="css/assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>

        <script src="css/assets/js/script.js"></script>
    </body>
</html>
