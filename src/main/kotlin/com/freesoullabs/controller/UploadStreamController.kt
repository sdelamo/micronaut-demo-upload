package com.freesoullabs.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.StreamingFileUpload
import org.slf4j.LoggerFactory
import io.micronaut.http.MediaType
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Controller("/upload-stream")
class UploadStreamController {

  private val logger = LoggerFactory.getLogger(UploadStreamController::class.java)

  @Post(consumes = [MediaType.MULTIPART_FORM_DATA], produces = [MediaType.TEXT_PLAIN])
  fun uploadStreamingMulti(data: Publisher<StreamingFileUpload>): HttpResponse<String> {
    data.subscribe(object : Subscriber<StreamingFileUpload>{
      private var s: Subscription? = null

      override fun onSubscribe(subscription: Subscription) {
        logger.info("onSubscribe: {}", subscription)
        this.s = subscription
        subscription.request(1)
      }

      override fun onNext(streamingFileUpload: StreamingFileUpload) {
        logger.debug("onNext: {}", streamingFileUpload)
        s?.request(1)
      }

      override fun onError(t: Throwable) {
        logger.error("onError: ", t)
      }

      override fun onComplete() {
        logger.info("onComplete")
      }
    })

    // Sleep for 2 seconds to simulate processing delay
    Thread.sleep(2000)

    logger.debug("Exitting...")

    return HttpResponse.ok("Streaming upload processed successfully.")
  }

}