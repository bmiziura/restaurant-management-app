<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>
    <style>
        <#include "assets/style.css">
    </style>
</head>
<body>
<div class="container">
    <#include "header.ftl">

    <div class="content">
        <h1>Odzyskiwanie konta w Restauratorze!</h1>
        <p>
            Kliknij link poniżej, aby zmienić hasło:<br/>
            <a href="http://localhost:3000/password-recovery?email=${user.email}&token=${token.token}">
                http://localhost:3000/password-recovery?email=${user.email}&token=${token.token}
            </a>
        </p>
    </div>

    <#include "footer.ftl">
</div>
</body>
</html>