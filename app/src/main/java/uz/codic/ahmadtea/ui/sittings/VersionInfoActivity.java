package uz.codic.ahmadtea.ui.sittings;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

import uz.codic.ahmadtea.BuildConfig;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.ui.base.BaseActivity;

public class VersionInfoActivity extends BaseActivity implements VersionInfoMvpView {

    VersionInfoMvpPresenter<VersionInfoMvpView> presenter;

    Toolbar toolbar;
    TextView info_text;
    Button check_but;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        info_text = findViewById(R.id.info_text);
        check_but = findViewById(R.id.bnt_check);
        progressBar = findViewById(R.id.progress_bar);
        presenter = new VersionInfoPresenter<>(this);
        presenter.onAttach(this);
        info_text.append("Version Name: " + BuildConfig.VERSION_NAME + "\n");
        info_text.append("Version Code: " + BuildConfig.VERSION_CODE + "\n");

        check_but.setOnClickListener(v -> {
            presenter.onRequestCheckVersion();
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void updateVersion(File apk) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);

        Uri uri;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            String author = this.getApplicationContext().getPackageName()+".fileprovider";
            uri = FileProvider.getUriForFile(this,author,apk);
        }else{
            uri = Uri.fromFile(apk);
        }
        i.setDataAndType(uri, "application/vnd.android.package-archive");
        Log.d("Lofting", "About to install new .apk");
        this.startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponseCurrentVersion(HashMap<String, HashMap<String, String>> map) {
        if (!map.get("payload").get("version_name").equals(BuildConfig.VERSION_NAME)) {
            String path = Environment.getExternalStorageDirectory() + File.separator + "antex.apk";
            new MyTask().execute(path, (Objects.requireNonNull(map.get("payload")).get("absolute_path_apk")));
        } else {
            info_text.append("\n\nNo Update required");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void closeProgresBar() {
        progressBar.setVisibility(View.GONE);
    }


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                FileOutputStream f = new FileOutputStream(strings[0]);
                URL u = new URL(strings[1]);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
//                    c.setDoOutput(true);
                c.connect();
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
                c.disconnect();
                Log.d("Checker", "Successfully saved!");
//                closeProgresBar();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Checker", "error" + e.getMessage());
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Test", "onPostExecute: " + result);
            closeProgresBar();
            updateVersion(new File(result));
        }
    }

}


