package uz.codic.ahmadtea.errors.error_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uz.codic.ahmadtea.R;

public class ErrorActivity extends AppCompatActivity {

    TextView errortext;
    ImageView back;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errortext = findViewById(R.id.info_error);
        back = findViewById(R.id.back);
        toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("Ошибка");

        String id = getIntent().getStringExtra("id");
        String message = null;
        try {
            message = getIntent().getStringExtra("message");
        }catch (Exception e){
        }

        if (message != null){
            errortext.setText("Произошла ошибка!\n" +
                    "\n"+ message +"!\n" +
                    "\n"+
                    "№: " + id+ "\n" +
                    "\n"+
                    "просим синхронизировать приложение" +
                    "и отправить скриншот этой ошибки" +
                    "в техподдержку,\n" +
                    "\n"+
                    "телеграм: +998908228585");
        }else {
            errortext.setText("Произошла ошибка!\n" +"\n"+
                    "код: общая ошибка\n" +"\n"+
                    "№: " + id+ "\n" +"\n"+
                    "просим синхронизировать приложение" +
                    "и отправить скриншот этой ошибки" +
                    "в техподдержку,\n" +"\n"+
                    "телеграм: +998908228585");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorActivity.super.onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
