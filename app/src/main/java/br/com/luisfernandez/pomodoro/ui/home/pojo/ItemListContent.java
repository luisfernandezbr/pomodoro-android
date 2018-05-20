package br.com.luisfernandez.pomodoro.ui.home.pojo;

public class ItemListContent extends ItemList {

    private String elapsedTime;
    private String status;
    private String finishedDate;

    public ItemListContent() {
        super(ItemList.TYPE_CONTENT);
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }
}
