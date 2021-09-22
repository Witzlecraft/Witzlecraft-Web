<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    </head>

    <body>
        <h2>Überweisung</h2>

        <form method="post" action="/money-transfer">
            <label>PlayerUUID:</label><br>
            <input id="input" type="text" name="uuid"><br>

            <label>Geld:</label><br>
            <input type="text" name="money"><br><br>

            <input type="submit" value="Senden">
        </form> 
    </body>

    <p>${System.currentTimeMillis()}</p>


</html>
