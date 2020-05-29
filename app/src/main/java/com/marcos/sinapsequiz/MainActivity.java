package com.marcos.sinapsequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mBotaoMentira;
    private Button mBotaoVerdade;
    private Button mBotaoNext;
    private TextView mTextViewPergunta;

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

    //metodos
    private void actualizarPergunta(){
        int pergunta = mListaDePerguntas[mCurrentIndex].getQuestao();
        mTextViewPergunta.setText(pergunta);
    }

    private void checarResposta(boolean respostaUsuario){
        boolean respostaCerta = mListaDePerguntas[mCurrentIndex].isQuestaoVerdadeira();
        int idMensagem;

        if(respostaUsuario == respostaCerta) {
            idMensagem = R.string.correct_answer;
        } else {
            idMensagem = R.string.incorrect_answer;
        }

        Toast.makeText(MainActivity.this, idMensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewPergunta = (TextView) findViewById(R.id.pergunta);

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

        mBotaoNext = (Button) findViewById(R.id.botao_next);
        mBotaoNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mListaDePerguntas.length;
                actualizarPergunta();
            }
        });

        actualizarPergunta();
    }
}
