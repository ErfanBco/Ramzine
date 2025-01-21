package shady.bco.jadval;

public class WordsItems {
    private String Word;
    private int WordsCount;
    private int Level;
    private boolean Check;

    public WordsItems(String Word, boolean Check, int WordsCount, int Level){
        this.Word=Word;
        this.Level=Level;
        this.Check=Check;
        this.WordsCount=WordsCount;
    }



    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }


    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    public int getWordsCount() {
        return WordsCount;
    }

    public void setWordsCount(int wordsCount) {
        WordsCount = wordsCount;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
