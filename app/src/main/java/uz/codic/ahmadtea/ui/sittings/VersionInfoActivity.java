package uz.codic.ahmadtea.ui.sittings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import uz.codic.ahmadtea.BuildConfig;
import uz.codic.ahmadtea.R;

public class VersionInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView info_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        info_text = findViewById(R.id.info_text);
        info_text.append("Version Name: " + BuildConfig.VERSION_NAME + "\n");
        info_text.append("Version Code: " + BuildConfig.VERSION_CODE + "\n");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
