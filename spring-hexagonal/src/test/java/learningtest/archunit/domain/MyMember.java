package learningtest.archunit.domain;

import learningtest.archunit.adapter.MyAdapter;
import learningtest.archunit.application.MyService;

public class MyMember {
//    MyService myService; // "application 클래스를 의존하는 클래스는 application, adapter 에만 존재해야 한다." 위반
//    MyAdapter myAdapter; // "domain 클래스는 domain, java 클래스에만 의존해야한다." 위반
}
