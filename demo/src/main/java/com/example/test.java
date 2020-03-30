package com.example;

public class test {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(1);
        System.out.println(l1.next.val);
    }
}
