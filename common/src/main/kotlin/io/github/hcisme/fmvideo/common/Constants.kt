package io.github.hcisme.fmvideo.common

object Constants {
    object Admin {
        private const val REDIS_KEY_PREFIX = "fmvideo-admin"

        const val TOKEN_KEY = "token"

        /**
         * redis token key
         */
        const val REDIS_KEY_TOKEN = "$REDIS_KEY_PREFIX:$TOKEN_KEY:"

        /**
         * 单位 ms
         *
         * 一分钟
         */
        const val REDIS_TIME_1MIN = 60000L

        /**
         * 单位 ms
         *
         * 一天
         */
        const val REDIS_KEY_EXPIRES_ONE_DAY = REDIS_TIME_1MIN * 60 * 24

        /**
         * token 失效时间 15天 单位 ms
         */
        const val REDIS_KEY_TOKEN_EXPIRES_15_DAY = REDIS_KEY_EXPIRES_ONE_DAY * 15
    }
}
