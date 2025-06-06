package Utilities;
import UserRelated.User;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Progress implements Serializable{

    public static <T> void saveProgress(T progressData){
        try{
            FileOutputStream fio = new FileOutputStream("progress.csv");
            ObjectOutputStream oos = new ObjectOutputStream(fio);
            oos.writeObject(progressData);
            System.out.println("Progress file saved");
            oos.close();
            fio.close();
        } catch (Exception ex){
            System.out.println("Error with saving progress file");
            ex.printStackTrace();
        }
    }

    public static void saveProgressAsCSV(String file, String name){
        try{
            Files.writeString(Paths.get(name + ".csv"), file, Charset.forName("windows-1251"));
            System.out.println("Progress file saved");
        } catch (Exception ex){
            System.out.println("Error with saving progress file");
            ex.printStackTrace();
        }
    }

    public static <T> void saveUsersDataBase(T dataBase){
        try{
            FileOutputStream fio = new FileOutputStream("database.txt");
            //PrintWriter pw = new PrintWriter();
            ObjectOutputStream oos = new ObjectOutputStream(fio);
            oos.writeObject(dataBase);
            System.out.println("Users database saved");
            oos.close();
            fio.close();
        } catch (Exception ex){
            System.out.println("Error with saving users database file");
            ex.printStackTrace();
        }
    }

    public static HashMap<Long, User> loadUsersDataBase(){
        try {
            FileInputStream fio = new FileInputStream("database.txt");
            ObjectInputStream ois = new ObjectInputStream(fio);
            System.out.println("Database file loaded");
            return (HashMap<Long, User>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }


    public static <T> T loadProgress(GetEmptyProgressData<T> getEmptyProgressData){
        try {
            FileInputStream fio = new FileInputStream("progress.csv");
            ObjectInputStream ois = new ObjectInputStream(fio);
            System.out.println("Progress file loaded");
            return (T) ois.readObject();
        } catch (Exception e) {
            return getEmptyProgressData.getEmptyVersion();
        }
    }
}