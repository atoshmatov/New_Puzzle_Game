package uz.gita.newPuzzle.mobdev.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import uz.gita.newPuzzle.mobdev.MainActivity;
import uz.gita.newPuzzle.mobdev.R;

public class WinDialog extends AlertDialog {
    private View view;

    public WinDialog(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.windialog, null);
        setView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view.findViewById(R.id.yes).setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
        });

        view.findViewById(R.id.no).setOnClickListener(view -> {

        });
    }
}
