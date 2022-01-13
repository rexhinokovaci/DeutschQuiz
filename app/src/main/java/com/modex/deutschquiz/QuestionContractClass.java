package com.modex.deutschquiz;

import android.provider.BaseColumns;



public final class QuestionContractClass {



    private QuestionContractClass() {}

    public static class QuestionsTable implements BaseColumns {


        public static final String TABLE_NAME = "Quiz_Questions";
        public static final String COLUMN_QUESTIONS = "Question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answerNr";
        public static final String COLUMN_DIFFICULTY = "difficulty";
    }




}


