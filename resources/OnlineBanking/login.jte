<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <h2>Login</h2>

        <form method="post" action="/sendlogin">
            <label>PlayerName:</label><br>
            <input id="input" type="text" name="name"><br>

            <label>Password:</label><br>
            <input type="text" name="password"><br><br>

            <input type="submit" value="Senden">
        </form>

        <a href="/register">Verify</a>
    </body>

    <p>${System.currentTimeMillis()}</p>

    <style>
        #input {
            width: 250px;
        }
    </style>
</html>
