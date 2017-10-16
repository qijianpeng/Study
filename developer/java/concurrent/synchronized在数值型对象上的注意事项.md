在对锁相关的知识进行整理时发现了一个很奇怪的现象, 都说Lock跟synchronized关键字所能达到的效果是一致的, 但是当我对一个数进行修改时,
却发现两种方式所得到的结果并不一致. 追究其原因, 原来是java的自动封箱解箱机制在作祟, 当对一个数值(比如Integer类型)进行synchronized加锁时, 如果对这个数值进行修改, 虽然说我们在初始化时是分配的高级类型的对象, 但是在修改时, java会解箱, 解箱修改后会重新生成新的数字, 如果对其加锁, 那么每次都是对新的对象进行锁操作, 因此会得到不确定的结果.
以下是代码以及解释.
```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main implements Runnable{
    static Lock lock = new ReentrantLock();
    static Object obj = new Object();
    static Long a = new Long(0);

    public static Long getA() {
        return a;
    }

    public static void main(String[] args) throws InterruptedException {
        Main m1 = new Main();     Main m2 = new Main();    Main m3 = new Main();

        Thread t1 = new Thread(m1);    Thread t2 = new Thread(m2);    Thread t3 = new Thread(m3);
        t1.start();  t2.start();    t3.start();
        t1.join();   t2.join();     t3.join();
        System.out.println(Main.a);
    }
    @Override
    public void run() {
        //1. ReentrantLock 方法
       for (int i = 0; i < 10000; i++){
             lock.lock();
             a++;
             lock.unlock();
       }// 最终输出结果: 30000
        //2. synchronized 关键字
        for (int i = 0; i < 10000; i++){
            synchronized (obj){//由于java的自动封箱与解箱, 如果锁a的话, 每次操作后, a都会发生变化, 不是原来的对象了.
                a++;
            }
        }// 最终输出结果不确定
    }
}
```
