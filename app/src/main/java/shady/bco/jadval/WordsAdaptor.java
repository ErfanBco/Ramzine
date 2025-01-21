package shady.bco.jadval;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdaptor extends BaseAdapter {
    private Context mContext;
    private ArrayList<WordsItems> mItem;

    public WordsAdaptor(Context mContext, ArrayList<WordsItems> mItem) {
        this.mContext = mContext;
        this.mItem = mItem;
    }
    @Override
    public int getCount() {
        return mItem.size();
    }


    @Override
    public Object getItem(int i) {
        return mItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {


        CheckBox checkBox;

        public ViewHolder(View mView) {
            checkBox = mView.findViewById(R.id.checkBox);
        }


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //if (view != null) return view;
        final WordsAdaptor.ViewHolder vHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.words_adaptor, viewGroup, false);
            vHolder = new WordsAdaptor.ViewHolder(view);
            view.setTag(vHolder);
        } else {
            vHolder = (WordsAdaptor.ViewHolder) view.getTag();
        }

        final WordsItems currentItem = (WordsItems) getItem(i);

        vHolder.checkBox.setText(currentItem.getWord());
        vHolder.checkBox.setChecked(currentItem.isCheck());

        SharedPreferences shPref=mContext.getSharedPreferences("Data",Context.MODE_PRIVATE);
        SharedPreferences.Editor sEditor=shPref.edit();
        Log.d("WordsAdaptor","Called");
        if (shPref.getString("Save:TempWords",null)!=null) {
                if (shPref.getString("Save:TempWords", null).length() == currentItem.getWordsCount()) {
                    char[] Data=shPref.getString("Save:TempWords",null).toCharArray();
                    /*for (int x = 1; x <= shPref.getString("Save:TempWords", null).length(); x++) {
                        boolean Current=false;
                        if ('1'==Data[x-1]) Current=true;
                        vHolder.checkBox.setTag(x-1);
                        vHolder.checkBox.setChecked(Current);

                    }*/
                    boolean Current=false;
                    if ('1'==Data[i]) Current=true;
                    vHolder.checkBox.setChecked(Current);
                }
            }

        //vHolder.checkBox.setTag(i);


      vHolder.checkBox.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SharedPreferences shPref=mContext.getSharedPreferences("Data",Context.MODE_PRIVATE);
              SharedPreferences.Editor sEditor=shPref.edit();
              if (shPref.getString("Save:TempWords",null)==null) {
                  String Data = "";
                  if (shPref.getString("Save:Level", null)!=null){
                      if (shPref.getString("Save:Level", null).equals(String.valueOf(currentItem.getLevel()))) {
                          Data = shPref.getString("Save:Words", null);
                      }else{
                          for (int x = 1; x <= currentItem.getWordsCount(); x++) {
                              Data = Data + "0";
                          }
                      }
                      } else {
                          for (int x = 1; x <= currentItem.getWordsCount(); x++) {
                              Data = Data + "0";
                          }
                      }

                      sEditor.putString("Save:TempWords", Data);
                      sEditor.apply();

              }else if (shPref.getString("Save:TempWords",null).length()!=currentItem.getWordsCount()){
                  String Data = "";
                  if (shPref.getString("Save:Level", null)!=null){
                      if (shPref.getString("Save:Level", null).equals(String.valueOf(currentItem.getLevel()))) {
                          Data = shPref.getString("Save:Words", null);
                      }else {
                          for (int x = 1; x <= currentItem.getWordsCount(); x++) {
                              Data = Data + "0";
                          }
                      }
                  } else {
                      for (int x = 1; x <= currentItem.getWordsCount(); x++) {
                          Data = Data + "0";
                      }
                  }

                  sEditor.putString("Save:TempWords", Data);
                  sEditor.apply();
              }
              char[] Data=shPref.getString("Save:TempWords",null).toCharArray();
              char Status;
              vHolder.checkBox.setTag(i);
              if (vHolder.checkBox.isChecked()) Status='1';
              else Status='0';
              Data[i]=Status;
              sEditor.putString("Save:TempWords",String.valueOf(Data));
              sEditor.apply();


          }
      });


        return view;

    }


}
