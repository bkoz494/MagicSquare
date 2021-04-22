package com.example.magicsquare;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Random rand;
    int[][] tab = new int[4][4];
    TextView[] sums = new TextView[6];
    EditText[][] answers = new EditText[3][3];
    List<Integer> lista = new LinkedList<Integer>();
    TextView messages;
    Button submit, resume, newGame, exitGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sums[0] = findViewById(R.id.sumX1);
        sums[1] = findViewById(R.id.sumX2);
        sums[2] = findViewById(R.id.sumX3);
        sums[3] = findViewById(R.id.sumY1);
        sums[4] = findViewById(R.id.sumY2);
        sums[5] = findViewById(R.id.sumY3);

        answers[0][0] = findViewById(R.id.ans11);
        answers[0][1] = findViewById(R.id.ans12);
        answers[0][2] = findViewById(R.id.ans13);

        answers[1][0] = findViewById(R.id.ans21);
        answers[1][1] = findViewById(R.id.ans22);
        answers[1][2] = findViewById(R.id.ans23);

        answers[2][0] = findViewById(R.id.ans31);
        answers[2][1] = findViewById(R.id.ans32);
        answers[2][2] = findViewById(R.id.ans33);

        messages = findViewById(R.id.tvMess);
        submit = findViewById(R.id.Submit);
        resume = findViewById(R.id.Continue);
        newGame = findViewById(R.id.New);
        exitGame = findViewById(R.id.Exit);

        rand = new Random(System.currentTimeMillis());
        init();
    }

    private void init(){
        for(int i = 1; i < 10; i++){
            lista.add(i);
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int tmp = rand.nextInt(lista.size());
                tab[i][j] = lista.get(tmp);
                lista.remove(tmp);
                System.out.print(String.valueOf(tab[i][j]) + " ");
            }
            System.out.println();
        }
        for(int i = 0; i < 3; i++){
            tab[i][3] = 0;
            tab[3][i] = 0;
            for(int j = 0; j < 3; j++) {
                tab[i][3] += tab[i][j];
                tab[3][i] += tab[j][i];
            }
        }

        sums[0].setText(String.valueOf(tab[0][3]));
        sums[1].setText(String.valueOf(tab[1][3]));
        sums[2].setText(String.valueOf(tab[2][3]));
        sums[3].setText(String.valueOf(tab[3][0]));
        sums[4].setText(String.valueOf(tab[3][1]));
        sums[5].setText(String.valueOf(tab[3][2]));

        newGame.setEnabled(false);
        resume.setEnabled(false);
        submit.setEnabled(true);
    }

    public void toSubmit(View view) {
        if(submit.isEnabled() == false){
            return;
        }
        submit.setEnabled(false);
        try {
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (tab[i][j] == Integer.parseInt(answers[i][j].getText().toString())){
                        continue;
                    }
                    else{
                        messages.setText("Wrong! Try again!");
                        newGame.setEnabled(true);
                        resume.setEnabled(true);
                        return;
                    }
                }
            }
            messages.setText("Good!");
            newGame.setEnabled(true);
        }
        catch (NumberFormatException nfe) {
            messages.setText("Use integers from 1 to 9. ");
            submit.setEnabled(true);
            return;
        }
    }

    public void toContinue(View view) {
        if(resume.isEnabled() == false){
            return;
        }
        messages.setText("");
        for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j ++){
                answers[i][j].setText("");
            }
        }
        submit.setEnabled(true);
        newGame.setEnabled(false);
        resume.setEnabled(false);
    }

    public void NewGame(View view) {
        if(newGame.isEnabled() == false){
            return;
        }
        toContinue(view);
        init();
    }

    public void ExitGame(View view) {
        onPause();
        onStop();
        onDestroy();
    }
}