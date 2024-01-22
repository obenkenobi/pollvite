package com.pollvite.polledgeservice.security

class Credentials(
    val type: Type,
    decodedToken: HashMap<String, Any>,
    session: String
) {
    enum class Type {ID_TOKEN, SESSION}
}