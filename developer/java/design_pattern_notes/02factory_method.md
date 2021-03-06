简单工厂模式的缺点：

只有一个工厂，当系统中需要引入新产品时，由于静态工厂方法通过所传入参数的不同来创建不同的产品，这必定要修改工厂类的源代码，将违背“开闭原则”，如何实现增加新产品而不影响已有代码？

工厂方法模式
针对不同的产品提供不同的工厂，系统提供一个与产品等级结构对应的工厂等级结构. 定义一个用于创建对象的接口，让子类决定将哪一个类实例化. 工厂方法模式让一个类的实例化延迟到其子类.

https://blog.csdn.net/lovelion/article/details/9306745

工厂方法模式实例
差的设计：　https://blog.csdn.net/lovelion/article/details/9306457
工厂方法模式设计：https://blog.csdn.net/lovelion/article/details/9307137
工厂方法模式－－隐藏细节：https://blog.csdn.net/lovelion/article/details/9307561

总结：
工厂方法模式是简单工厂模式的延伸，继承了简单工厂模式的优点，弥补了简单工厂模式的不足．
工厂方法模式是使用频率最高的设计模式之一，是很多开源框架和API类库的核心模式．

1. 主要优点
(1) 在工厂方法模式中，工厂方法用来创建客户所需要的产品，同时还向客户隐藏了哪种具体产品类将被实例化这一细节，用户只需要关心所需产品对应的工厂，无须关心创建细节，甚至无须知道具体产品类的类名

(2) 基于工厂角色和产品角色的多态性设计是工厂方法模式的关键。它能够让工厂可以自主确定创建何种产品对象，而如何创建这个对象的细节则完全封装在具体工厂内部

(3) 系统中加入新产品时，无须修改抽象工厂和抽象产品提供的接口，无须修改客户端，也无须修改其他的具体工厂和具体产品，完全符合“开闭原则”

2. 主要缺点
(1) 在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，系统中类的个数将成对增加，在一定程度上增加了系统的复杂度，有更多的类需要编译和运行，会给系统带来一些额外的开销

(2) 由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度


3. 适用场景
(1) 客户端不知道它所需要的对象的类. 在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建，可将具体工厂类的类名存储在配置文件或数据库中

(2) 抽象工厂类通过其子类来指定创建哪个对象. 在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和里氏代换原则，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展


