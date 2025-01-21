package shady.bco.jadval;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shady.bco.jadval.util.IabHelper;
import shady.bco.jadval.util.IabResult;
import shady.bco.jadval.util.Purchase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.logging.Level;

public class LevelActivity extends AppCompatActivity {
    //TabLayout tabLayout;
    ListView listView;

    SharedPreferences shPref;
    SharedPreferences.Editor sEditor;
    IabHelper iabHelper;
    ArrayList<LevelsItems> levelsItems=new ArrayList<>();
    LevelsAdaptor levelsAdaptor;

    TextView textViewPage;
    int Page=1;

    int DisplayWidth,DisplayHeight;
    int[] LevelSE={1,3};

    int actionbar=0;
    int RequestCode=1;

    boolean HasPurchaed=false;
    boolean BazarInstalation=false;
    SweetAlertDialog PurchaseWaitSAD;

    Typeface typefaceTitr;
    Typeface typefaceMitra;

    //Exit
    CountDownTimer ExitTime;
    boolean Exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //tabLayout=findViewById(R.id.TabLayoutLevels);
        listView=findViewById(R.id.ListViewLevels);

        shPref=getSharedPreferences("Data", Context.MODE_PRIVATE);
        sEditor=shPref.edit();
        levelsAdaptor=new LevelsAdaptor(this,levelsItems);
        levelsAdaptor.notifyDataSetChanged();
         iabHelper=new IabHelper(this,"MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCkpWIZhtnfsU4QhFZhcJIKzaHVF+jZmGFqunmGABNe6SXkFyIQG7fu9pN+fpbVXaZQeNntzjxwESYGV8DFaJNbelTtcsZZ6DSr/FqR4Tj6BbpiDR3FzpyMgBLsJUBZLKBXpEraHLF/XW5+Fx9TyyG1rpqBTUEMCgniHAdU3iGpQL1s9lt/qgQWF2tXjjDEK6eDCh4/wprP8Y/5BQNWNyqdlccqjlKTFO+lxkL1yYMCAwEAAQ==");
        Display DisplaySize=getWindowManager().getDefaultDisplay();
        final Point Size=new Point();
        DisplaySize.getSize(Size);
        DisplayWidth=Size.x;
        DisplayHeight=Size.y;
        textViewPage=findViewById(R.id.textViewPage);

        typefaceTitr = Typeface.createFromAsset(getAssets(), "fonts/IRTitr.ttf");
        typefaceMitra=Typeface.createFromAsset(getAssets(),"fonts/IRMitra.ttf");

