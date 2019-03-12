
/**
  * Created by qijianpeng on 2019/3/12.
  * mail: jianpengqi@126.com
  */
import java.lang.reflect.{Method, InvocationHandler, Proxy}

object ProxyTesting {

  class ProxyInvocationHandlerBefore extends InvocationHandler {
    var targetObj: AnyRef = null
    def invoke(proxy: scala.AnyRef, method: Method, args: Array[AnyRef]): AnyRef = {
      println("Hello Stackoverflow when invoking method with name \"%s\"".format(method.getName))
      method.invoke(targetObj, args:_*)
    }
  }
  class ProxyInvocationHandlerAfter extends InvocationHandler {
    var targetObj: AnyRef = null
    def invoke(proxy: scala.AnyRef, method: Method, args: Array[AnyRef]): AnyRef = {
      val res = method.invoke(targetObj, args:_*)
      println("Hello Stackoverflow after invoking method with name \"%s\"".format(method.getName))
      res
    }
  }

  class ProxyInvocationHandlerAfter2 extends InvocationHandler {
    var targetObj: AnyRef = null
    def invoke(proxy: scala.AnyRef, method: Method, args: Array[AnyRef]): AnyRef = {
      val res = method.invoke(targetObj, args:_*)
      println("Hello Stackoverflow after2 invoking method with name \"%s\"".format(method.getName))
      res
    }
  }

  trait Iface {
    def doNothing()
  }

  class Impl extends Iface {
    override def doNothing(): Unit = {
      println("Do sth.")
    }
  }

  def main(args: Array[String]) {
    var handlerBefore = new ProxyInvocationHandlerBefore

    var impl:Iface = new Impl
    handlerBefore.targetObj = impl
    impl =  Proxy.newProxyInstance(
      classOf[Iface].getClassLoader,
        Array(classOf[Iface]),
      handlerBefore
    ).asInstanceOf[Iface]

    var handlerAfter = new ProxyInvocationHandlerAfter
    handlerAfter.targetObj = impl
    impl = Proxy.newProxyInstance(
      classOf[Iface].getClassLoader,
      Array(classOf[Iface]),
      handlerAfter
    ).asInstanceOf[Iface]

    var handlerAfter2 = new ProxyInvocationHandlerAfter2
    handlerAfter2.targetObj = impl
    impl = Proxy.newProxyInstance(
      classOf[Iface].getClassLoader,
      Array(classOf[Iface]),
      handlerAfter2
    ).asInstanceOf[Iface]

    impl.doNothing()
  }

}
