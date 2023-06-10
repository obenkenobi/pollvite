package com.pollvite.polledgeservice.security

import com.google.firebase.auth.FirebaseToken

class Credentials(
    val type: Type, decodedToken:
    FirebaseToken, session: String
) {
    enum class Type {ID_TOKEN, SESSION}
}