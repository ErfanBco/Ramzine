package shady.bco.jadval;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;

public class LevelsAdaptor extends BaseAdapter {
    private Context mContext;
    private ArrayList<LevelsItems> mItem;

    public LevelsAdaptor(Context mContext, ArrayList<LevelsItems> mItem) {
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


        Button[] button=new Button[5];
        TextView tabLayout;

        public ViewHolder(View mView) {
            button[0] = mView.findViewById(R.id.buttonLevel1);
            button[1] = mView.findViewById(R.id.buttonLevel2);
            button[2] = mView.findViewById(R.id.buttonLevel3);
            button[3] = mView.findViewById(R.id.buttonLevel4);
            button[4] = mView.findViewById(R.id.buttonLevel5);
            tabLayout = mView.findViewById(R.id.textViewPage);
        }


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LevelsAdaptor.ViewHolder vHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.levels_adaptor, viewGroup, false);
            vHolder = new LevelsAdaptor.ViewHolder(view);
            view.setTag(vHolder);
        } else {
            vHolder = (LevelsAdaptor.ViewHolder) view.getTag();
        }

        LevelsItems currentItem = (LevelsItems) getItem(i);
        int[] Level=currentItem.getLevel();
        boolean[] Lock=currentItem.getLock();

        int min;
        if ((currentItem.getDisplayWidth()/4)>(currentItem.getDisplayHeight()/4)) min=currentItem.getDisplayWidth()/4;
        else min=currentItem.getDisplayWidth()/4;

        int text=currentItem.getRow()*3;
//vHolder.button[0].setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,drawable);
        Drawable drawable=mContext.getResources().getDrawable(R.drawable.backarrow);
        //if (text/3==17) vHolder.button[2].setVisibility(View.GONE);
        SharedPreferences shPref=mContext.getSharedPreferences("Data",Context.MODE_PRIVATE);
        int LevelNum=Integer.valueOf(shPref.getString("Level",null));
        for (int n=1;n<=3;n++){
            vHolder.button[n-1].getLayoutParams().width=min;
            vHolder.button[n-1].getLayoutParams().height=Math.round(vHolder.button[n-1].getLayoutParams().width*4/3);
            ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(50,0/*(currentItem.getDisplayHeight()-(vHolder.button[n-1].getLayoutParams().height*3))/4*/,0,0);
           // vHolder.button[n-1].setLayoutParams(params);
            vHolder.button[n-1].getLayoutParams().width=min;
            vHolder.button[n-1].getLayoutParams().height=vHolder.button[n-1].getLayoutParams().width*(4/3);
            vHolder.button[n-1].setText("");
            Typeface typefaceTitr = Typeface.createFromAsset(mContext.getAssets(), "fonts/IRTitr.ttf");
            vHolder.button[n-1].setTypeface(typefaceTitr);
            vHolder.button[n-1].setTag(String.valueOf(text+n));
            Log.d("ShitAdaptor",String.valueOf(Lock[n-1]));
            vHolder.button[n-1].setClickable(true);
            /*if (!Lock[(n-1)]) vHolder.button[n-1].setBackgroundResource(R.drawable.lock);
            else vHolder.button[n-1].setBackgroundResource(R.drawable.unlock);*/
            if (LevelNum<text+n) vHolder.button[n-1].setBackgroundResource(R.drawable.lock);
            else{
                FaNums faNums = new FaNums();
                vHolder.button[n-1].setText(faNums.ToPersian(String.valueOf(text+n)));
                vHolder.button[n-1].setBackgroundResource(R.drawable.unlock);
            }
            vHolder.button[n-1].setTextSize(0,vHolder.button[n-1].getLayoutParams().height/2);
        }
        currentItem.getTextViewPage().setTextSize(vHolder.button[0].getLayoutParams().height/12);


        ConstraintLayout constraintLayout=view.findViewById(R.id.ConsLevelAdaptor);
        ConstraintSet constraintSet=new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.buttonLevel1,ConstraintSet.LEFT,R.id.ConsLevelAdaptor,ConstraintSet.LEFT,0);
      if (currentItem.getRow()==0 ||currentItem.getRow()==3 ||currentItem.getRow()==6 ||currentItem.getRow()==9 ||currentItem.getRow()==12 ||currentItem.getRow()==15) constraintSet.connect(R.id.buttonLevel1,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[0].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/8);
      else constraintSet.connect(R.id.buttonLevel1,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[0].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/4);
      constraintSet.connect(R.id.buttonLevel1,ConstraintSet.RIGHT,R.id.buttonLevel2,ConstraintSet.LEFT,0);

        constraintSet.connect(R.id.buttonLevel2,ConstraintSet.LEFT,R.id.buttonLevel1,ConstraintSet.RIGHT,0);
        constraintSet.connect(R.id.buttonLevel2,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[1].getLayoutParams().width*4)-currentItem.getTabLayoutHeight())/4);
        if (currentItem.getRow()==0 ||currentItem.getRow()==3 ||currentItem.getRow()==6 ||currentItem.getRow()==9 ||currentItem.getRow()==12 ||currentItem.getRow()==15) constraintSet.connect(R.id.buttonLevel2,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[1].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/8);
        else constraintSet.connect(R.id.buttonLevel2,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[1].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/4);

        constraintSet.connect(R.id.buttonLevel3,ConstraintSet.LEFT,R.id.buttonLevel2,ConstraintSet.RIGHT,0);
        if (currentItem.getRow()==0 ||currentItem.getRow()==3 ||currentItem.getRow()==6 ||currentItem.getRow()==9 ||currentItem.getRow()==12 ||currentItem.getRow()==15) constraintSet.connect(R.id.buttonLevel3,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[2].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/8);
        else constraintSet.connect(R.id.buttonLevel3,ConstraintSet.TOP,R.id.ConsLevelAdaptor,ConstraintSet.TOP,(currentItem.getDisplayHeight()-(vHolder.button[2].getLayoutParams().width*4)-currentItem.getActionBar()-currentItem.getTabLayoutHeight())/4);
        constraintSet.connect(R.id.buttonLevel3,ConstraintSet.RIGHT,R.id.buttonLevel4,ConstraintSet.LEFT,0);

        constraintSet.applyTo(constraintLayout);

        vHolder.button[0].getLayoutParams().height=Math.round(vHolder.button[0].getLayoutParams().width*4/3);
        vHolder.button[1].getLayoutParams().height=Math.round(vHolder.button[1].getLayoutParams().width*4/3);
        vHolder.button[2].getLayoutParams().height=Math.round(vHolder.button[2].getLayoutParams().width*4/3);
        Log.d("Size",String.valueOf(vHolder.button[1].getLayoutParams().width)+"*"+String.valueOf(vHolder.button[1].getLayoutParams().height));


        return view;

    }




}
