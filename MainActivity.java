package arslan.mahmut.saltformath;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Button btnnasiloynanir, btnnewgame, btnzorluksec;
    Intent oyunekran,howtoplayekran,zorlukekran;
    private InterstitialAd mInterstitialAd;
    MediaPlayer sesGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oyunekran=new Intent(this,GameActivity.class);
        howtoplayekran=new Intent(this,HowToPlayActivity.class);
        zorlukekran=new Intent(this,ZorlukActivity.class);
        btnnewgame = findViewById(R.id.btnnewgame);
        btnnewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(oyunekran);
            }
        });
        btnnasiloynanir = findViewById(R.id.btnnasiloynanir);
        btnnasiloynanir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(howtoplayekran);
            }
        });
        btnzorluksec = findViewById(R.id.btnzorluksec);
        btnzorluksec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(zorlukekran);
            }
        });
        MobileAds.initialize(this, "ca-app-pub-9721666247779896~6084242294");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9721666247779896/9876201545");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                finish();
            }});
        sesGame=MediaPlayer.create(MainActivity.this,R.raw.game);
        sesGame.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if((mInterstitialAd.isLoaded())){
                mInterstitialAd.show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
