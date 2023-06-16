import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-app.js";
import { getAuth, GoogleAuthProvider, signInWithPopup  } from "https://www.gstatic.com/firebasejs/9.22.2/firebase-auth.js";

async function initAsync() {
    await helpers.csrfWrapper(csrf => $("#csrfToken").text(csrf))
    const fbConf = await fetch(helpers.absoluteUrl("api/conf/fb/web")).then(res => res.json())

    const app = initializeApp(fbConf);
    const auth = getAuth(app);
    auth.languageCode = 'it';

    const provider = new GoogleAuthProvider();

    const signInFuncAsync = async () => {
        try {
            await signInWithPopup(auth, provider)
            const idToken = await auth.currentUser.getIdToken(/* forceRefresh */ true)
            $("#token").text(idToken)
            try {
                await helpers.csrfWrapper(csrf => {
                    return fetch(helpers.absoluteUrl("/api/auth/login"), {
                        method: "POST",
                        headers: {"X-XSRF-TOKEN": csrf, "Content-Type": "application/json",},
                        body: JSON.stringify({token: idToken})
                    })
                })
            } finally {
                await auth.signOut()
            }
            console.log("Done!")
        } catch (error) {
            console.error(error)
            $("#err").text(error.code + "::" + error.message)
        }
    }
    $("#login").click(signInFuncAsync)
}

export default function () {
    $(document).ready(function () { initAsync().then() })
}