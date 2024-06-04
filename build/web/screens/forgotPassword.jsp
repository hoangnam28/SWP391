<!doctype html>
<html>
    <head>
        <meta charset='utf-8'>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css' rel='stylesheet'>
        <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <style>
            body {
                background-color: #f8f9fa;
                color: #495057;
                font-family: "Rubik", Helvetica, Arial, sans-serif;
                font-size: 14px;
                line-height: 1.5;
            }
            .forgot-container {
                max-width: 600px;
                margin: 0 auto;
                padding: 30px;
                background-color: #fff;
                border: 1px solid #dfdfdf;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
            .forgot-container h2 {
                margin-bottom: 20px;
            }
            .forgot-container ol {
                padding-left: 20px;
                margin-bottom: 20px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-control:focus {
                border-color: #76b7e9;
                box-shadow: 0 0 5px rgba(40, 167, 69, 0.25);
            }
            .alert {
                margin-bottom: 15px;
            }
            .card-footer {
                display: flex;
                justify-content: space-between;
            }
            .btn {
                padding: 10px 20px;
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <div class="forgot-container">
                <h2>Forgot your password?</h2>
                  <form class="card mt-4" action="forgot-password" method="POST">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="email-for-pass">Enter your email address</label>
                            <input class="form-control" type="email" name="email" id="email-for-pass" required>
                            <small class="form-text text-muted">Enter the registered email address. Then we'll email an OTP to this address.</small>
                        </div>
                        <div class="form-group">
                            <!-- Hi?n th? thông báo n?u có -->
                            <c:if test="${not empty message}">
                                <div class="alert alert-danger">${message}</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn-success" type="submit">Get New Password</button>
                        <a class="btn btn-danger" href="./home_page">Back to Login</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script type='text/javascript' src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
</body>
</html>
