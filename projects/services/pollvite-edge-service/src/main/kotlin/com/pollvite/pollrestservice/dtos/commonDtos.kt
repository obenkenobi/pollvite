package com.pollvite.pollrestservice.dtos

import com.pollvite.grpc.shared.IdPb
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