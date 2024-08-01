package com.cellbeans.hspa.nclivisitquestionanswer;

import com.sun.org.apache.xpath.internal.operations.Bool;

public interface NcliVisitQuestionAnswerFilledDto {
    String getCreatedDate();
    String getFilledBy();
    String getCfName();
    Long getCfId();
    Long getVisitId();
    String getCfcName();
    Long getIsEmrFinal();
}
