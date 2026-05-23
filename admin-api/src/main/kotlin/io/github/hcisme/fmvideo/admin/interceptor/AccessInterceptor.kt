package io.github.hcisme.fmvideo.admin.interceptor

import io.github.hcisme.fmvideo.admin.annotation.Access
import io.github.hcisme.fmvideo.admin.redis.getUserInfoByToken
import io.github.hcisme.fmvideo.common.entity.enums.ResponseCodeEnum
import io.github.hcisme.fmvideo.common.exception.BusinessException
import io.github.hcisme.fmvideo.common.redis.RedisUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AccessInterceptor(
    private val redisUtils: RedisUtils
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        try {
            if (handler !is HandlerMethod) return true

            val method = handler.method
            if (method.isAnnotationPresent(Access::class.java)) {
                val access = method.getAnnotation(Access::class.java)
                return checkAccess(access.isRequiredLoginAccess)
            }

            return true
        } catch (e: BusinessException) {
            throw e
        } catch (e: Throwable) {
            throw e
        }
    }

    private fun checkAccess(isRequiredLoginAccess: Boolean): Boolean {
        if (!isRequiredLoginAccess) {
            return true
        }

        val attributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes? ?: return false
        val token = attributes.request.getHeader("token") ?: throw BusinessException(ResponseCodeEnum.CODE_401)
        redisUtils.getUserInfoByToken(token)
            ?: throw BusinessException(ResponseCodeEnum.CODE_401)
        return true
    }
}