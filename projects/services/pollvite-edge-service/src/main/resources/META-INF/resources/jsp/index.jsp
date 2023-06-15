<%@ include file = "meta/index.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="fragments/head.jsp" %>
<body>
    <h1>Login ID Token:</h1>
    <p id="token"></p>
    <h1>CSRF Token:</h1>
    <p id="csrfToken"></p>
    <button id="login" class="pollvite-button">Login</button>
    <p id="err"></p>

    <%@include file="fragments/scripts.jsp" %>
    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-app.js";
        import { getAuth, GoogleAuthProvider, signInWithPopup  } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-auth.js";

        function getCsrfToken() {
            return  document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
        }
        $("#csrfToken").innerText = getCsrfToken()

        fetch("api/conf/fb/web").then(res => res.json()).then(firebaseConfig => {
            const app = initializeApp(firebaseConfig);
            const auth = getAuth(app);
            auth.languageCode = 'it';

            const provider = new GoogleAuthProvider();

            const signInFunc = () => {
                signInWithPopup(auth, provider)
                    .then(() => auth.currentUser.getIdToken(/* forceRefresh */ true))
                    .then((idToken) => {
                        $("#token").innerText = idToken
                        return auth.signOut()
                    })
                    .then(() => console.log("Done!"))
                    .catch((error) => {
                        console.error(error)
                        $("#error").innerText = error.code + "::" + error.message
                    });
            }
            $("#login").click(signInFunc)
        })
    </script>
</body>
</html>