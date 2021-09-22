<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <h2>Register</h2>

        <form method="post" action="">
            <label>Code:</label><br>
            <input id="input" type="text" name="code"><br>

            <input type="submit" value="Senden">
        </form>

        <a href="/login">Login</a>
    </body>

    <p>${System.currentTimeMillis()}</p>

    <style>
        #input {
            width: 250px;
        }
    </style>
</html>
