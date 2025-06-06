package BotCmds;

import UserRelated.UserType;
import Utilities.ProgressData;
import Utilities.SaveLoadCmds;
import Utilities.TelegramUtils;
import org.example.Main;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static Utilities.TelegramUtils.addOneRowButton;
import static Utilities.TelegramUtils.getChatId;


// This class file contains methods that implement some logic according to the role of its user-caller,
// e.g. "Send menu" method has different buttons for "Worker" and "Supervisor",
// hence why this method is located here.

public class RoleRelatedCommands {

    public static SendMessage sendMainMenu(Update update, TelegramLongPollingBot bot, UserType type){
        ProgressData progressData = SaveLoadCmds.getUserProgressData(getChatId(update));
        int messageID = progressData.getMessageID();
        int keyboardMessageID = progressData.getKeyboardMessageID();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        if(type == UserType.USER) {
            message.setText("Выберите интересующий пункт ниже ⬇\uFE0F");
            allTheRows.add(addOneRowButton("Как получить подарок?", TextCommands.sendRules()));
            allTheRows.add(addOneRowButton("Наш канал в TG", TextCommands.sendTelegramChannelLink()));
            allTheRows.add(addOneRowButton("Купить наши товары",TextCommands.sendShopsLinks()));
            allTheRows.add(addOneRowButton("Наш сайт", TextCommands.sendWebpageLink()));
            allTheRows.add(addOneRowButton("Связь с нами", TextCommands.sendContacts()));
        } else if (type == UserType.ADMIN) {
            message.setText("Выберите необходимую операцию ⬇\uFE0F");
            allTheRows.add(addOneRowButton("Прислать выгрузку", TextCommands.sendDatabase()));
            allTheRows.add(addOneRowButton("Отправить сообщение", TextCommands.initiateMessageForUsers()));
            allTheRows.add(addOneRowButton("Кол-во пользователей", TextCommands.sendUsersCount()));
            //allTheRows.add(addOneRowButton("", TextCommands.()));
        }
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            messageID = bot.execute(message).getMessageId();
            keyboardMessageID = messageID;
            progressData.setMessageID(messageID);
            progressData.setKeyboardMessageID(keyboardMessageID);
            SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
            SaveLoadCmds.saveUsersDB();
            return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        } return null;
    }
}
