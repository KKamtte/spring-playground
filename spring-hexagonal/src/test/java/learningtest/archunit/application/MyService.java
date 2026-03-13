package learningtest.archunit.application;

import learningtest.archunit.adapter.MyAdapter;

public class MyService {
    MyService2 myService2;
//    MyAdapter myAdapter; // "application 클래스는 adapter 의 클래스를 의존하면 안된다." 위반

    void run() {
        myService2 = new MyService2();
        System.out.println("myService2 = " + myService2);
    }
}
