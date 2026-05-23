package io.github.hcisme.fmvideo.admin.redis

import io.github.hcisme.fmvideo.admin.entity.vo.TokenUserInfoVO
import io.github.hcisme.fmvideo.common.Constants
import io.github.hcisme.fmvideo.common.redis.RedisUtils
import java.util.*

/**
 * 保存登录信息到redis
 */
fun RedisUtils.saveTokenUserInfo(tokenUserInfoVO: TokenUserInfoVO) {
    val token = UUID.randomUUID().toString()
    tokenUserInfoVO.apply {
        this@apply.token = token

        this@saveTokenUserInfo.setValueAndExpire(
            Constants.Admin.REDIS_KEY_TOKEN + token,
            this@apply,
            Constants.Admin.REDIS_KEY_TOKEN_EXPIRES_15_DAY
        )
    }
}

/**
 * 通过 `token` 获取用户信息
 */
fun RedisUtils.getUserInfoByToken(token: String?): TokenUserInfoVO? {
    return this.getValue(Constants.Admin.REDIS_KEY_TOKEN + token) as TokenUserInfoVO?
}

/**
 * 通过 token 清空指定用户redis里的 `token` 信息
 */
fun RedisUtils.cleanTokenByToken(token: String?) {
    this.delete(Constants.Admin.REDIS_KEY_TOKEN + token)
}
