package com.marcos.sinapsequiz;

/*
* Classe que armazena uma pergunta e sua resposta
*/
public class FalseTrue{
    private int mQuestao;

    private boolean mQuestaoVerdadeira;

    public FalseTrue(int questao, boolean questaoVerdadeira){
        mQuestao = questao;
        mQuestaoVerdadeira = questaoVerdadeira;
    }

    public int getQuestao(){
        return this.mQuestao;
    }
    public void setQuestao(int questao){
        this.mQuestao = questao;
    }

    public boolean isQuestaoVerdadeira(){
        return mQuestaoVerdadeira;
    }
    public void setQuestaoVerdadeira(boolean questaoVerdadeira){
        this.mQuestaoVerdadeira = questaoVerdadeira;
    }
}
