package org.example;
import UserRelated.User;
import Utilities.SaveLoadCmds;
import Utilities.TelegramUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Main extends TelegramLongPollingBot{


    public static HashMap<Long, User> usersDataBase = SaveLoadCmds.getUsersDB();

    public static LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));


    public static void main(String[] args) {
        try {
            TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(new Main());
        } catch (TelegramApiException exc) {
            System.out.println("Problem with registering the bot!");
        }
    }

    @Override
    public String getBotUsername() {
        return "Lecol_bot";
    }

    @Override
    public String getBotToken() {
        return "ENTER TOKEN FROM BOT_FATHER HERE";
    }


    @Override
    public void onUpdateReceived(Update update) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy*HH:mm:ss");
        System.out.println(now.format(dateTimeFormatter));
        System.out.println(update);
        TelegramUtils.registerUser(update);
        TelegramUtils.roleImplementer(update, this);
    }

}