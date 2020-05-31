package com.marcos.sinapsequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
//import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Button mBotaoMentira;
    private Button mBotaoVerdade;
    private Button mBotaoCheat;
    private ImageButton mBotaoNext;
    private ImageButton mBotaoPrevious;
    private TextView mTextViewPergunta;
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

    //metodos
    private void actualizarPergunta(){
        int pergunta = mListaDePerguntas[mCurrentIndex].getQuestao();
        mTextViewPergunta.setText(pergunta);
    }

    private void retrocederPergunta(){
        int fimLista = mListaDePerguntas.length -1;
        mCurrentIndex = mCurrentIndex == 0 ? fimLista : --mCurrentIndex;
        actualizarPergunta();
    }

    private void checarResposta(boolean respostaUsuario){
        boolean respostaCerta = mListaDePerguntas[mCurrentIndex].isQuestaoVerdadeira();
        int idMensagem;

        if(mIsCheater){
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

        mTextViewPergunta = (TextView) findViewById(R.id.pergunta);
        mTextViewPergunta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                actualizarPergunta();
            }
        });

        //eventos
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

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
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

        actualizarPergunta();
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Log.d(TAG, "Instance state saved");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        if(data == null){
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
