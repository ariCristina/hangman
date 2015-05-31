package donthang.hangman;


public class User {
    private String user_id;
    private String nickname;
    private String status;
    private String avatar;
    private String interests;

    public User(String user_id, String nickname, String status, String avatar, String interests) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.status = status;
        this.avatar = avatar;
        this.interests = interests;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getStatus() {
        return this.status;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getInterests() {
        return this.interests;
    }
}
