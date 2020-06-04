package com.marcos.sinapsequiz;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;

import org.w3c.dom.Text;
//import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Button mBotaoMentira;
    private Button mBotaoVerdade;
    private Button mBotaoCheat;
    private ImageButton mBotaoNext;
    private ImageButton mBotaoPrevious;
    private TextView mTextViewPergunta;
    private TextView mApiVersionTextView;
    private boolean mIsCheater;

    private FalseTrue[] mListaDePerguntas = new FalseTrue[] {
            new FalseTrue(R.string.pergunta_astronomia_1, false),
            new FalseTrue(R.string.pergunta_astronomia_2, true),
            new FalseTrue(R.string.pergunta_astronomia_3, false),

            new FalseTrue(R.string.pergunta_biologia_1, true),
            new FalseTrue(R.string.pergunta_biologia_2, true),

            new FalseTrue(R.string.pergunta_religiao_1, true),
            new FalseTrue(R.string.pergunta_religiao_2, true),

            new FalseTrue(R.string.pergunta_geografia_1, false),
            new FalseTrue(R.string.pergunta_geografia_2, false),

            new FalseTrue(R.string.pergunta_tecnologia_1, false),
            new FalseTrue(R.string.pergunta_tecnologia_2, true),
            new FalseTrue(R.string.pergunta_tecnologia_3, false),

            new FalseTrue(R.string.pergunta_quimica_1, false),
            new FalseTrue(R.string.pergunta_quimica_2, false)
    };

    private int mCurrentIndex = 0;

    //private static final String TAG ="SinapseQuiz";
    private static final String KEY_INDEX = "indice"; //usado como id do Bundle
    private static final String KEY_IS_CHEATER = "User cheating";

    /*Método para ir a pegunta seguinte*/
    private void actualizarPergunta(){
        int pergunta = mListaDePerguntas[mCurrentIndex].getQuestao();
        mTextViewPergunta.setText(pergunta);
    }

    /*Método para retroceder para a pergunta anterior*/
    private void retrocederPergunta(){
        int fimLista = mListaDePerguntas.length -1;
        mCurrentIndex = mCurrentIndex == 0 ? fimLista : --mCurrentIndex;
        actualizarPergunta();
    }

    /*Método para verificar se a resposta está certa*/
    private void checarResposta(boolean respostaUsuario){
        boolean respostaCerta = mListaDePerguntas[mCurrentIndex].isQuestaoVerdadeira();
        int idMensagem;

        if(mListaDePerguntas[mCurrentIndex].isCheater()){
            idMensagem = R.string.toast_julgamento;
        }
        else{
            if(respostaUsuario == respostaCerta) {
                idMensagem = R.string.correct_answer;
            } else {
                idMensagem = R.string.incorrect_answer;
            }
        }

        Toast.makeText(MainActivity.this, idMensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "OnCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        /*
        * Código inútil, mas um dia pode ser necessário

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle("Seja bem-vindo a mais um Sinapse Quiz.");
            }
        }

         */

        mTextViewPergunta = (TextView) findViewById(R.id.pergunta);
        mTextViewPergunta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                actualizarPergunta();
            }
        });

        mBotaoMentira = (Button) findViewById(R.id.botao_mentira);
        mBotaoMentira.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checarResposta(false);
            }
        });

        mBotaoVerdade = (Button) findViewById(R.id.botao_verdade);
        mBotaoVerdade.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checarResposta(true);
            }
        });

        mBotaoNext = (ImageButton) findViewById(R.id.botao_next);
        mBotaoNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mListaDePerguntas.length;
                mIsCheater = false;
                actualizarPergunta();
            }
        });

        mBotaoPrevious = findViewById(R.id.botao_previous);
        mBotaoPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                retrocederPergunta();
            }
        });

        /*Verifica o estado salvo, e restaura os dados guardados no estado anterior*/
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
            mListaDePerguntas[mCurrentIndex].setIsCheater(mIsCheater);
        }

        mBotaoCheat = (Button) findViewById(R.id.botao_cheat);
        mBotaoCheat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, CheatActivity.class);
                boolean answerIsTrue = mListaDePerguntas[mCurrentIndex].isQuestaoVerdadeira();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        mApiVersionTextView = (TextView) findViewById(R.id.versao_api_textView);
        mApiVersionTextView.setText("API Level " + Build.VERSION.SDK_INT); //Eu sei que na horizontal não vai aparecer bem em alguns dispositivos

        actualizarPergunta();
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Log.d(TAG, "Instance state saved");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }

    @Override
    protected void onActivityResult(int resquestCode, int resultCode, Intent data) {
        super.onActivityResult(resquestCode, resultCode, data);
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

/*
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called.");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called.");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
    }
     */
}
