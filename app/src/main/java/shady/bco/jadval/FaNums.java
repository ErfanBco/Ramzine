package shady.bco.jadval;

public class FaNums {
    String[][] Nums={
            {"0", "۰"},
            {"1", "۱"},
            {"2", "۲"},
            {"3", "۳"},
            {"4", "۴"},
            {"5", "۵"},
            {"6", "۶"},
            {"7", "۷"},
            {"8", "۸"},
            {"9", "۹"}};


    public String ToPersian(String text){
        for (int i=1;i<=Nums.length;i++){
            text=text.replace(Nums[i-1][0],Nums[i-1][1]);
        }
        return text;
    }
}
