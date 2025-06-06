package UserRelated;

import BotCmds.RoleRelatedCommands;
import Utilities.TelegramUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserServiceMessages {

    public static File image1 = Paths.get("image.png").toFile();
    public static InputFile photo = new InputFile(image1, "image.png");
    public static void sendHelloMessage(Update update, String localName, TelegramLongPollingBot bot){
        TelegramUtils.printMessageOut(update, ("Здравствуйте, " + localName + "!\n" + "Добро пожаловать в наш бот! " +
                "Здесь Вы сможете ознакомиться с актуальными акциями, а также связаться с нами!"), bot);
    }

    public static void sendEventRules(Update update, TelegramLongPollingBot bot) {
        TelegramUtils.sendImage(update,new InputFile("AgACAgIAAxkDAAIDymboN3f5J36MtvREFgToAyEjPQmyAAID4zEb_BNBS5K6NXA2eATqAQADAgADeQADNgQ"),bot);
        TelegramUtils.printMessageWithPause(update,(StringsForMessages.message1()),bot, 0);
        TelegramUtils.printMessageWithPause(update,(StringsForMessages.message2()),bot, 5000);
        TelegramUtils.printMessageWithPause(update,(StringsForMessages.message3()),bot, 5000);
        List<MessageEntity> entities = new ArrayList<>();
        MessageEntity entity = new MessageEntity("text_link",105, 14, "http://t.me/lecol_collagen", null, null,null, "Telegram-канал");
        entities.add(entity);
        TelegramUtils.printFormattedMessageOut(update,(StringsForMessages.message4()),entities,bot);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TelegramUtils.printMessageWithPause(update,(StringsForMessages.message5()),bot, 5000);
    }
}
