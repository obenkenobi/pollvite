<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Books</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <h1>Login ID Token:</h1>
    <p id="token"></p>
    <h1>CSRF Token:</h1>
    <p id="csrfToken"></p>
    <button id="login" class="button">Login</button>
    <p id="err"></p>

    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-app.js";
        import { getAuth, GoogleAuthProvider, signInWithPopup  } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-auth.js";

        function getCsrfToken() {
            return  document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
        }

        document.getElementById("csrfToken").innerText = getCsrfToken()

        fetch("api/conf/fb/web").then(res => res.json()).then(firebaseConfig => {
            const app = initializeApp(firebaseConfig);
            const auth = getAuth(app);
            auth.languageCode = 'it';

            const provider = new GoogleAuthProvider();

            const signInFunc = () => {
                signInWithPopup(auth, provider)
                    .then(() => auth.currentUser.getIdToken(/* forceRefresh */ true))
                    .then((idToken) => {
                        document.getElementById("token").innerText = idToken
                        return auth.signOut()
                    })
                    .then(() => console.log("Done!"))
                    .catch((error) => {
                        console.error(error)
                        document.getElementById("error").innerText = error.code + "::" + error.message
                    });
            }
            document.getElementById("login").addEventListener('click', signInFunc)
        })
    </script>
</body>
</html>