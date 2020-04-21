package com.xiaoge.org.data_structure.btree1;

public interface AbstractBinaryTree {
    void printPostOder();

    void printPostOderByRecursion();

    void printPreOder();

    void printPreOderByRecursion();

    void printInOderByRecursion();

    void printInOder();

    void printHeight();

    void printMaxWidth();

    void printLevelOrder();

    void printK(int k);
}