<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Pollvite</title>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body>
        <h1>Token:</h1>
        <p id="token"></p>
        <button id="login">Login</button>
        <p id="err"></p>

        <script type="module">
            import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-app.js";
            import { getAuth, GoogleAuthProvider, signInWithPopup  } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-auth.js";

            fetch("/fb/fbConf").then(res => res.json()).then(firebaseConfig => {
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