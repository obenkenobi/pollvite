package com.pollvite.pollrestservice.dtos

import com.pollvite.grpc.shared.AuditPb
import com.pollvite.grpc.shared.IdPb
import com.pollvite.grpc.shared.TimestampsPb
import javax.validation.constraints.NotNull

data class IdDto(@field:NotNull val value: String?) {
    companion object {
      fun fromPb(idPb: IdPb) : IdDto {
          return IdDto(value = idPb.value)
      }
    }
    
    fun toPb(): IdPb {
        return IdPb.newBuilder().also {
            it.value = value
        }.build()
    }
}

data class TimestampsDto(val createdAt: Long, val updatedAt: Long) {
    companion object {
        fun fromPb(timestampsPb: TimestampsPb) : TimestampsDto {
            return TimestampsDto(createdAt = timestampsPb.createdAt, updatedAt = timestampsPb.updatedAt)
        }
    }

    fun toPb(): TimestampsPb {
        return TimestampsPb.newBuilder().also {
            it.createdAt = createdAt
            it.updatedAt = updatedAt
        }.build()
    }
}

data class AuditDto(val createdBy: String, val updatedBy: String) {
    companion object {
        fun fromPb(pb: AuditPb) : AuditDto {
            return AuditDto(createdBy = pb.createdBy, updatedBy = pb.updatedBy)
        }
    }

    fun toPb(): AuditPb {
        return AuditPb.newBuilder().also {
            it.createdBy = createdBy
            it.createdBy = createdBy
        }.build()
    }
}