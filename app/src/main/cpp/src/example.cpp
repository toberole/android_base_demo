#include <memory>
#include "com_xiaoge_org_jni_Example.h"
#include "log.h"

#include <thread>
#include <mutex>

class Test_Data {
public:
    Test_Data() {
        LOGI("Test_Data#Test_Data ......");
    }

    ~Test_Data() {
        LOGI("Test_Data#~Test_Data ......");
    }

    void sys() {
        LOGI("Test_Data#sys ......");
    }
};

void test_auto_ptr() {
    // ************* auto_ptr （C++98的方案，C++11已经抛弃）采用所有权模式 *************
    // auto_ptr的缺点是：存在潜在的内存崩溃问题！
    std::auto_ptr<Test_Data> p1(new Test_Data());
    // p1失去了指针所有权,此时p1编程悬垂指针, p2拥有了指针的所有权
    std::auto_ptr<Test_Data> p2 = p1;
    p2.get()->sys();
    // 错误用法 ptr1失去所有权之后 就不要在使用了
    // ptr1.get()->sys();
}

void test_unique_ptr() {
    // ************* unique_ptr *************
    std::unique_ptr<Test_Data> p3(new Test_Data());
    // error
    // 编译器认为p4=p3非法，避免了p3不再指向有效数据的问题。尝试复制p3时会编译期出错，而auto_ptr能通过编译期从而在运行期埋下出错的隐患。
    // 因此，unique_ptr比auto_ptr更安全。
    // std::unique_ptr<Test_Data> p4 = p3;
    // 当程序试图将一个 unique_ptr 赋值给另一个时，如果源 unique_ptr 是个临时右值，
    // 编译器允许这么做；如果源 unique_ptr 将存在一段时间，编译器将禁止这么做
    std::unique_ptr<Test_Data> p5 = std::unique_ptr<Test_Data>(new Test_Data);
    p5.get()->sys();
}

void test_shared_ptr() {
    // shared_ptr 解决不了循环引用的问题
    // 配合weak_ptr可以解决循环引用的问题

    // ************* shared_ptr *************
    /*
     成员函数：
        use_count 返回引用计数的个数
        unique 返回是否是独占所有权( use_count 为 1)
        swap 交换两个 shared_ptr 对象(即交换所拥有的对象)
        reset 放弃内部对象的所有权或拥有对象的变更, 会引起原有对象的引用计数的减少
        get 返回内部对象(指针), 由于已经重载了()方法, 因此和直接使用对象是一样的。

     shared_ptr实现共享式拥有概念。多个智能指针可以指向相同对象，该对象和其相关资源会在“最后一个引用被销毁”时候释放。从名字share就可以看出了资源可以被多个指针共享，它使用计数机制来表明资源被几个指针共享。可以通过成员函数use_count()来查看资源的所有者个数。除了可以通过new来构造，还可以通过传入auto_ptr, unique_ptr,weak_ptr来构造。当我们调用release()时，当前指针会释放资源所有权，计数减一。当计数等于0时，资源会被释放。

     shared_ptr 是为了解决 auto_ptr 在对象所有权上的局限性(auto_ptr 是独占的), 在使用引用计数的机制上提供了可以共享所有权的智能指针。
     */
    Test_Data *p_testData = new Test_Data();
    std::shared_ptr<Test_Data> p6(p_testData);
    std::shared_ptr<Test_Data> p7 = p6;
    int count1 = p6.use_count();
    int count2 = p7.use_count();
    LOGI("count1: %d", count1);
    LOGI("count2: %d", count2);
    // 放弃所有权
    p6.reset();
    LOGI("放弃所有权 count: %d", p7.use_count());

    // make_shared
    std::shared_ptr<Test_Data> p8 = std::make_shared<Test_Data>();

}

class B;

class A {
public:
    // std::shared_ptr<B> pb;
    std::weak_ptr<B> pb;

    A() {
        LOGI("A() ......");
    }

    ~A() {
        LOGI("~A() ......");
    }
};

class B {
public:
    std::shared_ptr<A> pa;

    B() {
        LOGI("B() ......");
    }

    ~B() {
        LOGI("~B() ......");
    }
};

