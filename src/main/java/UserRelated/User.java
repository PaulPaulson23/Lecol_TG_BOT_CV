package UserRelated;

import org.example.Main;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class User implements Serializable {

    UserType type = UserType.USER;

    private Long userId;
    private String firstName;
    private String lastName;
    private String localName;
    private String telegramNickname;
    private boolean isChatMember;
    private LocalDateTime firstTimeJoined;
    private LocalDateTime rejoinedTime;
    private LocalDateTime timeLeft;



    public User(Long userID, String firstName, String lastName, String nickname){
        this.userId = userID;
        setProperName(firstName,lastName);
        this.telegramNickname = nickname;
        if(firstTimeJoined == null){
            firstTimeJoined = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        }
        isChatMember = true;
    }

    public void setRejoinedTime(LocalDateTime rejoinedTime) {
        this.rejoinedTime = rejoinedTime;
    }

    public void setTimeLeft(LocalDateTime timeLeft) {
        this.timeLeft = timeLeft;
    }

    public LocalDateTime getTimeLeft() {
        return timeLeft;
    }

    public LocalDateTime getRejoinedTime(){
        return rejoinedTime;
    }

    public void becameChatMember(){
        isChatMember = true;
    }

    public void leftBot(){
        isChatMember = false;

    }

    public String isChatMember(){
        return isChatMember ? "Подписан" : "Отписался";
    }
    private void setProperName(String firstName, String lastName){
        if (firstName != null & lastName != null) {
            this.firstName = firstName;
            this.lastName = lastName;
        } else if(firstName == null){
            this.firstName = "Клиент";
            this.lastName = "";
        } else if(lastName == null){
            this.firstName = firstName;
            this.lastName = "";
        } this.localName = this.firstName + " " + this.lastName;
    }

    public String getLocalName() { return localName; }

    public void setProperName(String localName) {
        this.localName = localName;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String name){
        this.firstName = name;
    }


    public String getLastName(){
        return lastName;
    }
    public String getFirstName(){
        return firstName;
    }

    public LocalDateTime getFirstTimeJoined() {
        return firstTimeJoined;
    }

    public void setTelegramNickname(String nickname){
        this.telegramNickname = nickname;
    }

    public String toString(){
        return ((isLocalNameAssigned()) ? ("Пользователь: " + getLocalName()) : ("Пользователь: " + firstName))
                + "\n" + ((telegramNickname != null) ? "Ссылка: " + getTelegramNickname() : "")
                + "\n" + "ID: " + userId.toString() + "\n" + "Под ролью: " + printRole();
    }

    public boolean isLocalNameAssigned(){
        if(getLocalName() != null){
            return true;
        }
        else return false;
    }

    public String getStringUserID(){
        return userId.toString();
    }
    public UserType getType() {
        return type;
    }

    public void setRole(UserType role) {
        this.type = role;
    }

    //TODO: review required
    public String getTelegramNickname(){
        if(telegramNickname != null){
            return "@" + this.telegramNickname;
        } else {
            return null;
        }
    }

    public String printRole(){
        if(getType() == UserType.USER){
            return "Пользователь";
        } else if (getType() == UserType.ADMIN){
            return "Администратор";
        }
        return null;
    }
}

