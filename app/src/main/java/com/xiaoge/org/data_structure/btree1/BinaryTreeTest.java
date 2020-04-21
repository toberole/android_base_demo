package com.xiaoge.org.data_structure.btree1;

import java.lang.reflect.Proxy;

public class BinaryTreeTest {

    public static void main(String[] args) {
        String treeStr = "A,B,D,#,#,#,C,#,E,#,#";
        // String treeStr = "A,#,#";
        AbstractBinaryTree binaryTree = BinaryTreeTest.proxyBinaryTree(treeStr);

        binaryTree.printPostOder();
        binaryTree.printPostOderByRecursion();

        binaryTree.printPreOder();
        binaryTree.printPreOderByRecursion();

        binaryTree.printInOder();
        binaryTree.printInOderByRecursion();

        binaryTree.printLevelOrder();

        binaryTree.printHeight();

        binaryTree.printMaxWidth();

        binaryTree.printK(3);
    }

    public static AbstractBinaryTree proxyBinaryTree(String treeStr) {

        AbstractBinaryTree binaryTree = new BinaryTree(treeStr);

        Object newProxyInstance = Proxy.newProxyInstance(binaryTree.getClass().getClassLoader(),
                binaryTree.getClass().getInterfaces(), (proxy, method, args) -> {
                    System.out.println(method.getName());
                    Object invoke = method.invoke(binaryTree, args);
                    System.out.println();
                    return invoke;
                });

        return (AbstractBinaryTree) newProxyInstance;
    }
}
