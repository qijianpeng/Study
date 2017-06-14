# Spout作用
`Spout`: A `spout` is a source of streams in a topology. Generally spouts will read tuples from an external source and `emit` them into the topology (e.g. a Kestrel queue or the Twitter API). Spouts can either be reliable or unreliable. A reliable spout is capable of replaying a tuple if it failed to be processed by Storm, whereas an unreliable spout forgets about the tuple as soon as it is emitted.

Spouts can emit more than one stream. To do so, declare multiple streams using the `declareStream` method of [ OutputFieldsDeclarer(_Interface_)](http://storm.apache.org/releases/1.1.0/javadocs/org/apache/storm/topology/OutputFieldsDeclarer.html) and specify the stream to emit to when using the emit method on [SpoutOutputCollector(_Class_)](http://storm.apache.org/releases/1.1.0/javadocs/org/apache/storm/spout/SpoutOutputCollector.html).

The main method on spouts is `nextTuple`. `nextTuple` either emits a new tuple into the topology or simply returns if there are no new tuples to emit. It is imperative(必要的) that `nextTuple` does not block for any `spout` implementation, because Storm calls all the spout methods **on the same thread**.

The other main methods on spouts are `ack` and `fail`. These are called when Storm detects that a tuple emitted from the spout either successfully completed through the topology or failed to be completed. ack and fail are only called for reliable spouts. See the [here](http://storm.apache.org/releases/1.1.0/Concepts.html) for more information.

因此, `Spout`中不能存在阻塞方法, 因为`Spout`是单线程.另外, `Spout`中重要的是`ack()`与`fail()`方法.

# Spout处理数据的流程
在上文中提到, 若想使用`Spout`发送数据, 在使用`SpoutOutputCollector`中的`emit()`方法时要对`OutputFieldsDeclarer`对数据流进行声明, 即`declareStream()`. 因此发送数据的过程大致可分为：
1. 实现`OutputFieldsDeclarer.declareStream()`.
2. 调用`emit()`方法.
那么, 如何读取数据呢？可以猜出, 读取数据在第1步`declareStream()`实现. 由于`OutputFieldsDeclarer`属于`Interface`, 应该哪个类进行`implements`呢？

我们这时情不自禁想起了Storm提供的Storm-Starter的例子. 里面有一个叫`RandomSentenceSpout`的类, 看一看这个类的声明为: `public class RandomSentenceSpout extends BaseRichSpout`, 这里并没有想看到的`OutputFieldsDeclarer`接口, 但是却Overwrite了`declareOutputFields`方法:
```java
@Override
public void declareOutputFields(OutputFieldsDeclarer declarer) {
  declarer.declare(new Fields("word"));
}
```

到这里你可能比较疑惑, 为什么在`OutputFieldsDeclarer`中明明调用的是`declareOutputFields`方法,这里却变成了`declareStream`? 其实[`declare`](http://storm.apache.org/releases/1.1.0/javadocs/org/apache/storm/topology/OutputFieldsDeclarer.html#declare-boolean-org.apache.storm.tuple.Fields-)方法中调用了'declareStream'方法, 并传入了默认`stream id`.

这一定是`BaseRichSpout`继承得到的方法. 再查看`BaseRichSpout(_abstract class_)`的源代码, 发现其`extends BaseComponent implements IRichSpout`并且声明了4个函数:
```java
close(); activate(); deactivate(); ack(Object msgId); fail(Object msgId);
```
并没有`declareOutputFields`, 那一定是来自于`BaseComponent`了. 最终在`org.apache.storm.topology.IComponent`找到了该方法.
可以看出`IComponent`的继承/实现过程为`RandomSentenceSpout`<--`BaseRichSpout`<--`BaseComponent`<--`IComponent`.

`declareOutputFields`的API解释如下:
> Declare the output schema for all the streams of this topology. `declarer` is used to declare output stream ids, output fields, and whether or not each output stream is a direct stream.
因此, 它是用来定义当前topology的输出流属性的.

那么, 这个方法是如何工作的呢?
在`main`方法里我们可以看到:
```java
TopologyBuilder builder = new TopologyBuilder();
builder.setSpout("spout", new RandomSentenceSpout(), 5);
```
其中调用了`setSpout`方法, 这个方法, 就将`Spout`传递给了Topology.
`RandomSentenceSpout` 调用`nextTuple`方法不断地产生数据流. 当产生数据流后要向topology发送, 要发送数据, 如何进行识别呢? 上文讲到了, 在emit时还要实现`OutputFieldsDeclarer`的`declareStream()`方法. 这个方法的调用过程是这样的:
`TopologyBuilder.createTopology()` --> `componentCommon.getComponentCommon()` --> `component.declareOutputFields(outputFieldsGetter)` --> 'OutputFieldsDeclarer.declareOutputFields()'.

现在就对上号了, Spout通过多重继承/implements `IComponent` 实现`declareOutputFields`方法, 然后在该方法中调用`OutputFieldsDeclarer`中的`declare`或`declareStream`方法, 之后由`topology`创建流的映射关系, 再由`emit`方法, 将数据发送到整个`topology`中去. 因此, `declare`方法是必须要实现的方法. `Topology`中的`fieldsGrouping`至关重要, 它建立了与`declare`方法中传入的`Field`对应. 而`emit`又与`declarer`对应, 这就形成了一个铰链.  `nextTuple`中调用`emit`方法, 实现了数据到`topology`中的映射. 最后交由`Bolt`的`execute`方法对接收到的每个`tuple`进行处理.

Some useful links: [Lifecycle of a topology](http://storm.apache.org/releases/1.1.0/Lifecycle-of-a-topology.html)

# Spout读取数据的方式
Storm读取数据可以与很多平台进行整合. 比如[HDFS](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HdfsUserGuide.html). 后续将结合HDFS进行数据的读取.
