package com.freesoullabs.controller

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Part
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.serde.annotation.Serdeable
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory
import java.nio.file.Paths

@Serdeable
data class UploadResponse(
  val message: String,
  val filename: String,
  val path: String?
)

@Controller(value = "upload")
class UploadFileController(@Value("\${file-upload.directory}") private val uploadDir: String) {

  private val logger = LoggerFactory.getLogger(UploadFileController::class.java)

  @Post(consumes = [MediaType.MULTIPART_FORM_DATA])
  fun uploadFile(@Part file: CompletedFileUpload): HttpResponse<UploadResponse> {
    val safeFilename = FilenameUtils.getName(file.filename)
    val destination = Paths.get(uploadDir, safeFilename).toFile()
    destination.parentFile.mkdirs()

    return try {
      FileUtils.copyInputStreamToFile(file.inputStream, destination)
      logger.info("File uploaded: {}", destination.absolutePath)
      HttpResponse.created(
        UploadResponse("File uploaded successfully", safeFilename, destination.absolutePath)
      )
    } catch (e: Exception) {
      logger.error("File upload failed: {}", e.message)
      HttpResponse.serverError(
        UploadResponse("File upload failed: ${e.message}", safeFilename, null)
      )
    }
  }
}