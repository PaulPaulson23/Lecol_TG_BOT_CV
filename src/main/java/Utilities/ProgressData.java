package Utilities;

import UserRelated.User;
import org.example.BotState;
import org.example.Main;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ProgressData implements Serializable {

    static final long serialVersionUID = 1L;
    BotState botState = BotState.DEFAULT;
    private boolean pressedLinkToTG = false;
    private boolean pressedAboutEvent = false;
    private boolean pressedLinkToWebpage = false;
    private boolean pressedSendMeShops = false;
    private boolean pressedSendMeContacts = false;
    private boolean startedBot = false;
    private LocalDateTime pressedTGLinkTime;
    private LocalDateTime pressedAboutEventTime;
    private LocalDateTime pressedWebpageLinkTime;
    private LocalDateTime pressedShopsLinksTime;
    private LocalDateTime pressedContactsTime;

    private String serviceMessage;
    private int messageID = 0;
    private int keyboardMessageID = 0;

    private User userData;

    private String returnTime(LocalDateTime localDateTime){
        if(localDateTime != null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy*HH:mm:ss");
            return localDateTime.format(dateTimeFormatter);
        }
        else return "не было";
    }

    public String whenPressedTGLink(){
        return returnTime(pressedTGLinkTime);
    }

    public void stoppedBot(){
        startedBot = false;
    }

    public String whenPressedAboutEvent(){
        return returnTime(pressedAboutEventTime);
    }

    public String whenPressedWebpage(){
        return returnTime(pressedWebpageLinkTime);
    }

    public String whenPressedShopsLinksTime(){
        return returnTime(pressedShopsLinksTime);
    }

    public String whenPressedContactsTime(){
        return returnTime(pressedContactsTime);
    }

    public String whenJoinedFirstTime(){
        return returnTime(userData.getFirstTimeJoined());
    }

    public String whenLeft(){
        return returnTime(userData.getTimeLeft());
    }

    public String whenRejoined(){
        return returnTime(userData.getRejoinedTime());
    }

    public String isPressedSendMeContacts(){
        return pressedSendMeContacts ? "Да" : "Нет";
    }

    public void setMessageID(int messageID){
        this.messageID = messageID;
    }

    public void setKeyboardMessageID(int keyboardMessageID){
        this.keyboardMessageID = keyboardMessageID;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getKeyboardMessageID(){
        return keyboardMessageID;
    }

    public void setServiceMessage(String text){
        this.serviceMessage = text;
    }

    public String getServiceMessage(){
        return serviceMessage;
    }

    public void setPressedSendMeContacts(){
        if(!pressedSendMeContacts){
            pressedSendMeContacts = true;
            pressedContactsTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
    }
    public String isPressedSendMeShops(){
        return pressedSendMeShops ? "Да" : "Нет";
    }

    public void setPressedSendMeShops(){
        if(!pressedSendMeShops){
            pressedSendMeShops = true;
            pressedShopsLinksTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
    }
    public void setUserData(User userData){
        this.userData = userData;
    }

    public User getUserData(){
        return userData;
    }

    public BotState getState() {
        return botState;
    }

    public void setState(BotState botState) {
        this.botState = botState;
    }

    public ProgressData() {
    }

    public void startedBot(){
        startedBot = true;
    }
    public boolean hasStartedBot(){
        return startedBot;
    }

    public void setPressedLinkToTG(){
        if(!pressedLinkToTG){
            pressedLinkToTG = true;
            pressedTGLinkTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
    }

    public void setPressedAboutEvent(){
       if(!pressedAboutEvent){
           pressedAboutEvent = true;
           pressedAboutEventTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
       }
    }

    public void setPressedLinkToWebpage(){
        if(!pressedLinkToWebpage){
            pressedLinkToWebpage = true;
            pressedWebpageLinkTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
    }

    public String isPressedLinkToTG() {
        return pressedLinkToTG ? "Да" : "Нет";
    }

    public String isPressedAboutEvent() {
        return pressedAboutEvent ? "Да" : "Нет";
    }

    public String isPressedLinkToWebpage() {
        return pressedLinkToWebpage ? "Да" : "Нет";
    }

    public String getSumInfoOnUsers(){
        String userID = getUserData().getStringUserID();
        String firstName = getUserData().getFirstName();
        String lastName = getUserData().getLastName();
        String nickname = getUserData().getTelegramNickname();
        String localName = getUserData().getLocalName();
        String isPressedAboutEvent = isPressedAboutEvent();
        String isPressedLinkToTG = isPressedLinkToTG();
        String isPressedLinkToWebpage = isPressedLinkToWebpage();
        String isPressedSendMeContacts = isPressedSendMeContacts();
        String isPressedSendMeShops = isPressedSendMeShops();
        String getType = getUserData().printRole();
        String memberStatus = getUserData().isChatMember();
        String whenFirstJoined = whenJoinedFirstTime();
        String whenLeft = whenLeft();
        String whenRejoined = whenRejoined();
        String whenPressedAboutEvent = whenPressedAboutEvent();
        String whenPressedLinkToTG = whenPressedTGLink();
        String whenPressedLinkToWP = whenPressedWebpage();
        String whenPressedContacts = whenPressedContactsTime();
        String whenPressedShops = whenPressedShopsLinksTime();
        return (userID + "," + firstName + "," + lastName + "," + nickname + "," + localName + "," + memberStatus
                + "," + getType + "," + whenFirstJoined + "," + whenLeft+ "," + whenRejoined + "," + isPressedAboutEvent + "," + whenPressedAboutEvent
                + "," + isPressedLinkToTG + "," + whenPressedLinkToTG + "," + isPressedLinkToWebpage + "," + whenPressedLinkToWP
                + "," + isPressedSendMeContacts + "," + whenPressedContacts + "," + isPressedSendMeShops + "," + whenPressedShops);
    }


}