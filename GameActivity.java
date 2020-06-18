package arslan.mahmut.saltformath;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.FileInputStream;

public class GameActivity extends AppCompatActivity {

    TextView sorutext,txtpuan,txtzaman;
    EditText txtcevap;
    Button btncevap,btnplay;
    int zorluk,cevap,puan,artim;
    FileInputStream fileinput;
    private AdView mAdView;
    MediaPlayer sesDogru;
    MediaPlayer sesYanlis;
    MediaPlayer sesZaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        try {
            fileinput=openFileInput("dosya");
            zorluk=fileinput.read();
            fileinput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sesDogru = MediaPlayer.create(GameActivity.this, R.raw.dogru);
        sesYanlis = MediaPlayer.create(GameActivity.this, R.raw.yanlis);
        sesZaman = MediaPlayer.create(GameActivity.this, R.raw.zaman);
        MobileAds.initialize(this, "ca-app-pub-9721666247779896~6084242294");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        oyunEkranYukle();
        newGame();
    }

    int min,max,kacislem;
    public void oyunEkranYukle(){
        sorutext=findViewById(R.id.anaText);
        txtcevap=findViewById(R.id.txtcevap);
        btncevap=findViewById(R.id.btncevap);
        btnplay=findViewById(R.id.btnplay);
        txtpuan=findViewById(R.id.txtpuan);
        txtzaman=findViewById(R.id.txtzaman);
        txtpuan.setText("");
        txtzaman.setText("");
        btncevap.setVisibility(View.INVISIBLE);
        txtcevap.setVisibility(View.INVISIBLE);
        txtcevap.setEnabled(false);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });
        btncevap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevapla();
            }
        });
    }
    public void newGame() {
        txtcevap.requestFocus();
        btnplay.setVisibility(View.INVISIBLE);
        txtcevap.setVisibility(View.VISIBLE);
        txtcevap.setEnabled(true);
        btncevap.setVisibility(View.VISIBLE);
        txtpuan.setText(getString(R.string.txtpuan));
        txtzaman.setText(getString(R.string.txtzaman));
        puan=0;
        if(zorluk==1){ //cokkolay - tek hane 2 islem
            sorutext.setTextSize(42);
            artim=5;
            min=0;
            max=10;
            kacislem=2;
        }
        else if (zorluk==2){ //kolay - tek hane 3 islem
            sorutext.setTextSize(42);
            artim=10;
            min=0;
            max=10;
            kacislem=3;
        }
        else if (zorluk==3){ // orta - 2 hane 2 islem
            sorutext.setTextSize(36);
            artim=15;
            min=10;
            max=90;
            kacislem=2;
        }
        else if (zorluk==4){ // zor - 2 hane 3 islem
            sorutext.setTextSize(36);
            artim=25;
            min=10;
            max=90;
            kacislem=3;
        }
        else if (zorluk==5){ // cok zor - 3 hane 3 islem
            sorutext.setTextSize(30);
            artim=40;
            min=100;
            max=900;
            kacislem=3;
        }
        cevap=soruUret();
        timer.start();
    }
    CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long sure) {
            txtzaman.setText(getString(R.string.zaman)+" "+sure/1000);
        }
        @Override
        public void onFinish() {
            gameOver();
            sesZaman.start();
        }
    };
    public void cevapla(){
        try {
            if (cevap == Integer.parseInt(txtcevap.getText().toString())) {
                puan = puan + artim;
                if (sesDogru.isPlaying()==false)
                    sesDogru.start();
            }
            else{
                puan=puan-artim;
                if (sesYanlis.isPlaying()==false)
                    sesYanlis.start();
            }
            txtcevap.setText("");
            txtpuan.setText(getString(R.string.puan)+" "+ puan);
            cevap = soruUret();
            txtcevap.requestFocus();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private int soruUret(){
        char islemisaret='+';
        int cevap=0;
        int sayi1=min+(int)(Math.random()*(double)max);
        int sayi2=min+(int)(Math.random()*(double)max);
        int islem=(int)(Math.random()*kacislem);
        if (islem==0){
            islemisaret='+';
            cevap=sayi1+sayi2;
        }
        else if(islem==1){
            islemisaret='-';
            if (sayi1<sayi2){
                int temp=sayi1;
                sayi1=sayi2;
                sayi2=temp;
            }
            cevap=sayi1-sayi2;
        }
        else if(islem==2){
            islemisaret='x';
            cevap=sayi1*sayi2;
        }
        sorutext.setText(sayi1+" "+islemisaret+" "+sayi2+" = ?");
        return cevap;
    }
    public void gameOver(){
        sorutext.setTextSize(25);
        sorutext.setText(getString(R.string.txtgameover)+" "+puan);
        txtzaman.setText("");
        txtpuan.setText("");
        btncevap.setVisibility(View.INVISIBLE);
        txtcevap.setVisibility(View.INVISIBLE);
        txtcevap.setEnabled(false);
        btnplay.setVisibility(View.VISIBLE);
        btnplay.requestFocus();
    }
    @Override
    protected void onStop() {
        timer.cancel();
        super.onStop();
    }
}
