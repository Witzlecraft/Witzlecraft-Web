@param de.morrisbr.witzlecraft.pages.RegisterPage page

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <h2>Willkommen ${page.getPlayer().getName()}</h2><br>

        <h3>Name: ${page.getPlayer().getName()}</h3><br>
        <h3>UUID: ${page.getPlayer().getUuid()}</h3><br>
        <h3>Code: ${page.getCode()}</h3><br>

        <h1>Code: ${page.getCode()} is now DELETED!</h1><br>
    </body>
</html>
