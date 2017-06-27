# TumblingWindow 的使用

在数据流处理中不可避免的会涉及到`Window`的概念.
Storm中提供了两种类型的`Window`, 分别为`SlidingWindow`与`TumblingWindow`.
前者是窗口一个一个`Slide`的往前滑动, 而后者是一个一个`Window`的往前滑动.
这里主要讲述`TumblingWindow`. 如下图:
```
| e1 e2 | e3 e4 e5 e6 | e7 e8 e9 |...
0       5             10         15    -> time
   w1         w2            w3
```
在`TumblingWindow`中, `w1`滑动将会变为`w2`, 可以看出`w1`与`w2`之间是没有交叉重叠的. 并且这个例子中是每隔`5s`滑动一次.

那么如何使用呢? Storm中默认的是使用以下方法对`TumblingWindow`进行定义.
```
withTumblingWindow(BaseWindowedBolt.Count count)
Count based tumbling window that tumbles after the specified count of tuples.

withTumblingWindow(BaseWindowedBolt.Duration duration)
Time duration based tumbling window that tumbles after the specified time duration.
```
我们可以看出有两种方式, 分别为`count-based`与`time-based`.
当我们使用`time-based`, Storm默认情况下所使用`timestamp`是`Bolt`处理当前`tuple`时所获得的`timestamp`.
在实际应用中可能会用自定义的`time`来使用`time-based`的窗口(比如使用历史数据进行测试, 历史数据中存在一个`timestamp`的字段). 这种情况下该如何做呢?

## 自定义`timestamp`的使用
Storm提供了自定义`timestamp`的方法.

```java
/**
* Specify a field in the tuple that represents the timestamp as a long value. If
* this field is not present in the incoming tuple, an
*{@link IllegalArgumentException} will be thrown.
*
* @param fieldName the name of the field that contains the timestamp
*/
public BaseWindowedBolt withTimestampField(String fieldName)
```
在流过来的`tuple`中会寻找`fieldName`这个字段. 由于是在`tuple`中查找, 所以这就追溯到上游发送数据流的节点.
在`emit tuple`时我们记得要实现`declareOutputFields`方法, 所以, 在这个地方我们要使用自定义的`timestamp`必须要在上游组件中的`declareOutputFields`方法里添加一个`"timestamp"`(或者其他有意义的名字)来指明这个字段, 然后在`withTimestampField(String fieldName)`传入`"timestamp"`.

还需要注意的是, `"timestamp"`所指向的字段的类型必须是`long`型的.
