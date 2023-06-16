<#import "utils.ftl" as u />
<#import "spring.ftl" as spring />

<@u.page>
    <h1>Login ID Token:</h1>
    <p id="token"></p>
    <h1>CSRF Token:</h1>
    <p id="csrfToken"></p>
    <button id="login" class="pollvite-button">Login</button>
    <p id="err"></p>
    <script type="module">
        import initIndexPage from "<@spring.url '/js/index-page.js'/>"
        initIndexPage()
    </script>
</@u.page>