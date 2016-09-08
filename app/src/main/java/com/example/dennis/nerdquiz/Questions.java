package com.example.dennis.nerdquiz;

/**
 * Created by Dennis on 06.09.2016.
 */
public class Questions {

    private String Question,RA,FA1,FA2,FA3;

    public Questions(String Question,String RA, String FA1, String FA2, String FA3){
        this.setQuestion(Question);
        this.setRA(RA);
        this.setFA1(FA1);
        this.setFA2(FA2);
        this.setFA3(FA3);
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public String getFA1() {
        return FA1;
    }

    public void setFA1(String FA1) {
        this.FA1 = FA1;
    }

    public String getFA2() {
        return FA2;
    }

    public void setFA2(String FA2) {
        this.FA2 = FA2;
    }

    public String getFA3() {
        return FA3;
    }

    public void setFA3(String FA3) {
        this.FA3 = FA3;
    }
}
