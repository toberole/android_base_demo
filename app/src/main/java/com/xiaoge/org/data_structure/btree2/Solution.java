package com.xiaoge.org.data_structure.btree2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solution {

    // 非递归前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            res.add(current.val);

            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return res;
    }

    // 非递归中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();

        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                res.add(p.val);
                p = p.right;
            }
        }

        return res;
    }

    // 非递归后序遍历
    public static ArrayList postorderTraversal(TreeNode root) {
        ArrayList alist = new ArrayList();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) {
            return alist;
        }

        TreeNode cur, pre = null;
        stack.push(root);
        while (!stack.empty()) {
            cur = stack.peek();
            if ((cur.left == null && cur.right == null) || (pre != null && (cur.left == pre || cur.right == pre))) {
                TreeNode temp = stack.pop();
                alist.add(temp.val);
                pre = temp;
            } else {
                if (cur.right != null)
                    stack.push(cur.right);
                if (cur.left != null)
                    stack.push(cur.left);
            }
        }
        return alist;
    }

    // 非递归层次遍历
    public List<Integer> levelTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            // current node
            TreeNode current = queue.remove();
            res.add(current.val);

            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }

        return res;
    }
}
