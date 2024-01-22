package com.pollvite.identityservice.services

import com.pollvite.grpc.user.UserProfileCreatePb
import com.pollvite.grpc.user.UserProfileFullPb
import com.pollvite.grpc.user.UserProfilePublicPb
import com.pollvite.grpc.user.UserProfileUpdatePb
import com.pollvite.identityservice.configuration.JobProps
import com.pollvite.identityservice.models.UserProfile
import com.pollvite.identityservice.repositories.UserProfileRepository
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.random.Random

interface UserService {
    fun getUserProfileByUUID(uuid: String): UserProfileFullPb
    fun getUserProfileByPublicId(publicId: String): UserProfilePublicPb
    fun getUserProfileByPublicIds(publicIds: List<String>): List<UserProfilePublicPb>
    fun createUserProfile(request: UserProfileCreatePb): UserProfileFullPb
    fun updateUserProfile(request: UserProfileUpdatePb): UserProfileFullPb
    fun beginDeleteUserByUUID(uuid: String): UserProfileFullPb
    fun doUserSyncActions()
}

@Service
class UserServiceImpl(@Autowired private val userProfileRepository: UserProfileRepository,
                      @Autowired private val JobProps: JobProps): UserService {
    override fun getUserProfileByUUID(uuid: String): UserProfileFullPb {
        TODO("Not yet implemented")
    }

    override fun getUserProfileByPublicId(publicId: String): UserProfilePublicPb {
        TODO("Not yet implemented")
    }

    override fun getUserProfileByPublicIds(publicIds: List<String>): List<UserProfilePublicPb> {
        TODO("Not yet implemented")
    }

    override fun createUserProfile(request: UserProfileCreatePb): UserProfileFullPb {
        // Todo: add business rules (username not blank or taken, uuid not blank)
        val userProfile = UserProfile(
            publicId = null,
            uuid = request.uuid,
            userName = request.userName,
            timestamps = Timestamps.create(),
            batchIndex = Random.nextLong(),
            fbSynced = false,
            action = UserProfile.Action.CREATED
        )

        val userProfileSaved = userProfileRepository.save(userProfile)
        return createUserProfilePbFull(userProfileSaved)
    }

    override fun updateUserProfile(request: UserProfileUpdatePb): UserProfileFullPb {
        // Todo: add business rules (username not blank or taken, uuid not blank)
        TODO("Not yet implemented")
    }

    override fun beginDeleteUserByUUID(uuid: String): UserProfileFullPb {
        TODO("Not yet implemented")
    }

    override fun doUserSyncActions() {
        TODO("Not yet implemented")
    }

    private fun createUserProfilePbFull(userProfile: UserProfile): UserProfileFullPb {
        return UserProfileFullPb.newBuilder().also {
            it.publicId = userProfile.publicId
            it.uuid = userProfile.uuid
            it.userName = userProfile.userName
        }.build()
    }

    private fun getProfileByPublicIdRequired(publicId: String): UserProfile {
        val profile = userProfileRepository.findById(publicId)
        if (profile.isEmpty) {
            throw AppException(ErrorStatus.NOT_FOUND)
        }
        return profile.get()
    }

}