<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>
</head>
<body>
<img src="assets/logo.png" alt="logo"/>
<div style="margin-top: 10px">Greetings, ${user.email}</div>
<div>Your email is <b>${user.email}</b></div>
<br/>
<div>To activate an account <a href="http://localhost:3000/activate-user?userId=${user.id}&token=${token.token}">click
        here</a></div>
<br/>
<div> Have a nice day..!</div>
</body>
</html>