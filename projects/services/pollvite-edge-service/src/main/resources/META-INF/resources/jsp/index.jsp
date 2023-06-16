<%@ include file = "meta/index.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="fragments/head.jsp" %>
<body>
    <%@include file="fragments/navbar.jsp" %>

    <h1>Login ID Token:</h1>
    <p id="token"></p>
    <h1>CSRF Token:</h1>
    <p id="csrfToken"></p>
    <button id="login" class="pollvite-button">Login</button>
    <p id="err"></p>

    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-app.js";
        import { getAuth, GoogleAuthProvider, signInWithPopup  } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-auth.js";

        function getCsrfTokenCookie() {
            return  document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
        }
        async function getCsrfToken() {
            const cookie = getCsrfTokenCookie()
            if (!cookie) {
                return fetch("/api/auth/csrf").then(() => getCsrfTokenCookie())
            }
            return cookie
        }

        async function csrfWrapper(csrfSupplier) {
            return getCsrfToken().then(csrf => csrfSupplier(csrf))
        }

        async function init() {
            await csrfWrapper(csrf => $("#csrfToken").text(csrf))
            const fbConf = await fetch("api/conf/fb/web").then(res => res.json())

            const app = initializeApp(fbConf);
            const auth = getAuth(app);
            auth.languageCode = 'it';

            const provider = new GoogleAuthProvider();

            const signInFuncAsync = async () => {
                try {
                    await signInWithPopup(auth, provider)
                    const idToken = await auth.currentUser.getIdToken(/* forceRefresh */ true)
                    $("#token").text(idToken)
                    await csrfWrapper(csrf => {
                        return fetch("/api/auth/login", {
                            method: "POST",
                            headers: {"X-XSRF-TOKEN": csrf, "Content-Type": "application/json",},
                            body: JSON.stringify({token: idToken})
                        })
                    })
                    await auth.signOut()
                    console.log("Done!")
                } catch (error) {
                    console.error(error)
                    $("#err").text(error.code + "::" + error.message)
                }
            }
            $("#login").click(signInFuncAsync)
        }
        $(document).ready(function () {
            init().then()
        })
    </script>
</body>
</html>