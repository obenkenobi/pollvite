package com.pollvite.identityservice.services

import com.pollvite.grpc.user.UserPrivateProfilePb
import com.pollvite.grpc.user.UserPublicProfilePb
import com.pollvite.grpc.user.UserSignUpPb
import com.pollvite.grpc.user.UserUpdatePb
import com.pollvite.identityservice.configuration.JobProps
import com.pollvite.identityservice.models.UserProfile
import com.pollvite.identityservice.repositories.UserProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {
    fun getUserProfileByUUID(uuid: String): UserPrivateProfilePb
    fun getUserProfileByPublicId(publicId: String): UserPublicProfilePb
    fun getUserProfileByPublicIds(publicIds: List<String>): List<UserPublicProfilePb>

    fun signUpUser(request: UserSignUpPb): UserPrivateProfilePb
    fun updateUser(request: UserUpdatePb): UserPrivateProfilePb
    fun beginDeleteUserByUUID(uuid: String): UserPrivateProfilePb
    fun doUserSyncActions()
}

@Service
class UserServiceImpl(@Autowired private val userProfileRepository: UserProfileRepository,
                      @Autowired private val JobProps: JobProps): UserService {
    override fun getUserProfileByUUID(uuid: String): UserPrivateProfilePb {
        TODO("Not yet implemented")
    }

    override fun getUserProfileByPublicId(publicId: String): UserPublicProfilePb {
        TODO("Not yet implemented")
    }

    override fun getUserProfileByPublicIds(publicIds: List<String>): List<UserPublicProfilePb> {
        TODO("Not yet implemented")
    }

    override fun signUpUser(request: UserSignUpPb): UserPrivateProfilePb {
        TODO("Not yet implemented")
    }

    override fun updateUser(request: UserUpdatePb): UserPrivateProfilePb {
        TODO("Not yet implemented")
    }

    override fun beginDeleteUserByUUID(uuid: String): UserPrivateProfilePb {
        TODO("Not yet implemented")
    }

    override fun doUserSyncActions() {
        TODO("Not yet implemented")
    }

    private fun createUserProfilePbFull(userProfile: UserProfile): UserPrivateProfilePb {
        TODO("Not yet implemented")
    }

    private fun getProfileByPublicIdRequired(publicId: String): UserProfile {
        TODO("Not yet implemented")
    }

}