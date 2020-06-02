package com.marcos.sinapsequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "com.marcos.sinapsequiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.marcos.sinapsequiz.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean isAnswerShown;
    private static final String KEY_IS_CHEATER = "Is_Cheating";

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.text_view_resposta);

        if(savedInstanceState != null) {
            isAnswerShown = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
        }

        setAnswerShownResult(isAnswerShown);
        mShowAnswerButton = (Button) findViewById(R.id.botao_mostrar_resposta);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.botao_verdade);
                }
                else{
                    mAnswerTextView.setText(R.string.botao_mentira);
                }
                isAnswerShown = true;
                setAnswerShownResult(isAnswerShown);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, isAnswerShown);
    }
}
