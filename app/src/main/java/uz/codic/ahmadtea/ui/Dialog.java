package uz.codic.ahmadtea.ui;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import uz.codic.ahmadtea.R;

public class Dialog {

    private AlertDialog loadingAlertDialog;
    private TextView label;
    private ProgressBar progressBar;
    private LinearLayout first, second;

    public void showProgress(AppCompatActivity activity) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        label = view.findViewById(R.id.label);
        first = view.findViewById(R.id.first_container);
        second = view.findViewById(R.id.second_container);
        progressBar = view.findViewById(R.id.progress_bar);
        dialogBuilder.setView(view);

        loadingAlertDialog = dialogBuilder.create();
        loadingAlertDialog.setCanceledOnTouchOutside(false);
        loadingAlertDialog.setCancelable(false);

        loadingAlertDialog.show();
    }

    public void changeStatus(String label, int progress_status) {

        if (first.getVisibility() == View.GONE) {
            this.label.setText(label);
            progressBar.setProgress(progress_status);
        } else {
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
        }
    }

    public void dismissProgress() {
        loadingAlertDialog.dismiss();
    }
}
