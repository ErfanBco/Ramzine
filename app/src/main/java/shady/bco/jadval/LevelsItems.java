package shady.bco.jadval;

import android.widget.TextView;

public class LevelsItems {
    private int[] Level;
    private boolean[] Lock;
    private int DisplayWidth;
    private int DisplayHeight;
    private int Row;
    private int TabLayoutHeight;
    private int ActionBar;

    private TextView textViewPage;

    public LevelsItems(int[] Level, boolean[] Lock, int DisplayWidth, int DisplayHeight, int Row, int TabLayoutHeight, int ActionBar, TextView textViewPage){
        this.Level=Level;
        this.Lock=Lock;
        this.DisplayWidth=DisplayWidth;
        this.DisplayHeight=DisplayHeight;
        this.Row=Row;
        this.TabLayoutHeight=TabLayoutHeight;
        this.ActionBar=ActionBar;
        this.textViewPage=textViewPage;
    }


    public void setLevel(int[] level) {
        Level = level;
    }

    public boolean[] getLock() {
        return Lock;
    }

    public void setLock(boolean[] lock) {
        Lock = lock;
    }


    public int[] getLevel() {
        return Level;
    }



    public int getDisplayWidth() {
        return DisplayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        DisplayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return DisplayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        DisplayHeight = displayHeight;
    }


    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public int getTabLayoutHeight() {
        return TabLayoutHeight;
    }


    public int getActionBar() {
        return ActionBar;
    }

    public void setActionBar(int actionBar) {
        ActionBar = actionBar;
    }

    public void setTabLayoutHeight(int tabLayoutHeight) {
        TabLayoutHeight = tabLayoutHeight;
    }


    public TextView getTextViewPage() {
        return textViewPage;
    }

    public void setTextViewPage(TextView textViewPage) {
        this.textViewPage = textViewPage;
    }

}
