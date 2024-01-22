<#import "utils.ftl" as u />
<#import "spring.ftl" as spring />

<@u.page>
    <h1>CSRF Token:</h1>
    <p id="csrfToken"></p>
    <button id="login" class="pollvite-button">Login</button>
    <p id="err"></p>
    <script>
        async function initAsync() {
            await helpers.csrfWrapper(csrf => $("#csrfToken").text(csrf))
        }

        $(document).ready(function () { initAsync().then() })
    </script>
</@u.page>