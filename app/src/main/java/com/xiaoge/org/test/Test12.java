package com.xiaoge.org.test;

class Node {
    public int data;
    Node next;
}

public class Test12 {
    public static void main(String[] args) {
        // test1();
    }


    private static void test1() {
        int n = 10;
        Node list = createList(n);

        while (list.next != null) {
            Node node = list.next;
            System.out.println(node.data);
            list.next = node.next;
        }

        System.out.println("**********************");

        Node list1 = createList1(n);
        while (list1.next != null) {
            Node node = list1.next;
            System.out.println(node.data);
            list1.next = node.next;
        }

        System.out.println("**********************");
        Node list2 = createList1(n);
        list2 = reverseList(list2);
        while (list2.next != null) {
            Node node = list2.next;
            System.out.println(node.data);
            list2.next = node.next;
        }
    }

    private static void merge(Node list1, Node list2) {



    }

    /**
     * 翻转链表
     */
    private static Node reverseList(Node head) {
        Node node = new Node();
        Node last = node;

        while (head.next != null) {
            Node temp = new Node();
            Node n = head.next;
            temp.data = head.data;
            last.next = temp;
            last = temp;

            head.next = n.next;
        }

        return node;
    }

    /**
     * 头插法创建链表
     */
    private static Node createList(int n) {
        Node node = new Node();
        for (int i = 0; i < n; i++) {
            Node temp = new Node();
            temp.data = i;
            temp.next = node.next;
            node.next = temp;
        }
        return node;
    }

    /**
     * 尾插法创建链表
     */
    private static Node createList1(int n) {
        Node node = new Node();
        Node last = node;
        for (int i = 0; i < n; i++) {
            Node temp = new Node();
            temp.data = i * n / 3;
            last.next = temp;
            last = temp;
        }
        return node;
    }
}
