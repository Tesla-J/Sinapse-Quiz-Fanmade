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

        setAnswerShownResult(false);
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
                setAnswerShownResult(true);
            }
        });
    }
}
