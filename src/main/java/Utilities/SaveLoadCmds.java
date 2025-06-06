package Utilities;

import UserRelated.User;
import org.example.Main;

import java.util.HashMap;

public class SaveLoadCmds {
    public static ProgressData getUserProgressData(Long chatId) {
        try {
            HashMap<Long, ProgressData> userMap = Progress.loadProgress(() -> new HashMap<>());
            ProgressData progressData = userMap.get(chatId);
            if (progressData == null) {
                return new ProgressData();
            } else {
                return progressData;
            }
        } catch (Exception exc) {
            System.out.println("Something went wrong with reading the progress from file or file missing ->");
            System.out.println("->Progress set to default");
            return new ProgressData();
        }
    }

    public static HashMap<Long, User> getUsersDB() {
        try {
            HashMap<Long, User> userDB = Progress.loadUsersDataBase();
            return userDB;
        } catch (Exception exc) {
            System.out.println("Something went wrong with reading the database from file or file missing ->");
            System.out.println("->Database set to empty");
            return new HashMap<>();
        }
    }

    public static void saveUsersDB() {
        Progress.saveUsersDataBase(Main.usersDataBase);
    }

    public static void saveCertainUserProgressFile(Long chatId, ProgressData progressData) {
        HashMap<Long, ProgressData> userMap = Progress.loadProgress(() -> new HashMap<>());
        userMap.put(chatId, progressData);
        Progress.saveProgress(userMap);
    }

    public static void saveProgressFile(Long chatId, ProgressData progressData) {
        HashMap<Long, ProgressData> userMap = Progress.loadProgress(() -> new HashMap<>());
        userMap.put(chatId, progressData);
        Progress.saveProgress(userMap);
    }
}
