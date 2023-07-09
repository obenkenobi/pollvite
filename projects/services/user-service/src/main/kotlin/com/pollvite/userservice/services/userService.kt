package com.pollvite.userservice.services

import com.pollvite.grpc.user.UserProfileCreatePb
import com.pollvite.grpc.user.UserProfileFullPb
import com.pollvite.grpc.user.UserProfilePublicPb
import com.pollvite.grpc.user.UserProfileUpdatePb
import com.pollvite.userservice.configuration.JobProps
import com.pollvite.userservice.repositories.UserProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
        TODO("Not yet implemented")
    }

    override fun updateUserProfile(request: UserProfileUpdatePb): UserProfileFullPb {
        TODO("Not yet implemented")
    }

    override fun beginDeleteUserByUUID(uuid: String): UserProfileFullPb {
        TODO("Not yet implemented")
    }

    override fun doUserSyncActions() {
        val profiles = userProfileRepository.getActionableBatch(
            this.JobProps.userProfileBatchNumber,
            this.JobProps.userProfileBatchCount)
        TODO("Not yet implemented")
    }

}