package com.xiaoge.org.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环打印阵列
 * 可以用一个循环来打印矩阵，每一次打印矩阵中的一个圈。
 * 那么循环结束的条件是什么？假设这个矩阵的行数是rows，列数是columns。
 * 打印第一圈的左上角的坐标是（0, 0），第二圈的左上角的坐标是（1, 1）,依次类推。
 * 我们注意到左上角的坐标中行标和列标总是相同的，
 * 于是可以在矩阵中选取左上角为（start，start）的一圈作为我们的分析的目标。
 * <p>
 * 对于一个5*5的矩阵，最后一圈只有一个数字，对应的坐标为（2, 2）。5 > 2 * 2；
 * <p>
 * 对于一个6*6的矩阵，最后一圈有4个数字，其左上角的坐标仍是（2, 2）。6 > 2 *2；
 * <p>
 * 故循环继续的条件为:
 * rows > startY * 2 && columns > startX * 2
 * <p>
 * 打印一圈的实现可以分为4步：
 * 第一步从左到右打印一行
 * 第二步从上到下打印一列
 * 第三步从右到左打印一行
 * 第四步从下到上打印一列（每一步根据起始坐标和终止坐标用一个循环就能打印出一行或者一列）。
 * <p>
 * 注意：
 * 最后一圈可能退化成只有一行、只有一列，甚至只有一个数字，因此打印这样的一圈就不再需要四步，可能只需要三步、两步、一步。
 * <p>
 * 接下来我们分析打印时每一步的前提条件。
 * 第一步总是需要的，因为打印一圈至少有一步。如果只有一行，那么就不用第二步了。
 * 即第二步的前提条件是终止行号大于起始行号。
 * 打印第三步的前提条件是圈内至少有两行两列。
 * 即除了要求终止行号大于起始行号外，还需要终止列号大于起始列号。
 * 同理打印第四步的前提条件是至少有三行两列，即要求终止行号比起始行号至少大2，
 * 同时终止列号大于起始列号。
 * <p>
 * 第二步：start_row > end_row
 * 第三步：start_row > end_row  && start_column > end_column
 * 第四步：start_row — end_row > 2 && start_column > end_column
 */
public class Test20_1 {
    public static void main(String[] args) {
        int arr[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        List<Integer> res = print(arr);

//        for (Integer i : res) {
//            System.out.print(i + "  ");
//        }
    }

    private static List<Integer> print(int[][] arr) {
        List<Integer> res = new ArrayList<>();

        int rows = arr.length;
        int cols = arr[0].length;

        int start = 0;
        while (rows > start * 2 && cols > start * 2) {
            printCircle(res, arr, rows, cols, start);
            start++;
        }

        return res;
    }

    private static void printCircle(List<Integer> res, int[][] nums, int rows, int cols, int start) {
        // 水平方向终止最后元素的index[左到右]
        int endX = cols - start - 1;
        // 垂直方向终止最后元素的index[上到下]
        int endY = rows - start - 1;

        //第一步：从左到右打印一行
        for (int i = start; i <= endX; ++i) {
            int number = nums[start][i];
            System.out.print(number + " ");
            res.add(number);
        }

        System.out.println("************");

        //第二步：从上到下打印一列
        if (start < endY) {
            for (int i = start + 1; i <= endY; ++i) {
                int number = nums[i][endX];
                System.out.print(number + " ");
                res.add(number);
            }
        }

        System.out.println("************");

        //第三步：从右向左打印一行
        if (start < endX && start < endY) {
            for (int i = endX - 1; i >= start; --i) {
                int number = nums[endY][i];
                System.out.print(number + " ");
                res.add(number);
            }
        }

        System.out.println("************");

        //第四步：从下向上打印一列
        if (start < endX && start < endY - 1) {
            for (int i = endY - 1; i >= start + 1; --i) {
                int number = nums[i][start];
                System.out.print(number + " ");
                res.add(number);
            }
        }
    }
}
