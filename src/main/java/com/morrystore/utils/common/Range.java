package com.morrystore.utils.common;

public class Range {

    /**
     * 快速生成 start 到 end 的数组（包含start , 不包含 end）<br>
     * eg: Range.init(3,6)  ->  [3,4,5] <br>
     * 用途如for循环： <br>
     * for(int i : Range.init(1,5)) {  <br>
     *     System.out.println(i);  <br>
     * }<br>
     * @param start 数组开始的值
     * @param end 数组结束的值
     * @return
     */
    public static int[] init(int start, int end) {
        if(start < 0 || start > end || end > Integer.MAX_VALUE) {
            return new int[0];
        }
        int[] arr = new int[end-start + 1];
        for(int i = start; i < end; i++) {
            arr[i - start] = i;
        }
        return arr;
    }
}