# Apache Storm awesome links.

- [Apache Storm](https://storm.apache.org/index.html)
  > Apache Storm is a free and open source distributed realtime computation system. Storm makes it easy to reliably process unbounded streams of data, doing for realtime processing what Hadoop did for batch processing. Storm is simple, can be used with any programming language, and is a lot of fun to use!

- [Structure of the Codebase, 每个`package`的用途](https://storm.apache.org/releases/1.1.0/Structure-of-the-codebase.html)
  > ***First***, Storm was designed from the very beginning to be compatible with multiple languages. _Nimbus is a Thrift service and topologies are defined as Thrift structures._ The usage of Thrift allows Storm to be used from any language. ***Second***, all of Storm's interfaces are specified as Java interfaces. So even though there's a lot of Clojure in Storm's implementation, all usage must go through the Java API. This means that _every feature of Storm is always available via Java_.
  ***Third***, Storm's implementation is largely in Clojure. Line-wise, Storm is about half Java code, half Clojure code. But Clojure is much more expressive, so in reality the great majority of the implementation logic is in Clojure.
  The following sections explain each of these layers in more detail.

- [用实例理解Storm的Stream概念](http://zqhxuyuan.github.io/2016/06/30/Hello-Storm/)
涉及到`declareOutputFields`指定单/多流输出, `topology`的流程结构图. 图文并茂.
