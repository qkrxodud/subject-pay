package kakaopay.kakaopay.utils;

import java.util.Scanner;

public class ArraySearch {

    static int [][] arr;
    static int xArrLen;
    static int yArrLen;
    static String[] splitStr;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        xArrLen = in.nextInt();
        yArrLen = in.nextInt();

        String arrValue = in.next();
        splitStr = arrValue.split(",");

        String answer = "";

        // 초기 설정 값
        initData();

        //상태값의 수
        for (int i = 0; i < xArrLen; i++) {
            int cnt = 0;
            // 배열 갯수 1개씩 더 많다.
            for (int j = 0; j < i+1; j++) {
                // 상태값의 반향
                if (isOdd(i)) {
                    answer += arr[cnt][i-cnt] +",";
                } else if (isEven(i)) {
                    answer += arr[i-cnt][cnt] +",";
                }
                cnt++;
            }
        }

        //상태값의 횟수
        for (int i = 0; i < xArrLen-1; i++) {
            int cnt = 1;
            // 배열 갯수 1개씩 더 작다.
            for (int j = 0; j < xArrLen-i-1; j++) {
            // 상태값의 반향
                if (isOdd(i + xArrLen)) {
                    answer += arr[i+cnt][xArrLen-cnt] +",";
                } else if (isEven(i + xArrLen)) {
                    answer += arr[xArrLen-cnt][i+cnt] +",";
                }
                cnt++;
            }
        }
        System.out.println(answer);
    }

    // 초기데이터 값 셋팅
    static void initData() {
        int row = 0;
        arr = new int[xArrLen][yArrLen];
        for (int i = 0; i < splitStr.length; i++) {
            if (i != 0 && i % xArrLen == 0) {
                row++;
            }
            arr[row][i % xArrLen] = Integer.parseInt(splitStr[i]);
        }
    }

    // 짝 구분`
    static boolean isEven(int n) {
        if (n%2 == 0) {
            return true;
        }
        return false;
    }

    // 홀 구분
    static boolean isOdd(int n) {
        if (n%2 != 0) {
            return true;
        }
        return false;
    }
}
