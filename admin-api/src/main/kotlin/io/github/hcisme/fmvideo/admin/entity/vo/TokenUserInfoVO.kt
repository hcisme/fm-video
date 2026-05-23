package io.github.hcisme.fmvideo.admin.entity.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class TokenUserInfoVO : Serializable {
    var email: String? = null
    var token: String? = null
}
