package donthang.hangman;


public class Challenge {
    private String message;
    private String challenge_id;
    private String status;
    private String notification_id;


    public Challenge(String notification_id, String message, String challenge_id, String status) {
        this.message = message;
        this.challenge_id = challenge_id;
        this.status = status;
        this.notification_id = notification_id;
    }

    public void setNotificationId(String notification_id) { this.notification_id = notification_id; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChallengeId(String challenge_id) {
        this.challenge_id = challenge_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationId() { return this.notification_id; }

    public String getMessage() {
        return this.message;
    }

    public String getChallengeId() {
        return this.challenge_id;
    }

    public String getStatus() {
        return this.status;
    }
}
