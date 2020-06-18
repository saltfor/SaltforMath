package arslan.mahmut.saltformath;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ZorlukActivity extends AppCompatActivity {

    RadioButton r1,r2,r3,r4,r5;
    Button btnzorlukonayla;
    FileInputStream fileinput;
    int zorluk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zorluk);
        zorlukEkranYukle();
        try {
            fileinput=openFileInput("dosya");
            zorluk=fileinput.read();
            fileinput.close();
            if(zorluk==1)
                r1.setChecked(true);
            if(zorluk==2)
                r2.setChecked(true);
            if(zorluk==3)
                r3.setChecked(true);
            if(zorluk==4)
                r4.setChecked(true);
            if(zorluk==5)
                r5.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void zorlukEkranYukle(){
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);
        r4=findViewById(R.id.r4);
        r5=findViewById(R.id.r5);
        btnzorlukonayla=findViewById(R.id.btnzorlukonayla);
        btnzorlukonayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1.isChecked())
                    zorluk=1;
                if(r2.isChecked())
                    zorluk=2;
                if(r3.isChecked())
                    zorluk=3;
                if (r4.isChecked())
                    zorluk=4;
                if (r5.isChecked())
                    zorluk=5;
                try {
                    FileOutputStream os=openFileOutput("dosya", Context.MODE_PRIVATE);
                    os.write(zorluk);
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
