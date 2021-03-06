package org.zalando.react.nakadi.properties

import org.zalando.react.nakadi.NakadiMessages._
import org.zalando.react.nakadi.commit.handlers.BaseCommitManager

import scala.concurrent.duration._
import scala.language.postfixOps


object ConsumerProperties {

  def apply(serverProperties: ServerProperties,
            tokenProvider: Option[() => String],
            eventType: String,
            groupId: String,
            partition: String,
            commitHandler: BaseCommitManager): ConsumerProperties = {
    new ConsumerProperties(
      serverProperties = serverProperties,
      tokenProvider = tokenProvider,
      eventType = eventType,
      groupId = groupId,
      partition = partition,
      commitHandler = commitHandler
    )
  }
}

case class ConsumerProperties(
  serverProperties: ServerProperties,
  tokenProvider: Option[() => String],
  eventType: String,
  groupId: String,
  partition: String,
  commitHandler: BaseCommitManager,
  offset: Option[Offset] = None,
  commitInterval: FiniteDuration = 30.seconds,
  batchLimit: Int = 0,
  batchFlushTimeoutInSeconds: FiniteDuration = 30.seconds,
  streamLimit: Int = 0,
  streamTimeoutInSeconds: FiniteDuration = 0.seconds,
  streamKeepAliveLimit: Int = 0,
  pollParallelism: Int = 0,
  staleLeaseDelta: FiniteDuration = 300.seconds,
  leaseHolder: String = "test-lease-holder" // FIXME - set this to make sense
) {

  /**
    * Use custom interval for auto-commit or commit flushing on manual commit.
    */
  def commitInterval(time: FiniteDuration): ConsumerProperties =
    this.copy(commitInterval = time)

  def readFromStartOfStream(): ConsumerProperties =
    this.copy(offset = Some(BeginOffset))

}
