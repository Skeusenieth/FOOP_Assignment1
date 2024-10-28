package hello;

class A {
    int x = 0;
    A next = null;

    public A(int x) {
        this.x = x;
    }
}

public class ObjectRefs {
    public static void main(String[] args) {
        A a = new A(1);
        A b = new A(2);
        A c = new A(3);
        a.next = b;
        b.next = c;
        c.next = a;
        System.out.println(a.x);
        System.out.println(a.next.x);
        System.out.println(a.next.next.x);
        System.out.println(a.next.next.next.x);
    }
}



