package com.xiaoge.org.test;

/**
 * 排序相关
 */
public class Test22 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 0, 5, 0, 6, 0, 7, 0};
        move(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "  ");
        }

        System.out.println("\n******************************");
        // int arr1[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int arr1[] = {8, 7, 6, 5, 4, 3, 2, 1};
        move2(arr1);
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + "  ");
        }

        System.out.println("\n******************************");
        int arr2[] = {1, 2, 3, 4, 5, 6, 7};
        move3(arr2);
        for (int i = 0; i < arr2.length; i++) {
            System.out.print(arr2[i] + "  ");
        }

        System.out.println("\n******************************");
        int arr3[] = {8, 7, 6, 5, 4, 3, 2, 1};
        // int arr3[] = {1, 2, 3, 4, 5, 6, 7};
        move4(arr3);
        for (int i = 0; i < arr3.length; i++) {
            System.out.print(arr3[i] + "  ");
        }
    }

    /**
     * 奇数位于偶数前面 保持元素的前后相对位置不变
     * <p>
     * 方法1：
     * 用空间换时间，如果遇到奇数就用一个数组存起来，
     * 遇到偶数再用另一个数组存起来就需要2个额外的数组，
     * 最后合并到一个数组里。
     * 方法2：
     * 只申请一个额外的数组，将原来的数组从左往右扫，
     * 遇到奇数就存到新数组的左边，同时将原来的数组从右往左扫，
     * 遇到偶数就存到新数组的右边，这样就可以保证左边是奇数，
     * 右边是偶数，且奇数之间、偶数之间相对位置不变，再合并到原数组。
     */
    public static void move4(int[] array) {
        int start = 0;
        int len = array.length;
        int end = len - 1;

        int[] temp = new int[len];
        for (int i = 0; i < len; ++i) {
            if ((array[i] & 1) == 1) {
                temp[start++] = array[i];
            }

            if ((array[len - 1 - i] & 1) == 0) {
                temp[end--] = array[len - 1 - i];
            }
        }

        for (int i = 0; i < len; ++i) {
            array[i] = temp[i];
        }
    }

    /**
     * 奇数 保持元素的前后相对位置不变 有问题 顺序没法保证
     */
    private static void move3(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            while (left < right && (arr[left] & 1) == 1)//使用位运算判断奇偶
                left++;//arr[left]为奇数，自增直至为偶数为止
            while (left < right && (arr[right] & 1) != 1)
                right--;//arr[right]为偶数，自减直至为奇数为止
            //arr[left]为偶数，arr[right]为奇数，交换
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
    }

    /**
     * 奇数 保持元素的前后相对位置不变 有问题 顺序没发保证
     */
    // int arr1[] = {8, 7, 6, 5, 4, 3, 2, 1};
    private static void move2(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 == 0) {
                int temp = arr[i];
                for (int j = i + 1; j < arr.length; j++) {
                    arr[j - 1] = arr[j];
                }

                arr[arr.length - 1] = temp;

                for (int k = 0; k < arr.length; k++) {
                    System.out.print(arr[k] + "  ");
                }
                System.out.print("\n");
            }
        }
    }

    /**
     * 奇数 保持元素的前后相对位置不变 有问题
     */
    // int arr1[] = {8, 7, 6, 5, 4, 3, 2, 1};
    private static void move1(int[] arr) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0) {
                if (index != i) {
                    int temp = arr[index];
                    arr[index] = arr[i];
                    arr[i] = temp;
                }

                index++;
            }
        }
    }

    /**
     * 非0元素前移 保持元素的前后相对位置不变
     */
    private static void move(int[] arr) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                if (index != i) {
                    arr[index] = arr[i];
                    arr[i] = 0;
                }

                index++;
            }
        }
    }
}


class ReOrder {

    public void reOrderArray(int[] array) {
        if (array.length == 0 || array == null)
            return;
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            if (array[i] % 2 == 1) {            //如果是奇数
                int j = i - 1;
                for (; j >= 0 && array[j] % 2 == 0; j--) {//奇数前面的所有偶数都得后移
                    array[j + 1] = array[j];
                }
                array[j + 1] = temp;
            }
        }
    }

    public void reOrder(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            if (arr[i] % 2 == 1) {//如果是奇数

                for (int j = i; j > 0; j--) {
                    if (arr[j - 1] % 2 == 0) {//奇数前面的所有偶数交换
                        int t = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = t;
                    }
                }
            }
        }
    }
}













