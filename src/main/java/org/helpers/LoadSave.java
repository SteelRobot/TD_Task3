package org.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    public static void CreateFile() {

        File txtFile = new File("src/main/resources/testTextFile.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CreateLevel(String name, int[] idArr) {
        File newLevel = new File("src/main/resources/" + name + ".txt");
        if (newLevel.exists()) {
            System.out.println("File: " + name + " exists.");
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(newLevel, idArr);
        }
    }

    private static void WriteToFile(File f, int[] idArr) {
        try {
            PrintWriter pw = new PrintWriter(f);

            for (int i : idArr) pw.println(i);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void SaveLevel(String name, int[][] idArr) {
        File levelFile = new File("src/main/resources/" + name + ".txt");
        if (levelFile.exists()) {
            WriteToFile(levelFile, Utils.TwoDToIntArr(idArr));
        } else {
            System.out.println("File: " + name + " exists.");
            return;
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
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

    public static int[][] GetLevelData(String name) {
        File lvlFile = new File("src/main/resources/" + name + ".txt");

        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utils.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File does not exist");
            return null;
        }
    }
}
