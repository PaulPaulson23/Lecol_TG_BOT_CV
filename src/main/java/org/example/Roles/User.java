package org.example.Roles;

import UserRelated.UserType;
import Utilities.Progress;
import Utilities.ProgressData;
import Utilities.SaveLoadCmds;
import Utilities.TelegramUtils;
import BotCmds.RoleRelatedCommands;
import BotCmds.TextCommands;
import org.example.BotState;
import org.example.Main;
import org.example.Receivable;
import UserRelated.UserServiceMessages;
import org.example.buttons.Buttons;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class User implements Receivable {

    BotState botState;


    @Override
    public void onUpdateReceived(Update update, TelegramLongPollingBot bot) {
        ProgressData progressData = SaveLoadCmds.getUserProgressData(TelegramUtils.getChatId(update));
        int messageID = progressData.getMessageID();
        botState = progressData.getState();
        UserRelated.User thisUser = progressData.getUserData();
        SaveLoadCmds.saveUsersDB(); //TODO: solve this issue
        if((TelegramUtils.isTextMessage(update,"/42309fjh957fgb"))){
            thisUser.setRole(UserType.ADMIN);
            progressData.setUserData(thisUser);
            Main.usersDataBase.put(TelegramUtils.getChatId(update), thisUser);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.printMessageOut(update,"Режим администратора активирован. Добро пожаловать.", bot);
            RoleRelatedCommands.sendMainMenu(update, bot, UserType.ADMIN);
        } else if(!progressData.hasStartedBot() || (TelegramUtils.isCallback(update,"/start") || TelegramUtils.isTextMessage(update,"/start"))){
            progressData.startedBot();
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            UserServiceMessages.sendHelloMessage(update, thisUser.getLocalName(), bot);
            Buttons.permanentMenu(update, bot);
            RoleRelatedCommands.sendMainMenu(update, bot, TelegramUtils.checkUserType(update));
            TelegramUtils.saveCSV();
        } else if(TelegramUtils.isCallback(update,"/backtomenu") || TelegramUtils.isTextMessage(update,"/backtomenu")){
            TelegramUtils.returnMenu(update,bot, messageID);
        } else if(TelegramUtils.isCallback(update, TextCommands.menu()) || TelegramUtils.isTextMessage(update, TextCommands.menu())){
            botState = BotState.DEFAULT;
            RoleRelatedCommands.sendMainMenu(update, bot, TelegramUtils.checkUserType(update));
        } else if(TelegramUtils.isCallback(update, TextCommands.sendRules()) || TelegramUtils.isTextMessage(update, TextCommands.sendRules())){
            botState = BotState.ON_RULES_MSG;
            progressData.setState(botState);
            progressData.setPressedAboutEvent();
            UserServiceMessages.sendEventRules(update,bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.saveCSV();
            RoleRelatedCommands.sendMainMenu(update, bot, TelegramUtils.checkUserType(update));
        } else if(TelegramUtils.isCallback(update, TextCommands.sendTelegramChannelLink()) || TelegramUtils.isTextMessage(update, TextCommands.sendTelegramChannelLink())){
            botState = BotState.LINK_TO_TELEGRAM_CHANNEL_SENT;
            progressData.setState(botState);
            progressData.setPressedLinkToTG();
            Buttons.sendTelegramChannelLink(update, messageID, bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.saveCSV();
        } else if(TelegramUtils.isCallback(update, TextCommands.sendShopsLinks()) || TelegramUtils.isTextMessage(update, TextCommands.sendShopsLinks())){
            botState = BotState.SHOPS_LINKS_SENT;
            progressData.setState(botState);
            progressData.setPressedSendMeShops();
            Buttons.sendMarketsLinks(update, messageID, bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.saveCSV();
        } else if(TelegramUtils.isCallback(update, TextCommands.sendContacts()) || TelegramUtils.isTextMessage(update, TextCommands.sendContacts())){
            botState = BotState.CONTACTS_SENT;
            progressData.setState(botState);
            progressData.setPressedSendMeContacts();
            Buttons.sendContacts(update, messageID, bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.saveCSV();
        } else if(TelegramUtils.isCallback(update, TextCommands.sendWebpageLink()) || TelegramUtils.isTextMessage(update, TextCommands.sendWebpageLink())){
            botState = BotState.WEBPAGE_LINK_SENT;
            progressData.setState(botState);
            progressData.setPressedLinkToWebpage();
            Buttons.sendWebpageLink(update, messageID, bot);
            SaveLoadCmds.saveProgressFile(TelegramUtils.getChatId(update),progressData);
            TelegramUtils.saveCSV();
        }

    }
}
