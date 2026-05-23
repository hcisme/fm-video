package io.github.hcisme.fmvideo.common.entity.vo

data class ResponseVO<T>(
    var status: String? = null,
    var code: Int? = null,
    var info: String? = null,
    var data: T? = null
)
