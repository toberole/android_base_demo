package com.xiaoge.org.test;

public class Test16_Find {
    public static void main(String[] args) {
        int arr[][] = {{1, 2, 3}, {4, 5, 6}};

        System.out.println(arr[0].length);
        System.out.println(arr.length);

        System.out.println(find(5, arr));
    }

    public static boolean find(int target, int[][] array) {
        //基本思路从左下角开始找，这样速度最快
        int row = array.length - 1;//行
        int column = 0;//列
        //当行数大于0，当前列数小于总列数时循环条件成立
        while ((row >= 0) && (column < array[0].length)) {
            if (array[row][column] > target) {
                row--;
            } else if (array[row][column] < target) {
                column++;
            } else {

                System.out.println("row: " + row);
                System.out.println("column: " + column);
                return true;
            }
        }
        return false;
    }

    public static boolean find_1(int target, int[][] array) {
        int row = array.length;
        int column = 0;

        while (row >= 0 && column <= array[0].length) {
            if (array[row][column] > target) {
                row--;
            } else if (array[row][column] < target) {
                column++;
            } else {
                System.out.println("row: " + row);
                System.out.println("column: " + column);
                return true;
            }
        }
        return false;
    }
}
