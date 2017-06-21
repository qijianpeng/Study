# 如何获取一个Slide内的所有数据?

## Sync Policies
Sync policies allow you to control when buffered data is flushed to the underlying filesystem (thus making it available to clients reading the data) by implementing the `org.apache.storm.hdfs.sync.SyncPolicy` interface:
```java
public interface SyncPolicy extends Serializable {
    boolean mark(Tuple tuple, long offset);
    void reset();
}
```
The `HdfsBolt` will call the `mark()` method for every tuple it processes. Returning `true` will trigger the `HdfsBolt` to perform a sync/flush, after which it will call the `reset()` method.

The `org.apache.storm.hdfs.sync.CountSyncPolicy` class simply triggers a sync after the specified number of tuples have been processed.

- `emit` Slide 内的数据量, 当达到数据量后表示已经获得一个Slide的所有数据.
