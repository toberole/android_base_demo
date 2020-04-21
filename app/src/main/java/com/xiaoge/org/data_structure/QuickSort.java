package com.xiaoge.org.data_structure;

public class QuickSort {
    // 时间复杂度为O(NlogN)。
    public static void quickSort(int arr[], int low, int high) {
        if (low > high) return;

        // temp 基准位
        int temp = arr[low];
        int i = low;
        int j = high;

        while (i < j) {
            // 从右往左扫描
            // 找到第一个小于temp的值
            while (i < j && arr[j] >= temp) {
                j--;
            }

            // 从左往右扫描
            // 找到第一个大于temp的值
            while (i < j && arr[i] <= temp) {
                i++;
            }

            // 交换前面找到的两个值
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        // 基准值放置到其最终的位置
        arr[low] = arr[i];
        arr[i] = temp;

        quickSort(arr, low, i - 1);
        quickSort(arr, i + 1, high);
    }

    public static void main(String[] args) {
        // int arr[] = {5, 1, 2, 4, 3};
        // int arr[] = {1, 2, 3, 4, 5};
        int arr[] = {5, 4, 3, 2, 1};

        quickSort(arr, 0, arr.length - 1);

        for (int i : arr) {
            System.out.println(i);
        }
    }
}
