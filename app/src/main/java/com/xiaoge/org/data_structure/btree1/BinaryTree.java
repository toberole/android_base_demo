package com.xiaoge.org.data_structure.btree1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Node {
    public String data;
    public Node left = null;
    public Node right = null;
    public boolean flag;

    public Node(String data) {
        this.data = data;
    }

    public Node() {
    }

    @Override
    public String toString() {
        return this.data;
    }
}

public class BinaryTree implements AbstractBinaryTree {
    private Node root = new Node();

    public BinaryTree(String tree) {
        String[] treeNodes = tree.split(",");
        createTreeByRecursion(treeNodes);
    }

    public void createTreeByRecursion(String[] treeNodes) {
        createTreeByRecursion(root, treeNodes, 0);
    }

    /**
     * 先序递归创建二叉树
     */
    private int createTreeByRecursion(Node node, String[] treeNodes, int n) {
        if ("#".equals(treeNodes[n]))// # 代表叶子节点
            return n + 1;

        node.data = treeNodes[n];
        node.left = new Node();
        int left = createTreeByRecursion(node.left, treeNodes, n + 1);
        node.right = new Node();
        int right = createTreeByRecursion(node.right, treeNodes, left);
        return right;
    }

    /**
     * 先序非递归创建
     */
    public void createTree(String[] treeNodes) {
        Stack<Node> stack = new Stack<>();
        int index = 0;
        Node node = root;
        while (index < treeNodes.length) {
            while (true) {
                if ("#".equals(treeNodes[index])) {
                    node = stack.pop();
                    if (node.flag == false) {
                        node.left = null;
                        node.flag = true;
                        stack.push(node);
                    } else {
                        node.right = null;
                    }

                    // 记得加1
                    index++;
                    break;
                }

                if (node.flag == true) {
                    node.right = new Node();
                    node = node.right;
                }

                node.data = treeNodes[index];
                stack.push(node);
                node.left = new Node();
                node = node.left;
                index++;
            }

            if (node.flag == false) {
                stack.push(node);
                node.flag = true;
                node = node.right;
            } else {
                node = stack.peek();
                node.flag = true;
            }
        }
    }

    // 递归调用的方法,需要将root传递进去
    private void printPreOderByRecursion(Node node) {
        if (node == null) {
            return;
        }
        printNode(node);
        printPreOderByRecursion(node.left);
        printPreOderByRecursion(node.right);
    }

    public void printPreOderByRecursion() {
        printPreOderByRecursion(root);
    }

    private void printInOderByRecursion(Node node) {
        if (node == null) {
            return;
        }

        printInOderByRecursion(node.left);
        printNode(node);
        printInOderByRecursion(node.right);
    }

    public void printInOderByRecursion() {
        printInOderByRecursion(root);
    }

    private void printPostOderByRecursion(Node node) {
        if (node == null) {
            return;
        }

        printPostOderByRecursion(node.left);
        printPostOderByRecursion(node.right);
        printNode(node);
    }

    public void printPostOderByRecursion() {
        printPostOderByRecursion(root);
    }

    // 非递归遍历二叉树,先序遍历
    public void printPreOder() {
        Stack<Node> stack = new Stack<>();
        Node tempNode = root;
        while (true) {
            while (tempNode != null) {
                printNode(tempNode);
                stack.push(tempNode);
                tempNode = tempNode.left;
            }

            if (stack.isEmpty()) {
                break;
            }

            tempNode = stack.pop();
            tempNode = tempNode.right;
        }
    }

    // 非递归 中序遍历
    public void printInOder() {
        Stack<Node> stack = new Stack<>();
        Node tempNode = root;
        while (true) {
            while (tempNode != null) {
                stack.push(tempNode);
                tempNode = tempNode.left;
            }

            if (stack.isEmpty()) {
                break;
            }
            tempNode = stack.pop();
            printNode(tempNode);
            tempNode = tempNode.right;
        }
    }

    // 非递归 后序遍历
    public void printPostOder() {
        Stack<Node> stack = new Stack<>();
        Node tempNode = root;
        while (true) {
            while (tempNode != null) {
                if (tempNode.flag == true) {
                    tempNode = tempNode.right;
                } else {
                    stack.push(tempNode);
                    tempNode = tempNode.left;
                }
            }

            tempNode = stack.pop();
            if (tempNode.flag == false) {
                stack.push(tempNode);
                tempNode.flag = true;
                tempNode = tempNode.right;
            } else {
                printNode(tempNode);
                if (stack.isEmpty()) {
                    break;
                }
                tempNode = stack.peek();
                tempNode.flag = true;
            }
        }
    }

    // 层序遍历 利用队列
    public void printLevelOrder() {
        Queue<Node> queue = new LinkedList<>();
        Node tempNode = root;
        queue.offer(tempNode);
        while (!queue.isEmpty()) {
            Node topNode = queue.poll();
            if (topNode == null) {
                continue;
            }
            printNode(topNode);
            queue.offer(topNode.left);
            queue.offer(topNode.right);
        }
    }

    /**
     * 打印第K层元素
     */
    public void printK(int k) {
        printK(root, k);
    }

    public void printK(Node node, int k) {
        // System.out.println("k: " + k);

        if (k == 1 && node != null) {
            printNode(node);
        } else {
            if (k > 1 && node.left != null) {
                printK(node.left, k - 1);
            }

            if (k > 1 && node.right != null) {
                printK(node.right, k - 1);
            }
        }
    }

    // 树高 递归分别求出左子树的深度、右子树的深度，两个深度的较大值+1
    public int getHeightByRecursion(Node node) {
        if (node == null) {
            return 0;
        }
        int left = getHeightByRecursion(node.left);
        int right = getHeightByRecursion(node.right);
        return 1 + Math.max(left, right);
    }

    /**
     * 为什么不直接写成调用 root,而是另写一个方法去调用呢
     * 因为,这样可以不再为root,单独设置一个临时变量去存贮
     * 而且也固定外部调用的方法,而不用关心内部的实现
     */
    public void printHeight() {
        int height = getHeightByRecursion(root);
        System.out.print(height);
    }

    // 利用层序遍历,得到树的最大宽度
    public void printMaxWidth() {
        Queue<Node> queue = new LinkedList<>();
        Queue<Node> queueTemp = new LinkedList<>();

        int maxWidth = 1;
        Node tempNode = root;
        queue.offer(tempNode);
        while (!queue.isEmpty()) {
            while (!queue.isEmpty()) {
                Node topNode = queue.poll();
                if (topNode == null) {
                    continue;
                }
                if (topNode.left.data != null) {
                    queueTemp.offer(topNode.left);
                }

                if (topNode.right.data != null) {
                    queueTemp.offer(topNode.right);
                }
            }

            maxWidth = Math.max(maxWidth, queueTemp.size());
            queue = queueTemp;
            queueTemp = new LinkedList<>();
        }

        System.out.print(maxWidth);
    }

    public Node getRoot() {
        return root;
    }

    public void printNode(Node node) {
        if (node.data == null) {
            System.out.print("");
        } else {
            System.out.print(node.data);
        }
    }
}
