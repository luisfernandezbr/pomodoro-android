package br.com.luisfernandez.pomodoro.ui.home.pojo;

public class ItemListTitle extends ItemList {

    private String title;

    public ItemListTitle() {
        super(ItemList.TYPE_TITLE);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