        TypedValue typedValue=new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize,typedValue,true)){
            actionbar=TypedValue.complexToDimensionPixelSize(typedValue.data,getResources().getDisplayMetrics());
        }


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        View view=getSupportActionBar().getCustomView();
        view.findViewById(R.id.imageButton).setVisibility(View.GONE);


        /*final int[] Level={1,2,3,4,5};
        boolean[] Lock={true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
        int Which=-1;
        for (int i=LevelSE[0];i<=LevelSE[1];i++){
            for (int x=1;x<=5;x++){
                 Which=Which+1;
                //Level[x-1]=Which;
                if (Integer.valueOf(shPref.getString("Level",null))>=Which+1)Lock[Which]=true;
                else Lock[Which]=false;
            }
            levelsItems.add(new LevelsItems(Level,Lock,DisplayWidth,DisplayHeight,i-1,tabLayout.getLayoutParams().height,actionbar));
            Log.d("Shit",String.valueOf(Level[0]));
        }*/
        if (shPref.getString("Level",null)==null){
            sEditor.putString("Level","1");
            sEditor.apply();
        }
        if (shPref.getString("Purchase",null)==null){
            sEditor.putString("Purchase","false");
            sEditor.apply();
        }
        if (shPref.getString("Manual",null)==null){
            sEditor.putString("Manual","false");
            sEditor.apply();
        }

        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (result.isSuccess()) BazarInstalation=true;
                else  BazarInstalation=false;

            }
        });


        int[] Level={1,2,3,4,5};
        boolean[] Lock={true,false,false};
        int Which=0;
        for (int i=LevelSE[0];i<=LevelSE[1];i++){
            for (int x=1;x<=3;x++){
                Log.d("ShitFor",String.valueOf(i)+"-"+String.valueOf(x));
                Which=Which+1;
                //Level[x-1]=Which;
                /*if (Integer.valueOf(shPref.getString("Level",null))>=i)Lock[i]=true;
                else Lock[i]=false;*/

                if (Integer.valueOf(shPref.getString("Level",null))>=((i-1)*3)+x)Lock[x-1]=true;
                else Lock[x-1]=false;
            }
            levelsItems.add(new LevelsItems(Level,Lock,DisplayWidth,DisplayHeight,i-1,0,actionbar,textViewPage));
            Log.d("Shit",String.valueOf(Lock[0]));
        }
        levelsAdaptor.notifyDataSetChanged();


        listView.setAdapter(levelsAdaptor);

                   textViewPage.setTypeface(typefaceMitra);
        listView.setOnTouchListener(new SwipeTouchListener(){
            @Override
            public boolean onSwipeLeft() {
                levelsItems.clear();
                if (LevelSE[1]<=15) {
                    LevelSE[0] = LevelSE[0] + 3;
                    LevelSE[1] = LevelSE[1] + 3;
                    Page=Page+1;
                    textViewPage.setText("صفحه "+String.valueOf(Page)+" از 6 صفحه ");
                }else{
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.ConsLevelMain),"مراحل بعدی وجود نداره!",Snackbar.LENGTH_SHORT);
                    TextView textView=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTypeface(typefaceTitr);
                    snackbar.show();
                }
                //if (LevelSE[1]==18) LevelSE[1]=17;
                AddLevels();
                //Toast.makeText(LevelActivity.this,"SwipeLeft",Toast.LENGTH_SHORT).show();
                return super.onSwipeLeft();
            }

            @Override
            public boolean onSwipeRight() {
                levelsItems.clear();
                if (LevelSE[1]>3) {
                    LevelSE[0] = LevelSE[0] - 3;
                    LevelSE[1] = LevelSE[1] - 3;
                    Page=Page-1;
                    textViewPage.setText("صفحه "+String.valueOf(Page)+" از 6 صفحه");
                }else{
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.ConsLevelMain),"مراحل قبلی وجود نداره!",Snackbar.LENGTH_SHORT);
                    TextView textView=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTypeface(typefaceTitr);
                    snackbar.show();
                }
                //if (LevelSE[1]==18) LevelSE[1]=17;
                AddLevels();
                //Toast.makeText(LevelActivity.this,"SwipeRight",Toast.LENGTH_SHORT).show();
                return super.onSwipeRight();
            }
        });



    }

    public void onClickLevelOpen(View view){
        if (Integer.valueOf(shPref.getString("Level",null))>=Integer.valueOf(view.getTag().toString())){
        if (shPref.getString("Purchase",null).equals("false") && Integer.valueOf(view.getTag().toString()) >= 30  ){
            HasPurchaed=false;
            InAppPurchase();
            return;
        }
            Intent intent = new Intent(LevelActivity.this, MainActivity.class);
            intent.putExtra("Level", view.getTag().toString());
            startActivity(intent);
        }

    }

    public void AddLevels(){
        int[] Level={1,2,3,4,5};
        boolean[] Lock={true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
        int Which=0;
        for (int i=LevelSE[0];i<=LevelSE[1];i++){
            for (int x=1;x<=3;x++){
                Which=Which+1;
                //Level[x-1]=Which;
                /*if (Integer.valueOf(shPref.getString("Level",null))>=i)Lock[i]=true;
                else Lock[i]=false;*/

                if (Integer.valueOf(shPref.getString("Level",null))>=((i-1)*3)+x)Lock[x-1]=true;
                else Lock[x-1]=false;
            }
            levelsItems.add(new LevelsItems(Level,Lock,DisplayWidth,DisplayHeight,i-1,0,actionbar,textViewPage));
            Log.d("Shit",String.valueOf(Level[0]));
        }
        levelsAdaptor.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Screen","OnStart");
        try {
            Intent intent= getIntent();
            if (intent.getStringExtra("Manual").equals("true") && shPref.getString("Manual",null).equals("false")){
                Manual();
            }
        }catch (Exception f){

        }

        levelsItems.clear();
        int Level =Integer.valueOf(shPref.getString("Level",null));
        int Row = (((Level-1)-((Level-1)%3))/3)+1;
        int PageRow= (Row-1)%3;
        if (PageRow==0) {
            LevelSE[0]=Row;
            LevelSE[1]=Row+2;
        }
        if (PageRow==1) {
            LevelSE[0]=Row-1;
            LevelSE[1]=Row+1;
        }
        if (PageRow==2) {
            LevelSE[0]=Row-2;
            LevelSE[1]=Row;
        }

        Page=(Math.round((Level-1)/9))+1;
        textViewPage.setText("صفحه "+String.valueOf(Page)+" از 6 صفحه");
        //Toast.makeText(LevelActivity.this,String.valueOf(Row)+"-"+String.valueOf(PageRow),Toast.LENGTH_LONG).show();
       // LevelSE[0]=1;
        //LevelSE[1]=3;
        AddLevels();
    }
    void Manual(){
        BubbleShowCaseBuilder B1 =new BubbleShowCaseBuilder(this)
                .title("راهنما")
                .textColor(Color.BLACK)
                .description("تو منوی مراحل هم با کشیدن بسمت چپ مراحل بعدی رو میتونی ببینی و با کشیدن بسمت راست هم مراحل قبلی رو.")
                .backgroundColor(Color.parseColor("#146FF7"));

        BubbleShowCaseBuilder B2 =new BubbleShowCaseBuilder(this)
                .title("راهنما تموم شد!")
                .textColor(Color.BLACK)
                .description("موفق باشی.")
                .backgroundColor(Color.parseColor("#146FF7"))
                .listener(new BubbleShowCaseListener() {
                    @Override
                    public void onTargetClick(BubbleShowCase bubbleShowCase) {
                        sEditor.putString("Manual","true");
                        sEditor.apply();
                    }

                    @Override
                    public void onCloseActionImageClick(BubbleShowCase bubbleShowCase) {
                        sEditor.putString("Manual","true");
                        sEditor.apply();
                    }
                    @Override
                    public void onBackgroundDimClick(BubbleShowCase bubbleShowCase) {

                    }
                    @Override
                    public void onBubbleClick(BubbleShowCase bubbleShowCase) {

                    }
                });
        BubbleShowCaseSequence bubbleShowCaseSequence=new BubbleShowCaseSequence();
        bubbleShowCaseSequence.addShowCase(B1);
        bubbleShowCaseSequence.addShowCase(B2);
        bubbleShowCaseSequence.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        if (shPref.getString("Purchase",null) != null){
            if (shPref.getString("Purchase",null).equals("true"))
            menu.findItem(R.id.BuyItem).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BuyItem:
                InAppPurchase();
                break;
            case R.id.ContactUsItem:
                MenuItem menuItem=findViewById(R.id.ContactUsItem);
                final AlertDialog.Builder abuilder = new AlertDialog.Builder(new ContextThemeWrapper(LevelActivity.this,R.style.AlerDialog));
                LayoutInflater layoutinflater = getLayoutInflater();
                View Dview = layoutinflater.inflate(R.layout.contact_alert,null);
                abuilder.setCancelable(true);
                abuilder.setView(Dview);
                TextView Title = (TextView) Dview.findViewById(R.id.textViewTitle2_AD);
                TextView InstaText = (TextView) Dview.findViewById(R.id.textViewInstaText);
                TextView TeleText = (TextView) Dview.findViewById(R.id.textViewTeleText);
                TextView GmailText = (TextView) Dview.findViewById(R.id.textViewGmailText);
                final FloatingActionButton Insta = (FloatingActionButton) Dview.findViewById(R.id.floatingActionButtonInsta_AD);
                final FloatingActionButton Telegram = (FloatingActionButton) Dview.findViewById(R.id.floatingActionButtonTelegram_AD);
                FloatingActionButton Gmail = (FloatingActionButton) Dview.findViewById(R.id.floatingActionButtonGmail_AD);
                GmailText.getLayoutParams().width=Gmail.getWidth();
                InstaText.getLayoutParams().width=Insta.getWidth();
                Insta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.instagram.com/shadyentertainmentt"));
                        startActivity(intent);
                    }
                });
                Telegram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://t.me/ShadyBco"));
                        startActivity(intent);
                    }
                });
                Gmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"shadyentertainment77@gmail.com"));
                            intent.putExtra(Intent.EXTRA_SUBJECT,"موضوع شما");
                            intent.putExtra(Intent.EXTRA_TEXT,"مطلب شما");
                            intent.setPackage("com.google.android.gm");
                            //intent.setData(Uri.parse("http://www.Gmail.com"));
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(LevelActivity.this,"مشکلی در ارتباط با جیمیل به وجود آمد. شما میتوانید از طریق این ایمیل(shadyentertainment77@gmail.com) با ما در ارتباط باشید",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                abuilder.show();
                break;
            case R.id.OtherAppsItem:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://cafebazaar.ir/developer/923337503019"));
                startActivity(intent);
                break;
            case R.id.CheckUpdateItem:

                CheckUpdate("Manual");
                break;
        }
            return super.onOptionsItemSelected(item);
    }

    IabHelper.OnIabPurchaseFinishedListener  PurchaseListener= new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            PurchaseWaitSAD.dismiss();
            if (result.isSuccess()){
                Log.d("Purchase","TotalSuccess");
                HasPurchaed=true;
                sEditor.putString("Purchase","true");
                sEditor.apply();
                SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(LevelActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitle("ممنون از اعتمادتان");
                sweetAlertDialog.setContentText("خرید شما موفقیت آمیز بود.");
                sweetAlertDialog.setConfirmButton("باشه", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
            }else{
                PurchaseFailed();
            }
        }
    };
