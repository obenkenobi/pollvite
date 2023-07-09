package com.pollvite.userservice.services

import com.google.cloud.storage.Acl.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.pollvite.grpc.user.UserProfileCreatePb
import com.pollvite.grpc.user.UserProfileFullPb
import com.pollvite.grpc.user.UserProfilePublicPb
import com.pollvite.grpc.user.UserProfileUpdatePb
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import com.pollvite.userservice.configuration.JobProps
import com.pollvite.userservice.models.UserProfile
import com.pollvite.userservice.repositories.UserProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
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
@DependsOn("firebaseApp")
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
        val profiles = userProfileRepository.getActionableBatch(
            this.JobProps.userProfileBatchNumber,
            this.JobProps.userProfileBatchCount)
        profiles.forEach { profile ->
            val fbAuth = if (profile.action != UserProfile.Action.NONE) FirebaseAuth.getInstance() else return

            // Todo: send message to sync user data
            if (profile.action == UserProfile.Action.CREATED || profile.action == UserProfile.Action.UPDATE) {
                val fbSynced = try {
                    if (!profile.fbSynced) {
                        fbAuth.setCustomUserClaims(profile.uuid, mapOf("userName" to profile.userName))
                        true
                    } else { false }
                } catch (_: FirebaseAuthException) {
                    false
                }
                val profileToSave = profile.copy(action = UserProfile.Action.NONE, fbSynced = fbSynced)
                userProfileRepository.save(profileToSave)
            } else if (profile.action == UserProfile.Action.DELETE) {
                if (!profile.fbSynced) {
                    try { fbAuth.deleteUser(profile.uuid) }
                    catch (_: FirebaseAuthException) {}
                }
                userProfileRepository.deleteById(profile.publicId!!)
            }
        }
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