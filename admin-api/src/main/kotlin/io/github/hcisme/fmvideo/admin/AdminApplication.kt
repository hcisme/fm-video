package io.github.hcisme.fmvideo.admin

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "io.github.hcisme.fmvideo.admin",
        "io.github.hcisme.fmvideo.common"
    ]
)
@MapperScan("io.github.hcisme.fmvideo.admin.mappers")
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