void test_weak_ptr() {
    /*
     share_ptr智能指针还是有内存泄露的情况，当两个对象相互使用一个shared_ptr成员变量指向对方，会造成循环引用，使引用计数失效，从而导致内存泄漏。

    weak_ptr 是一种不控制对象生命周期的智能指针, 它指向一个 shared_ptr 管理的对象. 进行该对象的内存管理的是那个强引用的shared_ptr， weak_ptr只是提供了对管理对象的一个访问手段。weak_ptr 设计的目的是为配合 shared_ptr 而引入的一种智能指针来协助 shared_ptr 工作, 它只可以从一个 shared_ptr 或另一个 weak_ptr 对象构造, 它的构造和析构不会引起引用记数的增加或减少。weak_ptr是用来解决shared_ptr相互引用时的死锁问题,如果说两个shared_ptr相互引用,那么这两个指针的引用计数永远不可能下降为0,资源永远不会释放。它是对对象的一种弱引用，不会增加对象的引用计数，和shared_ptr之间可以相互转化，shared_ptr可以直接赋值给它，它可以通过调用lock函数来获得shared_ptr
     */

    std::shared_ptr<B> pb(new B());
    std::shared_ptr<A> pa(new A());
    // 1
    LOGI("pb.use_count:%d ", pb.use_count());
    // 1
    LOGI("pa.use_count:%d ", pa.use_count());

    // 循环引用 造成内存泄露，把其中一个改为weak_ptr可以解决
    pb->pa = pa;
    pa->pb = pb;
    // 2
    LOGI("pb.use_count:%d ", pb.use_count());
    // 2
    LOGI("pa.use_count:%d ", pa.use_count());
    // 注意
    // 不能通过weak_ptr直接访问对象的方法，应该先把它转化为shared_ptr，
    /*
     expired 用于检测所管理的对象是否已经释放, 如果已经释放, 返回 true; 否则返回 false.

    lock 用于获取所管理的对象的强引用(shared_ptr). 如果 expired 为 true, 返回一个空的 shared_ptr; 否则返回一个 shared_ptr, 其内部对象指向与 weak_ptr 相同.

    use_count 返回与 shared_ptr 共享的对象的引用计数.

    reset 将 weak_ptr 置空.

    weak_ptr 支持拷贝或赋值, 但不会影响对应的 shared_ptr 内部对象的计数.
     */
    if (pa->pb.expired()) {// 注意要判断
        LOGI("pb 已经释放不能使用了");
    } else {
        LOGI("pb 没有释放 可以继续使用");
        std::shared_ptr<B> sharedPtr = pa->pb.lock();
    }
}

/*
 * Class:     com_xiaoge_org_jni_Example
 * Method:    smart_point
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_xiaoge_org_jni_Example_smart_1point(JNIEnv *env, jobject jclazz) {
    /*
     C++里面的四个智能指针:
         auto_ptr,
         unique_ptr,
         shared_ptr,
         weak_ptr
         其中后三个是C++11支持，并且auto_ptr已经被C++11弃用。
     */

    // test_auto_ptr();
    // test_unique_ptr();
    // test_shared_ptr();
    test_weak_ptr();

}

void test_thread1() {
    LOGI("test_thread1 ......");
}

void test_thread2(int &a) {
    LOGI("test_thread1 a: %d......", a);
}

// 不可重入锁
std::mutex mtx;
// 可重入锁
std::recursive_mutex recursiveMutex;

void test_mutex1() {
    mtx.lock();
    LOGI("test_mutex1 mtx.lock......");
    mtx.unlock();// 一定要释放

    // lock 与 unlock次数需要一致
    recursiveMutex.lock();
    LOGI("test_mutex1 recursiveMutex.lock......");
    recursiveMutex.lock();
    LOGI("test_mutex1 recursiveMutex.lock......");
    recursiveMutex.unlock();
    recursiveMutex.unlock();

}

void test_mutex2() {

}

/*
 * Class:     com_xiaoge_org_jni_Example
 * Method:    mutli_thread
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_com_xiaoge_org_jni_Example_mutli_1thread(JNIEnv *env, jobject jclazz) {
//    std::thread thread1(test_thread1);
//    thread1.join();
//
//    // 如果需要将参数按引用传递，
//    // 必须将参数用std::ref 或者std::cref进行封装
//    int a = 10;
//    // 传递引用参数
//    std::thread thread2(test_thread2, std::ref(a));
//    thread2.join();

    /*
        thread的一些常用函数：位于std::this_thread命名空间中

        get_id: 返回当前线程的id.
        yield:在处于等待状态时，可以让调度器先运行其他可用的线程。
        sleep_for:阻塞当前线程，时间不少于其参数指定的时间。
        sleep_util:在参数指定的时间到达之前，使当前线程一直处于阻塞状态。
        锁：

        mutex: 提供了核心函数 lock() 和 unlock()，以及非阻塞方法的try_lock()方法，一旦互斥量不可用，该方法会立即返回。
        recursive_mutex:可重入锁，允许在同一个线程中对一个互斥量的多次请求
     */
}

