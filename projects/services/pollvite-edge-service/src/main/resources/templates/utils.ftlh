<#import "spring.ftl" as spring />
<#macro page>
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
        <#nested>
    </body>
    </html>
</#macro>