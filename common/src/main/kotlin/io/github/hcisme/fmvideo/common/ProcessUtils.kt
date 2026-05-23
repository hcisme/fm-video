package io.github.hcisme.fmvideo.common

import io.github.hcisme.fmvideo.common.exception.BusinessException
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.*

object ProcessUtils {
    private val logger = LoggerFactory.getLogger(ProcessUtils::class.java)

    private val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    @Throws(BusinessException::class)
    fun executeCommand(cmd: String, showLog: Boolean = false): String {
        val runtime = Runtime.getRuntime()
        var process: Process? = null
        try {
            // 判断操作系统，执行ffmpeg指令
            process = if (osName.contains("win")) Runtime.getRuntime().exec(cmd) else Runtime.getRuntime()
                .exec(arrayOf("/bin/sh", "-c", cmd))

            // 取出输出流和错误流的信息
            // 注意：必须要取出ffmpeg在执行命令过程中产生的输出信息，
            // 如果不取的话当输出流信息填满jvm存储输出留信息的缓冲区时，线程就回阻塞住
            val errorStream = PrintStream(process.errorStream)
            val inputStream = PrintStream(process.inputStream)
            errorStream.start()
            inputStream.start()

            // 等待ffmpeg命令执行完
            process.waitFor()

            // 获取执行结果字符串
            val result = buildString {
                append(errorStream.stringBuffer)
                append(inputStream.stringBuffer.toString())
                append("\n")
            }

            // 输出执行的命令信息
            if (showLog) {
                logger.info("执行命令：{}\n结果：{}", cmd, result)
            }
            return result
        } catch (e: Exception) {
            logger.error("执行命令失败cmd：{}\n失败：{} ", cmd, e.message)
            throw BusinessException("ffmpeg 转换失败")
        } finally {
            process?.let {
                val ffmpegKiller = ProcessKiller(it)
                runtime.addShutdownHook(ffmpegKiller)
            }
        }
    }

    /**
     * 在程序退出前结束已有的FFmpeg进程
     */
    private class ProcessKiller(private val process: Process) : Thread() {
        override fun run() {
            process.destroy()
        }
    }

    /**
     * 用于取出ffmpeg线程执行过程中产生的各种输出和错误流的信息
     */
    private class PrintStream(private val inputStream: InputStream? = null) : Thread() {
        var stringBuffer = StringBuffer()
        override fun run() {
            inputStream?.let {
                try {
                    it.bufferedReader().use { reader ->
                        reader.lineSequence().forEach { line ->
                            stringBuffer.append(line)
                        }
                    }
                } catch (e: Exception) {
                    logger.error("读取输入流出错！错误信息：${e.message}", e)
                }
            }
        }
    }
}
