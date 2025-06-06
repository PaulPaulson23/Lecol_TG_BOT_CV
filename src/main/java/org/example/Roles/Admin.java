package org.example.Roles;

import BotCmds.TextCommands;
import UserRelated.User;
import UserRelated.UserType;
import Utilities.Progress;
import Utilities.ProgressData;
import Utilities.SaveLoadCmds;
import Utilities.TelegramUtils;
import BotCmds.RoleRelatedCommands;
import org.example.BotState;
import org.example.Main;
import org.example.Receivable;
import org.example.buttons.Buttons;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static BotCmds.TextCommands.initiateMessageForUsers;
import static BotCmds.TextCommands.sendNews;
import static Utilities.SaveLoadCmds.getUsersDB;
import static Utilities.TelegramUtils.*;

public class Admin implements Receivable, Serializable {

    BotState botState;


    @Override
    public void onUpdateReceived(Update update, TelegramLongPollingBot bot) {

        ProgressData progressData = SaveLoadCmds.getUserProgressData(TelegramUtils.getChatId(update));
        botState = progressData.getState();
        SaveLoadCmds.saveUsersDB(); //TODO: solve this issue
        UserRelated.User thisUser = progressData.getUserData();
        //Buttons.permanentMenu(update, bot);
        if((TelegramUtils.isCallback(update, TextCommands.menu()) || TelegramUtils.isTextMessage(update, TextCommands.menu())) ||
                (TelegramUtils.isCallback(update, TextCommands.cancel()) || TelegramUtils.isTextMessage(update, TextCommands.cancel()))){
            botState = BotState.DEFAULT;
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            if(TelegramUtils.isCallback(update, TextCommands.cancel()) || TelegramUtils.isTextMessage(update, TextCommands.cancel())){
                printMessageOut(update,"Вы отменили операцию", bot);
            }
            RoleRelatedCommands.sendMainMenu(update, bot, TelegramUtils.checkUserType(update));
        } else if(TelegramUtils.isCallback(update, TextCommands.initiateMessageForUsers()) || TelegramUtils.isTextMessage(update, TextCommands.initiateMessageForUsers())){
            botState = BotState.ADMIN_SET_MESSAGE_FOR_SUBS;
            progressData.setState(botState);
            progressData.setServiceMessage(null);
            printMessageOut(update,"Введите сообщение для подписчиков бота", bot);
            sendCancelButton(update,bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
        } else if(botState == BotState.ADMIN_SET_MESSAGE_FOR_SUBS){
            receiveMessageForSubscribers(update,progressData,botState,bot,checkUserType(update));
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
        }
        else if(TelegramUtils.isCallback(update, TextCommands.sendNews()) || TelegramUtils.isTextMessage(update, TextCommands.sendNews())){
            sendMessageToSubscribers(update,progressData,botState,bot,checkUserType(update));
        } else if(TelegramUtils.isCallback(update, TextCommands.sendDatabase()) || TelegramUtils.isTextMessage(update, TextCommands.sendDatabase())){
            botState = BotState.ADMIN_SENDDATABASE;
            printMessageOut(update, "Статистика выгружена в файл ниже", bot);
            sendStatsAsCSV(update, bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
        } else if(TelegramUtils.isCallback(update, TextCommands.sendUsersCount()) || TelegramUtils.isTextMessage(update, TextCommands.sendUsersCount())){
            //botState = BotState.ADMIN_SEND_USER_COUNTER;
            sendUsersCounters(update,  bot);
            // TODO: send counters of all users, active users, non-active users and admins
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            RoleRelatedCommands.sendMainMenu(update, bot, TelegramUtils.checkUserType(update));

        } else if((TelegramUtils.isTextMessage(update,"/sdf-90jc208hv"))){
            thisUser.setRole(UserType.USER);
            progressData.setUserData(thisUser);
            Main.usersDataBase.put(TelegramUtils.getChatId(update), thisUser);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.printMessageOut(update,"Режим пользователя активирован.", bot);
            RoleRelatedCommands.sendMainMenu(update, bot, UserType.USER);
        }
    }

    private static void receiveMessageForSubscribers(Update update,ProgressData progressData, BotState botState, TelegramLongPollingBot bot,UserType type){
        String msg = update.hasCallbackQuery() ? (update.getCallbackQuery().getData()) : (update.getMessage().getText());
        if (msg.equalsIgnoreCase("отмена")){
            botState = BotState.ADMIN_DEFAULT;
            progressData.setState(botState);
            printMessageOut(update,"Вы отменили создание сообщения для подписчиков бота", bot);
            RoleRelatedCommands.sendMainMenu(update, bot, type);
        } else {
            boolean errorsFound = TelegramUtils.lookForAndPrintErrors(update,msg,bot);
            if(!errorsFound){
                botState = BotState.DEFAULT;
                progressData.setState(botState);
                progressData.setServiceMessage(msg);
                printMessageOut(update,"Вы планируете отправить следующее сообщение:", bot);
                printMessageOut(update, msg, bot);
                sendButtons(update,bot,"Вас всё устраивает или хотите изменить сообщение?",
                        addTwoButtonsRow("Да, отправить",TextCommands.sendNews(),
                                "Нет, исправить", TextCommands.initiateMessageForUsers()));
                sendCancelButton(update,bot);
                SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
            }
        }
        SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
    }

    private static void sendMessageToSubscribers(Update update, ProgressData progressData,  BotState botState, TelegramLongPollingBot bot, UserType type){
        String msg = progressData.getServiceMessage();
        boolean errorsFound = TelegramUtils.lookForAndPrintErrors(update,msg,bot);
        if(!errorsFound){
            HashMap<Long, User> usersMap = getUsersDB();
            for (Map.Entry<Long, User> user : usersMap.entrySet()){
                if(user.getValue().getType() == UserType.USER){
                    TelegramUtils.sendMessageToUser(user.getKey(),bot,msg);
                } else {
                    TelegramUtils.sendMessageToUser(user.getKey(),bot,"Подписчики бота получили следующее сообщение:");
                    TelegramUtils.sendMessageToUser(user.getKey(),bot,msg);
                }
            }
            progressData.setServiceMessage(null);
            botState = BotState.ADMIN_DEFAULT;
            progressData.setState(botState);
            SaveLoadCmds.saveProgressFile(getChatId(update), progressData);
            RoleRelatedCommands.sendMainMenu(update,bot,type);
        }
    }

    private static void sendUsersCounters(Update update, TelegramLongPollingBot bot){
        HashMap<Long, User> usersDataBase = SaveLoadCmds.getUsersDB();
        String msg;
        int commonUsers = 0;
        int admins = 0;
        int users = 0;
        for (Map.Entry<Long, User> user : usersDataBase.entrySet()){
            users++;
            if(user.getValue().getType() == UserType.USER){
                commonUsers++;
            } else if (user.getValue().getType() == UserType.ADMIN){
                admins++;
            }
        }
        msg = "Общее число пользователей: " + users + "\n"
                + "Простые пользователи: " + commonUsers + "\n"
                + "Администраторы: " + admins;
        printMessageOut(update,msg,bot);
    }

    private static void sendStatsAsCSV(Update update, TelegramLongPollingBot bot){
        InputFile CSVStats = new InputFile();
        File dataInCSV = new File("data.csv");
        CSVStats.setMedia(dataInCSV);
        SendDocument sendDocument = new SendDocument(getChatId(update).toString(), CSVStats);
        try{
            bot.execute(sendDocument);
        } catch (TelegramApiException e) {
            System.out.println("File was not sent due to exception caught");
            throw new RuntimeException(e);
        }
    }
}
