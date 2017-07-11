# 关于java中的指针不得不说的事
java语言与C/C++最大的不同是取消了对指针的使用, 转变为引用. 因此, 这里不得不说一些容易踩得坑.
我们经常会看到抛出一个`NoPointerException`这样的错误, 为了解决这些错误所消耗的时间甚至超过了
编写程序本身的时间. 然而大多数情况下都是可以避免的.

拿一个简单的例子来举例: `int`型数组 `array[3]`(初始值为`0`), 我要对array这个数组里的数据通过函数`modify(array)`
来修改array[2]的值.
```java
public void modify(int[] array){
  array[2] = 3;
}//end
System.out.println(array[2]);
```
这里会输出什么呢?
是`3`还是`0`? 当然是`3`, 因为`modify()`没有对`array`这个名称指向的地址进行修改.
如果是以下代码呢?
```java
public void modify(int[] array){
   int[] arr = {1,2,3};
   array = arr;// L1
   array[2] = 10;// L2
}//end
Arrays.stream(array).forEach(ints -> System.out.println(ints));
```
`array`里的数据会变成什么呢?  我们知道java是传递的引用, 这里`array`会变成`{1, 2, 3}`吗?
答案是`{0, 0, 0}`. 我传的明明是引用, 在L1进行了重新引用, 这里为什么变成了这般模样?

因为java是按值传递, 在`modify()`函数里, 对`array`这个名字重新指向了别的内存空间,
因此这里就不再是一开始在函数传参里传进来的`array`了.

NOTES: java 是按值传递, 传到函数里的是值, 是一份copy.

References:
 - [JAVA 对象引用，以及对象赋值](http://www.cnblogs.com/focusChen/articles/2497768.html)
 - [Does Java pass by reference or pass by value? Why can't you swap in Java?](http://www.javaworld.com/article/2077424/learn-java/does-java-pass-by-reference-or-pass-by-value.html)
