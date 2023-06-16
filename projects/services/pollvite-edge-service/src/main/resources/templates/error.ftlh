<#-- @ftlvariable name="status" type="java.lang.Number" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Pollvite</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css"
          href="<@spring.url '/css/styles.css'/>"/>
    <style></style>
</head>
<body>
<h1>${status}</h1>
<h2>${message}</h2>
</body>
</html>