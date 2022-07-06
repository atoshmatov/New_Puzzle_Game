package uz.gita.newPuzzle.mobdev;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;

import uz.gita.newPuzzle.mobdev.utils.ResultShared;

public class Game extends AppCompatActivity {

    private final TextView[][] cell = new TextView[4][4];
    private final ArrayList<Integer> list = new ArrayList<>();
    private int x = 3;
    private int y = 3;
    private int countSteps;
    AppCompatTextView count;
    Dialog dialog;
    private ResultShared pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        count = findViewById(R.id.counts);
        pref = ResultShared.getInstance(this);

        loadViews();// Replace by pressing elements
        loadData();//to use repetitive numbers
        loadDataToView();// to place numbers in cells

        count.setText(("Moves:\n" + countSteps));

        findViewById(R.id.refresh).setOnClickListener(view -> {
            animation();
            countSteps = 0;
            count.setText(("Moves:\n" + countSteps));
            loadDataToView();
            shuffle();
        });
    }

    /**
     * Replace by pressing elements
     */
    private void loadViews() {
        ConstraintLayout parent = findViewById(R.id.tableGame);
        for (int i = 0; i < parent.getChildCount(); i++) {
            cell[i / 4][i % 4] = (TextView) parent.getChildAt(i);
            cell[i / 4][i % 4].setTag(i);
            cell[i / 4][i % 4].setOnClickListener(view -> {
                int amount = (int) view.getTag();
                move(amount / 4, amount % 4);
            });
        }
    }

    /**
     * to use repetitive numbers
     */
    private void loadData() {
        for (int i = 0; i < 15; i++) {
            list.add(i + 1);
        }
        shuffle();
    }

    /**
     * To randomize numbers
     */
    private void shuffle() {
        Collections.shuffle(list);
    }

    /**
     * to place numbers in cells
     */
    private void loadDataToView() {
        while (!isSolved(list)) {
            shuffle();
        }
        cell[x][y].setVisibility(View.VISIBLE);
        x = 3;
        y = 3;
        cell[x][y].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 15; i++) {
            cell[i / 4][i % 4].setText(String.valueOf(list.get(i)));
        }
    }


    /**
     * When a cell is clicked, it turns on the cell in the same place as the main one, deletes the cell we clicked on, and changes the numbers.
     *
     * @param i int
     * @param j int
     */
    private void move(int i, int j) {
        if ((Math.abs(x - i) == 1 && Math.abs(y - j) == 0) ||
                (Math.abs(x - i) == 0 && Math.abs(y - j) == 1)) {
            YoYo.with(Techniques.BounceIn)
                    .duration(300)
                    .playOn(cell[x][y]);
            cell[x][y].setVisibility(View.VISIBLE);
            cell[x][y].setBackground(cell[i][j].getBackground());
            cell[i][j].setVisibility(View.INVISIBLE);
            cell[x][y].setText(cell[i][j].getText());
            x = i;
            y = j;
            countSteps++;
            count.setText(("Moves:\n" + countSteps));
            if (countWinner()) {
                dialog = new Dialog(Game.this);
                dialog.setContentView(R.layout.windialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                AppCompatButton home = dialog.findViewById(R.id.yes);
                AppCompatButton again = dialog.findViewById(R.id.no);
                AppCompatTextView textView = dialog.findViewById(R.id.win2);

                textView.setText(("You finished the game with " + countSteps + " steps"));
                home.setOnClickListener(view -> {
                    startActivity(new Intent(Game.this, MainActivity.class));
                    dialog.dismiss();
                    finish();
                });
                again.setOnClickListener(view -> {
                    countSteps = 0;
                    count.setText(("Moves:\n" + countSteps));
                    loadDataToView();
                    shuffle();
                    dialog.dismiss();
                });
                dialog.show();
//                dialogWin();
                ResultShared pref = ResultShared.getInstance(this);
                pref.saveResult(countSteps);
            }
        }
    }

    /**
     * winner game
     *
     * @return win
     */
    private boolean countWinner() {
        for (int i = 0; i < 15; i++) {
            if (!cell[i / 4][i % 4].getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    // solve
    private int getInvCount(ArrayList<Integer> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j))
                    count++;
            }
        }
        return count;
    }

    // animation shuffle
    private void animation() {
        ConstraintLayout layout = findViewById(R.id.tableGame);
        for (int i = 0; i < 15; i++) {
            YoYo.with(Techniques.BounceIn)
                    .duration(500)
                    .playOn(findViewById(layout.getChildAt(i).getId()));
        }
    }

    private boolean isSolved(ArrayList<Integer> list) {
        int getCount = getInvCount(list);
        return getCount % 2 == 0;
    }
    //solve


    @Override
    public void onBackPressed() {
        dialog = new Dialog(Game.this);
        dialog.setContentView(R.layout.exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        AppCompatButton yes = dialog.findViewById(R.id.yes_exit);
        AppCompatButton no = dialog.findViewById(R.id.no_exit);

        yes.setOnClickListener(view -> {
            startActivity(new Intent(Game.this, MainActivity.class));
            dialog.dismiss();
            finishAffinity();
        });
        no.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}