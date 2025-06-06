package Utilities;

import UserRelated.User;
import UserRelated.UserType;
import BotCmds.TextCommands;
import org.example.Main;
import org.example.Receivable;
import org.example.Roles.Admin;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class TelegramUtils {

    public static Long getChatId(Update update){
        if(update.getMessage() != null){
            if(!update.hasCallbackQuery()){
                Long userID = update.getMessage().getChatId();
                return userID;
            }
            else {
                Long userID = update.getCallbackQuery().getMessage().getChatId();
                return userID;
            }
        } else if(!update.hasCallbackQuery()){
            Long userID = update.getMessage().getChatId();
            return userID;
        }
        else {
            Long userID = update.getCallbackQuery().getMessage().getChatId();
            return userID;
        }
    }

    public static boolean isTextMessage(Update update, String command) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals(command);
    }

    public static boolean isCallback(Update update, String callbackName) {
        return update.hasCallbackQuery()
                && update.getCallbackQuery().getData().equals(callbackName);
    }

    public static List<InlineKeyboardButton> addOneRowButton(String buttonText, String callbackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callbackData);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    public static List<InlineKeyboardButton> addOneRowURLButton(String buttonText, String callbackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setUrl(callbackData);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    public static List<InlineKeyboardButton> addTwoButtonsRow(String buttonText, String callbackData, String buttonText2, String callbackData2){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callbackData);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(buttonText2);
        button2.setCallbackData(callbackData2);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        row.add(button2);
        return row;
    }


    public static List<List<InlineKeyboardButton>> addTextButtons(int buttonsInARow, String[][] contents){
        List<List<InlineKeyboardButton>> result = new ArrayList<>();
        int count = Stream.of(contents).mapToInt(m -> m.length).sum();
        int rows = contents.length/buttonsInARow;
        if(rows == 1){
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < buttonsInARow; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setCallbackData(contents[i][1]);
                row.add(button);
            }
            result.add(row);
            return result;
        } else if (rows > 2 && rows % 2 != 0){
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < contents.length-1; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setCallbackData(contents[i][1]);
                row.add(button);
                if(row.size() == buttonsInARow){
                    List<InlineKeyboardButton> tempRow = new ArrayList<>();
                    tempRow.addAll(row);
                    result.add(tempRow);
                    row.clear();
                }
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(contents[contents.length-1][0]);
            button.setCallbackData(contents[contents.length-1][1]);
            row.add(button);
            result.add(row);
            return result;
        } else if(rows > 1) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < contents.length; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setCallbackData(contents[i][1]);
                row.add(button);
                if(row.size() == buttonsInARow){
                    List<InlineKeyboardButton> tempRow = new ArrayList<>();
                    tempRow.addAll(row);
                    result.add(tempRow);
                    row.clear();
                }
            } return result;
        }
        return null;
    }

    public static List<List<InlineKeyboardButton>> addURLButtons(int buttonsInARow, String[][] contents){
        List<List<InlineKeyboardButton>> result = new ArrayList<>();
        int count = Stream.of(contents).mapToInt(m -> m.length).sum();
        int rows = contents.length/buttonsInARow;
        if(rows == 1){
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < buttonsInARow; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setUrl(contents[i][1]);
                row.add(button);
            }
            result.add(row);
            return result;
        } else if (rows > 2 && rows % 2 != 0){
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < contents.length-1; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setUrl(contents[i][1]);
                row.add(button);
                if(row.size() == buttonsInARow){
                    List<InlineKeyboardButton> tempRow = new ArrayList<>();
                    tempRow.addAll(row);
                    result.add(tempRow);
                    row.clear();
                }
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(contents[contents.length-1][0]);
            button.setUrl(contents[contents.length-1][1]);
            row.add(button);
            result.add(row);
            return result;
        } else if(rows > 1) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < contents.length; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(contents[i][0]);
                button.setUrl(contents[i][1]);
                row.add(button);
                if(row.size() == buttonsInARow){
                    List<InlineKeyboardButton> tempRow = new ArrayList<>();
                    tempRow.addAll(row);
                    result.add(tempRow);
                    row.clear();
                }
            } return result;
        }
        return null;
    }


    public static SendMessage sendSeveralButtons(Update update, TelegramLongPollingBot bot, String header, List<InlineKeyboardButton>...args){
        ProgressData progressData = SaveLoadCmds.getUserProgressData(getChatId(update));
        int keyboardMessageID = progressData.getKeyboardMessageID();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        for (List<InlineKeyboardButton> arg : args) {
            allTheRows.add(arg);
        }
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
           keyboardMessageID = bot.execute(message).getMessageId();
           progressData.setKeyboardMessageID(keyboardMessageID);
           SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
           return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return null;
    }

    public static SendMessage sendKeyboard(Update update, TelegramLongPollingBot bot, String header, List<List<InlineKeyboardButton>> keyboard){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            bot.execute(message);
            return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return null;
    }

    public static int sendButtonsReturningMessageID(Update update, TelegramLongPollingBot bot, String header, List<InlineKeyboardButton>...args){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        for (List<InlineKeyboardButton> arg : args) {
            allTheRows.add(arg);
        }
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            int messageID = bot.execute(message).getMessageId();
            return messageID;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return 0;
    }

    public static int sendKeyboardReturningMessageID(Update update, TelegramLongPollingBot bot, String header, List<List<InlineKeyboardButton>> keyboard){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            int messageID = bot.execute(message).getMessageId();
            return messageID;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return 0;
    }

    public static int sendButtonsReturningMessageID(Update update, TelegramLongPollingBot bot, String header, List<List<InlineKeyboardButton>> allTheRows){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            int messageID = bot.execute(message).getMessageId();
            return messageID;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return 0;
    }

    public static SendMessage sendButtons(Update update, TelegramLongPollingBot bot, String header, List<InlineKeyboardButton>...args){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        for (List<InlineKeyboardButton> arg : args) {
            allTheRows.add(arg);
        }
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            bot.execute(message);
            return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return null;
    }

    public static SendMessage sendYesNo(Update update, TelegramLongPollingBot bot, String header){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> result = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton yes = new InlineKeyboardButton("Да");
        yes.setCallbackData("Да");
        InlineKeyboardButton no = new InlineKeyboardButton("Нет");
        no.setCallbackData("Нет");
        row.add(yes);
        row.add(no);
        result.add(row);
        inlineKeyboardMarkup.setKeyboard(result);
        SendMessage message = new SendMessage();
        message.setChatId(TelegramUtils.getChatId(update));
        message.setText(header);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try{
            bot.execute(message);
            return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return null;
    }

    public static EditMessageText editMessage(Long chatId, int messageId, String message){
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(message);
        return newMessage;
    }

    public static EditMessageReplyMarkup editKeyboard(Long chatId, int messageId, InlineKeyboardMarkup keyboardMarkup){
        EditMessageReplyMarkup newReplyMarkup = new EditMessageReplyMarkup();
        newReplyMarkup.setChatId(chatId);
        newReplyMarkup.setMessageId(messageId);
        newReplyMarkup.setReplyMarkup(keyboardMarkup);
        return newReplyMarkup;
    }

    public static InlineKeyboardMarkup setKeyboardMarkup(List<List<InlineKeyboardButton>> allButtons){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(allButtons);
        return inlineKeyboardMarkup;
    }

    public static SendMessage sendCancelButton(Update update, TelegramLongPollingBot bot){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton cancelBt = new InlineKeyboardButton();
        cancelBt.setText("Отмена");
        cancelBt.setCallbackData(TextCommands.cancel());
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(cancelBt);
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        allTheRows.add(firstRow);
        inlineKeyboardMarkup.setKeyboard(allTheRows);
        SendMessage message = new SendMessage();
        message.setChatId(getChatId(update));
        message.setText("Если вы хотите отменить эту операцию, нажмите кнопку");
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            bot.execute(message);
            return message;
        } catch (Exception exception){
            System.out.println("Something went wrong");
        }
        return null;
    }

    public static void printMessageOut(Update update, String text, TelegramLongPollingBot bot){
        ProgressData progressData = SaveLoadCmds.getUserProgressData(getChatId(update));
        int messageID = progressData.getMessageID();
        int keyboardMessageID = progressData.getKeyboardMessageID();
        SendMessage msg = new SendMessage();
        msg.setText(text);
        msg.setChatId(getChatId(update));
        msg.enableHtml(true);
        try {
            keyboardMessageID = bot.execute(msg).getMessageId();
            messageID = keyboardMessageID;
            progressData.setKeyboardMessageID(keyboardMessageID);
            progressData.setMessageID(messageID);
            SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
        } catch (Exception exc) {
            System.out.println("Something has gone wrong");
        }
    }

    public static void printFormattedMessageOut(Update update, String text, List<MessageEntity> entities, TelegramLongPollingBot bot){
        SendMessage msg = new SendMessage();
        msg.setText(text);
        msg.setEntities(entities);
        msg.setChatId(getChatId(update));
        msg.enableHtml(false);
        try {
            bot.execute(msg);
        } catch (Exception exc) {
            System.out.println("Something has gone wrong");
        }
    }

    public static void printMessageWithPause(Update update, String text, TelegramLongPollingBot bot, int pauseInMilSecs){
        ProgressData progressData = SaveLoadCmds.getUserProgressData(getChatId(update));
        int keyboardMessageID = progressData.getKeyboardMessageID();
        SendMessage msg = new SendMessage();
        msg.setText(text);
        msg.setChatId(getChatId(update));
        msg.enableHtml(true);
        try {
            Thread.sleep(pauseInMilSecs);
            keyboardMessageID = bot.execute(msg).getMessageId();
            progressData.setKeyboardMessageID(keyboardMessageID);
            SaveLoadCmds.saveProgressFile(getChatId(update),progressData);
        } catch (Exception exception) {
            System.out.println("Something has gone wrong");
        }
    }

    public static void sendImage(Update update, InputFile inputFile, TelegramLongPollingBot bot){
        SendPhoto photo = new SendPhoto();
        photo.setPhoto(inputFile);
        photo.setChatId(getChatId(update));
        try{
            bot.execute(photo);
        } catch(TelegramApiException exception){
            System.out.println("An error occured while trying to send photo");
        }
    }


    public static void returnMenu(Update update, TelegramLongPollingBot bot,int messageID){
       EditMessageText editMessageText = editMessage(getChatId(update),messageID,"Выберите интересующий пункт ниже ⬇\uFE0F");
       EditMessageReplyMarkup et = editKeyboard(getChatId(update), messageID, setKeyboardMarkup(returnMenuKeyboard()));
       et.setReplyMarkup(setKeyboardMarkup(returnMenuKeyboard()));
        try{
            bot.execute(editMessageText);
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with editing message to menu");
        }
    }


    private static List<List<InlineKeyboardButton>> returnMenuKeyboard(){
        List<List<InlineKeyboardButton>> allTheRows = new ArrayList<>();
        allTheRows.add(addOneRowButton("Как получить подарок?", TextCommands.sendRules()));
        allTheRows.add(addOneRowButton("Наш канал в TG", TextCommands.sendTelegramChannelLink()));
        allTheRows.add(addOneRowButton("Купить наши товары",TextCommands.sendShopsLinks()));
        allTheRows.add(addOneRowButton("Наш сайт", TextCommands.sendWebpageLink()));
        allTheRows.add(addOneRowButton("Связь с нами", TextCommands.sendContacts()));
        return allTheRows;
    }



    /*public static void returnTime(Update update, TelegramLongPollingBot bot){
        Main.messageID = TelegramUtils.sendButtonsReturningMessageID(update, bot, Main.widgetNow.format(Main.dateTimeFormatter).toString(),
                addButtons(2, new String[][]
                        {new String[]{"<", "<"}, new String[]{">", ">"}}));
    }*/

    /*public static void returnCalendar(Update update, TelegramLongPollingBot bot){
        Main.messageID = TelegramUtils.sendKeyboardReturningMessageID(update, bot,
                "Выберите дату", CalendarKeyboard.generateKeyboard(Main.widgetToday));
    }*/

    /*public static void editTime(Update update, TelegramLongPollingBot bot, int messageID){
        EditMessageText et = editMessage(getChatId(update),messageID,
                Main.widgetNow.format(Main.dateTimeFormatter).toString());
        et.setReplyMarkup(setKeyboardMarkup(addButtons(2,
                new String[][]
                        {new String[]{"<", "<"}, new String[]{">", ">"}})));
        try{
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with time editing message");
        }
    }*/

    /*public static void editMonth(Update update, TelegramLongPollingBot bot, int messageID){
        EditMessageReplyMarkup et = editKeyboard(getChatId(update),messageID, setKeyboardMarkup(CalendarKeyboard.generateKeyboard(Main.widgetToday)));
        et.setReplyMarkup(setKeyboardMarkup(CalendarKeyboard.generateKeyboard(Main.widgetToday)));
        try{
            bot.execute(et);
        } catch (Exception ex){
            System.out.println("Something has gone wrong with month editing message");
        }
    }*/

    public static void saveCSV(){
        HashMap<Long, ProgressData> userMap = Progress.loadProgress(() -> new HashMap<>());
        AtomicReference<Long> key = new AtomicReference<>(0L);
        List<String> csvData = userMap.keySet().stream().map(hashKey -> {
            key.set(hashKey);
            return userMap.get(hashKey);
        }).map(progressData -> {
            String sumInfo = progressData.getSumInfoOnUsers();
            return String.join("\n", sumInfo);
        }).toList();
        String csvDataAsString = String.join("\n",csvData);
        if(csvDataAsString.contains(",")){
            csvDataAsString = "UserID,Имя,Фамилия,Ник,Рег.имя,Статус,Роль,Когда подписался впервые,Когда отписался,Когда подписался снова,Узнавал про ивент?,Время," +
                    "Запросил ссылку на канал?,Время,Запросил ссылку на сайт?,Время,Запросил контакты?,Время,Запросил ссылку на маркеты?,Время\n" + csvDataAsString;
            csvDataAsString.replace(", ", ",");
        }
        csvDataAsString = "sep=,\n" + csvDataAsString;
        Progress.saveProgressAsCSV(csvDataAsString, "data");
    }


    public static UserType checkUserType(Update update){
        if(update.getMessage() == null & !update.hasCallbackQuery()){
            return UserType.USER;
        } else if(update.hasCallbackQuery()){
            Long userID = getChatId(update);
            return Main.usersDataBase.get(userID).getType();
        } else {
            Long userID = getChatId(update);
            return Main.usersDataBase.get(userID).getType();
        }
    }

        public static void registerUser(Update update){
        Long userID;
        String lastName;
        String firstName;
        String nickname;
        if(update.getMessage() == null & !update.hasCallbackQuery()){
            User user;
            userID = update.getMyChatMember().getChat().getId();
            ProgressData progressData = SaveLoadCmds.getUserProgressData(userID);
            if (Main.usersDataBase.containsKey(userID)){
                user = Main.usersDataBase.get(userID);
            } else {
                lastName = update.getMyChatMember().getChat().getLastName();
                firstName = update.getMyChatMember().getChat().getFirstName();
                nickname = update.getMyChatMember().getChat().getUserName();
                user = new User(userID, firstName,lastName, nickname);
            } if(update.getMyChatMember().getNewChatMember().getStatus().equalsIgnoreCase("kicked")){
                user.leftBot();
                progressData.stoppedBot();
                LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
                user.setTimeLeft(now);
                System.out.println("User left the bot");
            } else if(update.getMyChatMember().getNewChatMember().getStatus().equalsIgnoreCase("member")) {
                LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
                user.setRejoinedTime(now);
                user.becameChatMember();
                System.out.println("User rejoined the bot");
            }
            Main.usersDataBase.put(userID, user);
            progressData.setUserData(user);
            SaveLoadCmds.saveProgressFile(userID,progressData);
        } else if(update.hasCallbackQuery() | update.getMessage() != null){
            User user;
            userID = TelegramUtils.getChatId(update);
            if (Main.usersDataBase.containsKey(userID)){
                user = Main.usersDataBase.get(userID);
            } else {
                lastName = update.getMessage().getFrom().getLastName();
                firstName = update.getMessage().getFrom().getFirstName();
                nickname = update.getMessage().getFrom().getUserName();
                user = new User(userID, firstName,lastName, nickname);
                Main.usersDataBase.put(userID, user);
                ProgressData progressData = SaveLoadCmds.getUserProgressData(userID);
                progressData.setUserData(user);
                SaveLoadCmds.saveProgressFile(userID,progressData);
            }
        } else {
            userID = getChatId(update);
            lastName = update.getMessage().getFrom().getLastName();
            firstName = update.getMessage().getFrom().getFirstName();
            nickname = update.getMessage().getFrom().getUserName();
            User user = new User(userID, firstName,lastName, nickname);
            Main.usersDataBase.put(userID, user);
            ProgressData progressData = SaveLoadCmds.getUserProgressData(userID);
            progressData.setUserData(user);
            SaveLoadCmds.saveProgressFile(userID,progressData);
        }
        SaveLoadCmds.saveUsersDB();
    }

    public static void roleImplementer(Update update, TelegramLongPollingBot bot){
        if(update.getMessage() == null & !update.hasCallbackQuery()){
            TelegramUtils.saveCSV();
        } else {
            UserType type = TelegramUtils.checkUserType(update);
            TelegramUtils.saveCSV();
            Receivable receiver = null;
            if(type == UserType.USER) {
                receiver = new org.example.Roles.User();
            } else if(type == UserType.ADMIN){
                receiver = new Admin();
            }
            assert receiver != null;
            receiver.onUpdateReceived(update, bot);
        }
    }

    public static void sendMessageToUser(Long userID, TelegramLongPollingBot bot, String... args) {
        SendMessage msg = new SendMessage();
        msg.setChatId(userID);
        if (args.length == 1) {
            String message = args[0];
            msg.setText(message);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i]);
            }
            String result = stringBuilder.toString();
            msg.setText(result);
        }
        try {
            bot.execute(msg);
        } catch (Exception exc) {
            System.out.println("Something has gone wrong");
        }
    }


    public static boolean lookForAndPrintErrors(Update update, String msg, TelegramLongPollingBot bot) {
        msg = update.hasCallbackQuery() ? (update.getCallbackQuery().getData()) : (update.getMessage().getText());
        if(!msg.isEmpty()) {
            if(msg.length() > 500) {
                printMessageOut(update, "Сообщение слишком длинное, пожалуйста, сократите сообщение до 300 символов", bot);
                System.out.println("Text sent is too long, exception thrown");
                return true;
            }
        } else {
            printMessageOut(update, "Сообщение не является текстом. Пожалуйста, введите только текст.", bot);
            System.out.println("Message sent is not a text, exception thrown. Message: " + update.getMessage().getText());
            return true;
        } return false;
    }
}

