package uz.gita.newPuzzle.mobdev;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;

import uz.gita.newPuzzle.mobdev.utils.ResultShared;

public class MainActivity extends AppCompatActivity {

    MaterialCardView play;
    MaterialCardView about;
    MaterialCardView result;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        about = findViewById(R.id.about);
        result = findViewById(R.id.result);
        play.setOnClickListener(view -> {
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
            finishAffinity();
        });
        about.setOnClickListener(view -> {
            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.about);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            AppCompatButton home = dialog.findViewById(R.id.yes_about);

            home.setOnClickListener(view1 -> dialog.dismiss());
            dialog.show();
        });


        result.setOnClickListener(view -> {
            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.result);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            AppCompatButton home_result = dialog.findViewById(R.id.yes_result);
            AppCompatTextView record_1 = dialog.findViewById(R.id.result_1);
            AppCompatTextView record_2 = dialog.findViewById(R.id.result_2);
            AppCompatTextView record_3 = dialog.findViewById(R.id.result_3);
            ResultShared pref = ResultShared.getInstance(this);

            if (pref.getFirst() == Integer.MAX_VALUE) {
                record_1.setText(("Record  1:   "));
            } else {
                record_1.setText(("Record  1:   " + pref.getFirst()));
            }
            if (pref.getSecond() == Integer.MAX_VALUE) {
                record_2.setText("Record  2:   ");
            } else {
                record_2.setText(("Record  2:   " + pref.getSecond()));
            }
            if (pref.getThird() == Integer.MAX_VALUE) {
                record_3.setText("Record  3:   ");
            } else {
                record_3.setText(("Record  3:   " + pref.getThird()));
            }
            home_result.setOnClickListener(view1 -> dialog.dismiss());
            dialog.show();
        });
    }
}