package io.github.hcisme.fmvideo.admin.entity

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ConfigurationProperties(prefix = "project")
class AppConfig {
    val storage = Storage()
    val ffmpeg = FFmpeg()

    class Storage {
        lateinit var baseFolder: String
        lateinit var fileFolder: String
        lateinit var videoFolder: String
        lateinit var tempFolder: String

        val finalVideoPath: String
            get() = File(File(baseFolder, fileFolder), videoFolder).absolutePath

        val finalTempPath: String
            get() = File(File(baseFolder, fileFolder), tempFolder).absolutePath
    }

    class FFmpeg {
        lateinit var path: String
        lateinit var ffprobePath: String
        var showLog: Boolean = false
    }
}