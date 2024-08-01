package com.cellbeans.hspa.nclivisitquestionanswer;

import java.io.Serializable;
import java.util.List;

public class NcliVisitQuestionAnswerDto implements Serializable {
    public List<NcliVisitQuestionAnswer> listQuestionAnswer;

    public List<NcliVisitQuestionAnswer> getListQuestionAnswer() {
        return listQuestionAnswer;
    }

    public void setListQuestionAnswer(List<NcliVisitQuestionAnswer> listQuestionAnswer) {
        this.listQuestionAnswer = listQuestionAnswer;
    }
}
