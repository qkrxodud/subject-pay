package kakaopay.kakaopay.utils;

import java.util.Scanner;

public class ArraySetting {
    static int[][] arr;
    static int xArrLen;
    static int yArrLen;
    static String[] splitStr;
    static int x;
    static int y;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        xArrLen = in.nextInt();
        yArrLen = in.nextInt();

        String arrValue = in.next();
        splitStr = arrValue.split(",");

        String rotations = in.next();
        String coordinates = in.next();
        String[] split = coordinates.split(",");

        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);

        // 초기 데이터 값 셋팅
        initData();

        // 명령어 실행
        for (char rotation : rotations.toCharArray()) {
            exec(rotation);
        }

        // 결과값 노출
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println(" ");
        }

        // 해답
        // 1 ,3 ==> 1이라는 가정하에 작성하였습나다.
        System.out.println(arr[y - 1][x - 1]);

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

    // 명령어 실행
    private static void exec(char rotation) {
        if (rotation == 'R') {
            rightRotate(xArrLen);
        } else if (rotation == 'L') {
            leftRotate(xArrLen);
        } else if (rotation == 'T') {
            reversRotate(xArrLen);
        }
    }

    // 오른쪽으로 돌림
    static void rightRotate(int n) {
        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = arr[n - 1 - j][i];
            }
        }
        arr = temp;
    }

    // 왼쪽으로 돌림
    static void leftRotate(int n) {
        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = arr[j][n - 1 - i];
            }
        }
        arr = temp;
    }

    //뒤집기
    static void reversRotate(int n) {
        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = arr[i][n - j - 1];
            }
        }
        arr = temp;
    }
}
