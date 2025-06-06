package org.example.buttons;

import Utilities.ProgressData;
import Utilities.SaveLoadCmds;
import Utilities.TelegramUtils;
import BotCmds.TextCommands;
import org.example.Main;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static Utilities.TelegramUtils.*;

public class Buttons {

    public static void sendMarketsLinks(Update update, int messageID, TelegramLongPollingBot bot){
        String msg = "Наши магазины на маркетплейсах. Будем рады видеть Вас в числе постоянных покупателей и благодарны Вашей обратной связи о нашей продукции! " +
                "Вы помогаете нам развиваться ❤\uFE0F";
        EditMessageText editMessageText = TelegramUtils.editMessage(getChatId(update), messageID,msg);
        List<List<InlineKeyboardButton>> allTheRows = addURLButtons(3,
                new String[][]
                        {new String[]{"WB", "https://www.wildberries.ru/seller/4072161?utm_source=telegram&utm_medium=link&utm_campaign=4072161-id-lecol_bot&utm_term=WB_lecol_shop"},
                                new String[]{"Ozon", "https://ozon.onelink.me/SNMZ/mpysxvwu"},
                                new String[]{"Я.Маркет", "https://market.yandex.ru/business--lekol/106603183?utm_source=telegram&utm_medium=lecol_bot&utm_campaign=link_from_bot&utm_content=yandex_market_shop"}});
        allTheRows.add(addOneRowButton("Назад",TextCommands.backToMenu()));
        EditMessageReplyMarkup et = TelegramUtils.editKeyboard(getChatId(update), messageID, setKeyboardMarkup(allTheRows));
        try{
            bot.execute(editMessageText);
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with editing message to menu");
        }
    }

    public static void sendContacts(Update update, int messageID, TelegramLongPollingBot bot){
        EditMessageText editMessageText = TelegramUtils.editMessage(getChatId(update), messageID, "Напишите нашему менеджеру");
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        allTheRows.add(addOneRowURLButton("Менеджер Лекол", "t.me/lecol_manager"));
        allTheRows.add(addOneRowButton("Назад",TextCommands.backToMenu()));
        EditMessageReplyMarkup et = TelegramUtils.editKeyboard(getChatId(update), messageID, setKeyboardMarkup(allTheRows));
        try{
            bot.execute(editMessageText);
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with editing message to menu");
        }
    }

    public static void sendTelegramChannelLink(Update update, int messageID, TelegramLongPollingBot bot){
        EditMessageText editMessageText = TelegramUtils.editMessage(getChatId(update), messageID,"Переходите в наш канал!");
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        allTheRows.add(addOneRowURLButton("Открыть канал", "t.me/lecol_collagen"));
        allTheRows.add(addOneRowButton("Назад",TextCommands.backToMenu()));
        EditMessageReplyMarkup et = TelegramUtils.editKeyboard(getChatId(update), messageID, setKeyboardMarkup(allTheRows));
        try{
            bot.execute(editMessageText);
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with editing message to menu");
        }
    }

    public static void sendWebpageLink(Update update, int messageID, TelegramLongPollingBot bot){
        EditMessageText editMessageText = TelegramUtils.editMessage(getChatId(update),messageID,"Посетите наш сайт!");
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        allTheRows.add(addOneRowURLButton("Открыть сайт", "https://clck.ru/3DLAXp"));
        allTheRows.add(addOneRowButton("Назад",TextCommands.backToMenu()));
        EditMessageReplyMarkup et = TelegramUtils.editKeyboard(getChatId(update), messageID, setKeyboardMarkup(allTheRows));
        try{
            bot.execute(editMessageText);
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with editing message to menu");
        }
    }

    public static SendMessage permanentMenu(Update update, TelegramLongPollingBot bot){
        ProgressData progressData = SaveLoadCmds.getUserProgressData(getChatId(update));
        ReplyKeyboardMarkup permanentMenu = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(TextCommands.menu());
        keyboard.add(firstRow);
        permanentMenu.setResizeKeyboard(true);
        permanentMenu.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText("Меню всегда доступно по кнопке внизу");
        message.setReplyMarkup(permanentMenu);
        try{
           bot.execute(message);
           return message;
        } catch(TelegramApiException e){
            System.out.println("Something went wrong");
        } return null;
    }
}
