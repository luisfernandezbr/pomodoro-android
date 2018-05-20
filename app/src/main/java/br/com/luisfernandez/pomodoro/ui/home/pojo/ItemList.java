package br.com.luisfernandez.pomodoro.ui.home.pojo;

public class ItemList {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;

    private int contentType;

    public ItemList(int contentType) {
        this.contentType = contentType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
