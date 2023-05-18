package org.helpers;

import java.util.ArrayList;

public class Utils {

    public static int[][] ArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        //Превращает список int в матрицу 20x20 (Для создания уровня)
        int[][] newArr = new int[ySize][xSize];

        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index);
            }
        }

        return newArr;
    }
    public static int[] TwoDToIntArr(int[][] twoArr) {
        //Тоже самое, что и прошлый метод, но наоборот
        int[] oneArr = new int[twoArr.length * twoArr[0].length];
        for (int j = 0; j < twoArr.length; j++) {
            for (int i = 0; i < twoArr[j].length; i++) {
                int index = j * twoArr.length + i;
                oneArr[index] = twoArr[j][i];
            }
        }
        return oneArr;
    }

    public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
        //Находит расстояние между двумя точками
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff,yDiff);
    }
}
