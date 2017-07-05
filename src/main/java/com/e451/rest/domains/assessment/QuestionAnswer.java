package com.e451.rest.domains.assessment;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by j747951 on 6/29/2017.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class QuestionAnswer {
    private String title;
    private String body;
    private String answer;
    private String questionResponseId;

    public QuestionAnswer() {
    }

    public QuestionAnswer(String title, String body, String answer, String questionResponseId) {
        this.title = title;
        this.body = body;
        this.answer = answer;
        this.questionResponseId = questionResponseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionResponseId() {
        return questionResponseId;
    }

    public void setQuestionResponseId(String questionResponseId) {
        this.questionResponseId = questionResponseId;
    }
}