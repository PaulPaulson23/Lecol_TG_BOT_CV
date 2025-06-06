package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Receivable {

    void onUpdateReceived(Update update, TelegramLongPollingBot bot);

}
