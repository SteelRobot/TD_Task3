package org.helpers;

import org.objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.helpers.Constants.Temp.lvlName;

public class LoadSave {

    public static BufferedImage getSpriteAtlas() {
        // Достаёт из папки resources png со всеми спрайтами
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

//    public static void CreateFile() {
//
//        File txtFile = new File("src/main/resources/testTextFile.txt");
//        try {
//            txtFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void CreateLevel(String name, int[] idArr) {
        //Создаёт новый уровень с названием. Если уже существует, то ничего не делает
        File newLevel = new File("src/main/resources/" + lvlName + ".txt");
        if (newLevel.exists()) {
            System.out.println("Файл: " + lvlName + " существует");
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WriteToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    private static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end) {
        //Записывает в файл ID каждой клетки, а также позицию появляения врагов и позицию базы
        // (4 значения в конце, для каждой координаты)
        try {
            PrintWriter pw = new PrintWriter(f);

            for (int i : idArr)
                pw.println(i);
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        //Сохраняет уровень при нажатии на кнопку "Сохранить"
        File levelFile = new File("src/main/resources/" + lvlName + ".txt");
        WriteToFile(levelFile, Utils.TwoDToIntArr(idArr), start, end);
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        //Считывает информацию из txt файла уровня
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoints(String name) {
        //Находит координаты появления врагов и базы
        File lvlFile = new File("src/main/resources/" + lvlName + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("Файла не существует");
            return null;
        }
    }

    public static int[][] GetLevelData(String name) {
        //Считывает данные каждой клетки из txt файла
        File lvlFile = new File("src/main/resources/" + lvlName + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utils.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("Файла не существует");
            return null;
        }
    }
}
