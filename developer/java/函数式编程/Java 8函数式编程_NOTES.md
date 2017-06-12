# 流( `java.util.stream` )

## 实现机制

`Stream`操作多次其中的对象，只过滤一次。
- 惰性求值：不创建新的集合或者值，只是一个操作，比如`filter()`。
- 及早求值：创建新的集合或者值，比如`count()`。

判断方法： 如果方法返回值是 `Stream`，那么是惰性求值； 如果返回值是另一个值或为空， 那么就是及早求值。

## 常用流操作

### 流来源
- `Collection.stream()`	使用一个集合的元素创建一个流。
- `Stream.of(T...)`	使用传递给工厂方法的参数创建一个流。
- `Stream.of(T[])`	使用一个数组的元素创建一个流。
- `Stream.empty()`	创建一个空流。
- `Stream.iterate(T first, BinaryOperator<T> f)`	创建一个包含序列`first, f(first), f(f(first)), ...` 的无限流
- `Stream.iterate(T first, Predicate<T> test, BinaryOperator<T> f)`	（仅限 Java 9）类似于` Stream.iterate(T first, BinaryOperator<T> f)`，但流在测试预期返回 `false` 的第一个元素上终止。
- `Stream.generate(Supplier<T> f)`	使用一个生成器函数创建一个无限流。
- `IntStream.range(lower, upper)`	创建一个由下限到上限（不含）之间的元素组成的 `IntStream`。
- `IntStream.rangeClosed(lower, upper)`	创建一个由下限到上限（含）之间的元素组成的 `IntStream。`
- `BufferedReader.lines()`	创建一个有来自 `BufferedReader` 的行组成的流。
- `BitSet.stream()`	创建一个由 `BitSet` 中的设置位的索引组成的 `IntStream`。
- `Stream.chars()`	创建一个与 `String` 中的字符对应的 `IntStream`。

### 惰性求值（中间流操作）
- `filter(Predicate<T>)`	与预期匹配的流的元素
- `map(Function<T, U>)`	将提供的函数应用于流的元素的结果
- `flatMap(Function<T, Stream<U>>`	将提供的流处理函数应用于流元素后获得的流元素
- `distinct()`	已删除了重复的流元素
- `sorted()`	按自然顺序排序的流元素
- `Sorted(Comparator<T>)`	按提供的比较符排序的流元素
- `limit(long)`	截断至所提供长度的流元素
- `skip(long)`	丢弃了前 N 个元素的流元素
- `takeWhile(Predicate<T>)`	（仅限 Java 9）在第一个提供的预期不是 true 的元素处阶段的流元素
- `dropWhile(Predicate<T>)`	（仅限 Java 9）丢弃了所提供的预期为 true 的初始元素分段的流元素

### 及早求值（终止操作）
- `forEach(Consumer<T> action)`	将提供的操作应用于流的每个元素。
- `toArray()`	使用流的元素创建一个数组。
- `reduce(...)`	将流的元素聚合为一个汇总值。
- `collect(...)` 将流的元素聚合到一个汇总结果容器中。

- `findAny()`	返回流的任何元素（如果有）。
- `min(Comparator<T>)` 通过比较符返回流的最小元素。
- `max(Comparator<T>)`	通过比较符返回流的最大元素。
- `count()`	返回流的大小。
- `{any,all,none}Match(Predicate<T>)`	返回流的任何/所有元素是否与提供的预期相匹配。
- `findFirst()`	返回流的第一个元素（如果有）。


``` java
List<String> collected = Stream.of("a","b","c").collect(Collectors.toList());
collected.stream().forEach(s -> System.out.println(s));
```
