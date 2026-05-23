package io.github.hcisme.fmvideo.admin.entity.query

open class BaseQuery {
    var simplePage: SimplePage? = null
    var page: Int? = null
    var pageSize: Int? = null
    var orderBy: String? = null
}