void InAppPurchase(){
    final SweetAlertDialog Pbuilder=new SweetAlertDialog(LevelActivity.this,SweetAlertDialog.NORMAL_TYPE);
    Pbuilder.setTitle("خریداری نسخه کامل");
    Pbuilder.setContentText("برای دسترسی به مراحل 30 به بعد نیاز است که آن را خریداری کنید");
    Pbuilder.setCancelable(false);
    Pbuilder.setConfirmButton("باشه", new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Pbuilder.dismiss();
            MainActivity mainActivity = new MainActivity();
            if (mainActivity.IsNetwork(LevelActivity.this)) {

                if (BazarInstalation) {
                    RequestCode = RequestCode + 1;
                    iabHelper.launchPurchaseFlow(LevelActivity.this, "Activation", RequestCode, PurchaseListener);
                    PurchaseWaitSAD=new SweetAlertDialog(LevelActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                    PurchaseWaitSAD.show();
                } else {
                    NoBazaar();
                }

            } else {
                NoInternet();
            }
        }
    });
    Pbuilder.setCancelButton("نمیخوام", new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            HasPurchaed=false;
            Pbuilder.dismiss();
        }
    });
    Pbuilder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            //Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iabHelper != null) iabHelper.dispose();
        iabHelper = null;
    }
    void PurchaseFailed(){
        Log.d("Purchase","ActivationFailed");

        HasPurchaed=false;
        Snackbar snackbar=Snackbar.make(findViewById(R.id.ConsLevelMain),"خرید شما ناموفق بود",Snackbar.LENGTH_LONG).setAction("تلاش دوباره", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestCode=RequestCode+1;
                iabHelper.launchPurchaseFlow(LevelActivity.this,"Activation",RequestCode,PurchaseListener,"Retry");
                PurchaseWaitSAD.show();
            }
        });
        TextView textView=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView2=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTypeface(typefaceMitra);
        textView2.setTypeface(typefaceTitr);
        snackbar.show();
    }
    void NoInternet(){
        Log.d("Purchase","ActivationFailed");
        HasPurchaed=false;
        Snackbar snackbar=Snackbar.make(findViewById(R.id.ConsLevelMain),"اتصالات اینترنت خود را بررسی کنید",Snackbar.LENGTH_LONG);
        Typeface typefaceTitr = Typeface.createFromAsset(getAssets(), "fonts/IRTitr.ttf");
        Typeface typefaceMitra=Typeface.createFromAsset(getAssets(),"fonts/IRMitra.ttf");
        TextView textView=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        TextView textView2=(TextView)snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTypeface(typefaceMitra);
        textView2.setTypeface(typefaceTitr);
        snackbar.show();
    }
    void NoBazaar(){
        Log.d("Purchase:", "SetupFailure");
        final SweetAlertDialog sweetAlertDialog2 = new SweetAlertDialog(LevelActivity.this, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog2.setTitle("نیاز به اپلیکیشن کافه بازار");
        sweetAlertDialog2.setCancelable(false);
        sweetAlertDialog2.setContentText("برای خرید درون برنامه ای نیاز به اپلیکیشن کافه بازار است" + "\n" + "لطفا به دانلود کافه بازار از طریق دکمه زیر اقدام کنید");
        sweetAlertDialog2.setConfirmButton("نصب", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.cafebazaar.ir/install"));
                startActivity(intent);
            }
        });
        sweetAlertDialog2.setCancelButton("بعدا", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog2.dismiss();
            }
        });
        sweetAlertDialog2.show();
    }





    public void CheckUpdate(final String Way) {
        MainActivity mainActivity=new MainActivity();
        if (mainActivity.IsNetwork(LevelActivity.this)) {
            ApiInterface apiInterface=API.getAPI().create(ApiInterface.class);
            Call<WebService> UpdateCall = apiInterface.loginCall("2", String.valueOf(BuildConfig.VERSION_CODE));
            UpdateCall.enqueue(new Callback<WebService>() {
                @Override
                public void onResponse(Call<WebService> call, final Response<WebService> response) {
                    if (response.body().getStatus().equals("Available")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LevelActivity.this);
                        final AlertDialog alertDialog = builder.create();
                        builder.setTitle("بروزرسانی جدیدی در دسترس است.");
                        builder.setMessage("آیا مایل هستید آن را دریافت کنید؟");
                        builder.setPositiveButton("آره", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(response.body().getLink()));
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("نه", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder.show();

                    } else {
                        if (Way.equals("Manual")) {
                            Snackbar.make(findViewById(R.id.ConsLevelMain), "نسخه شما بروز است", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WebService> call, Throwable t) {
                    //Toast.makeText(MainActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        } else if (Way.equals("Manual")) {
            Snackbar.make(findViewById(R.id.ConsLevelMain), "لطفا اتصال اینترنت خود را بررسی کنید", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        ExitTime=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                 Exit=false;
            }
        }.start();
        if (Exit) finishAffinity();
        else Exit=true;
    }
}
