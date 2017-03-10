package edu.hku.cs.network

import edu.hku.cs.Optimization.RuleCollector.RuleSet

/**
  * Created by jianyu on 3/8/17.
  */
trait EndPoint {
  val id: String
  // def onReceive(message: Message): Unit
  def receiveAndReply(message: Message): Message
  def send(obj: Message): Unit
  def onRegister()
}

trait EndpointRegister {
  def register(endPoint: EndPoint): Unit = {
    endPoint.onRegister()
  }
  def run(): Unit

  def stop(): Unit
}

trait NettyHandle {
  def sendMsg(obj: Any): Unit
}

abstract class NettyEndpoint extends EndPoint {

  private var nettyHandle: NettyHandle = _

  override def send(obj: Message): Unit = {
    nettyHandle.sendMsg(BoxMessage(this.id, obj))
  }

  def setHandle(_nettyHandle: NettyHandle): Unit = {
    nettyHandle = _nettyHandle
  }
}

sealed trait Message

case class BoxMessage(endpointId: String, message: Message)

case class onStart(int: Int, string: String, double: Double) extends Message

case class onStarted(int: Int) extends Message

case class onEnded(int: Int) extends Message

case class EndpointError(string: String) extends Message

case class RuleRegister(bool: Boolean) extends Message

case class RuleRegistered(bool: Boolean) extends Message

case class RuleInfered(id: Int, ruleSet: RuleSet) extends Message

case class RuleAdded(id: Int) extends Message