package com.xiaoge.org.test;

public class Sort {
    public static void main(String[] args) {
        int arr[] = new int[]{1, 3, 5, 2, 4, 6};
//        int arr[] = new int[]{1, 3, 2};
        // bubble(arr, false);

        binarySort(arr);
        for (int i : arr) {
            System.out.println(i + "  ");
        }
    }

    /**
     * 从小到大
     */
    public static void binarySort(int[] source) {
        for (int i = 1; i < source.length; i++) {
            int j;
            int low = 0;
            int high = i - 1;
            int mid;
            int temp = source[i];
            while (low <= high) {
                // 找出中间值
                mid = (low + high) >> 1;
                // 待插入记录比中间记录小
                if (temp < source[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            System.out.println("temp: " + temp + " j: " + (i - 1) + " low: " + low);

            //将前面所有大于当前待插入记录的记录后移
            for (j = i - 1; j >= low; j--) {
                source[j + 1] = source[j];
            }
            //将待插入记录回填到正确位置.
            source[low] = temp;
        }
    }

    public static void binarySort1(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            // 先找到插入的位置
            int start = 0;
            int end = i;
            int mid;
            while (start <= end) {
                mid = (start + end) / 2;
                int v = arr[i];
                int temp = arr[mid];
                if (v < temp) {
                    end = mid - 1;
                } else if (v > temp) {
                    start = mid + 1;
                }
            }
        }
    }

    /**
     * @param rank_b true表示大到小
     */
    private static void bubble(int[] arr, boolean rank_b) {
        for (int i = 0; i < arr.length; i++) {
            boolean b = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (rank_b) {
                    if (arr[j] < arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;

                        b = true;
                    }
                } else {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;

                        b = true;
                    }
                }
            }

            if (!b) return;
        }
    }


}
