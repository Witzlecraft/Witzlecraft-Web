@param de.morrisbr.witzlecraft.pages.RegisterPage page

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <h2>Register</h2>

        <form method="post" action="/verifyregister?key=${page.getCode()}">
            <label>Passwort:</label><br>
            <input type="text" name="password"><br><br>

            <input type="submit" value="Senden">
        </form>
    </body>

    <p>${System.currentTimeMillis()}</p>

    <style>
        #input {
            width: 250px;
        }
    </style>
</html>
