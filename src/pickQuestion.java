import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class pickQuestion {
    private List<Integer> easy = new ArrayList<>();
    private List<Integer> medium = new ArrayList<>();
    private List<Integer> hard = new ArrayList<>();
    private readWriteQuestions test = new readWriteQuestions(0);
    private List<readWriteQuestions> questionList = test.questionList;

    public pickQuestion() throws IOException {
    }

    private void loadQuestionDifficulty(){
        readWriteQuestions question;
        String difficulty;
        for(int place = 0; place < questionList.size(); place++){
            question = questionList.get(place);
            difficulty = question.DIFFICULTY;
            switch (difficulty){
                case "Easy":
                    easy.add(place);
                    break;
                case "Medium":
                    medium.add(place);
                    break;
                case  "Hard":
                    hard.add(place);
                    break;
            }
        }
    }

    public readWriteQuestions activeQuestion(List<Integer> probList, int lastQuestion, int numberOfQuestions, int currentQuestion){
        List<Integer> probList2 = updateProb(probList, lastQuestion, numberOfQuestions,currentQuestion);
        int probEasy = probList2.get(0), probMedium = probList2.get(1), probHard = probList2.get(2);
        int questionIndex = randomNumber(probEasy+probMedium+probHard);
        int thisQuestion, questionNumber;
        if(currentQuestion == numberOfQuestions - 1){
            thisQuestion = randomNumber(hard.size());
            thisQuestion--;
            questionNumber = hard.get(thisQuestion);
            hard.remove(thisQuestion);
            return questionList.get(questionNumber);
        }
        if(questionIndex <= probEasy){
            if(easyQuestionLeft()){
                thisQuestion = randomNumber(easy.size());
                thisQuestion--;
                questionNumber = easy.get(thisQuestion);
                easy.remove(thisQuestion);
                return questionList.get(questionNumber);
            }
        }
        if (questionIndex <= probMedium + probEasy){
            if(mediumQuestionLeft()){
                thisQuestion = randomNumber(medium.size());
                thisQuestion--;
                questionNumber = medium.get(thisQuestion);
                medium.remove(thisQuestion);
                return questionList.get(questionNumber);
            }
        }
        if(questionIndex <= probEasy + probMedium + probHard){
            if(hardQuestionLeft()){
                thisQuestion = randomNumber(hard.size());
                thisQuestion--;
                questionNumber = hard.get(thisQuestion);
                hard.remove(thisQuestion);
                return questionList.get(questionNumber);
            }
        }
            if(easyQuestionLeft()){
                thisQuestion = randomNumber(easy.size());
                thisQuestion--;
                easy.remove(thisQuestion);
                return questionList.get(thisQuestion);
            }
        return null;
    }
    private boolean easyQuestionLeft(){
        boolean hasQuestionLeft = false;
            if(easy.size() > 0){
                hasQuestionLeft = true;
            }
        return hasQuestionLeft;
    }
    private boolean mediumQuestionLeft(){
        boolean hasQuestionLeft = false;
        if(medium.size() > 0){
            hasQuestionLeft = true;
        }
        return hasQuestionLeft;
    }
    private boolean hardQuestionLeft(){
        boolean hasQuestionLeft = false;
        if(hard.size() > 1){
            hasQuestionLeft = true;
        }
        return hasQuestionLeft;
    }
    private List<Integer> updateProb(List<Integer> probList, int lastQuestion, int numberOfQuestions, int currentQuestion){
        int probEasy = probList.get(0), probMedium = probList.get(1), probHard = probList.get(2);
        if (currentQuestion == Math.ceil(numberOfQuestions/2)){
            probEasy = 12;
            probMedium = 66;
            probHard = 22;
        }
        if(lastQuestion == 0){
            return probList;
        }
        probList.clear();
        if(currentQuestion < Math.ceil(numberOfQuestions/2)) {
            switch (lastQuestion) {
                case 1:
                    if (probEasy >= 6) {
                        probEasy = (probEasy - 5);
                        probMedium = (probMedium + 3);
                        probHard = (probHard + 2);
                    }
                    break;
                case 2:
                    if (probMedium >= 4) {
                        probEasy = (probEasy + 3);
                        probMedium = (probMedium - 3);
                    }
                    break;
                case 3:
                    if (probHard >= 4) {
                        probEasy = (probEasy + 3);
                        probHard = (probHard - 3);
                    }
                    break;
            }
            if (hard.size() == 1) {
                probMedium += probHard;
                probHard = 0;
            }
        }else{
            switch (lastQuestion){
                case 1:
                    if(probEasy >= 6){
                        probEasy = (probEasy - 5);
                        probMedium = (probMedium + 2);
                        probHard = (probHard + 3);
                    }
                    break;
                case 2:
                    if(probMedium >= 4) {
                        probHard = (probHard + 3);
                        probMedium = (probMedium - 3);
                    }
                    break;
                case 3:
                    if(probHard >= 4) {
                        probMedium = (probMedium + 3);
                        probHard = (probHard - 3);
                    }
                    break;
            }
            if (hard.size() == 1) {
                probMedium += probHard;
                probHard = 0;
            }
            if(currentQuestion == numberOfQuestions - 1){
                probHard = 100;
                probMedium = 0;
                probEasy = 0;
            }
        }


        probList.add(probEasy);
        probList.add(probMedium);
        probList.add(probHard);
        return probList;
    }

    private int randomNumber(int high){
        int low = 1, random;
        Random rand = new Random();
        random =  rand.nextInt(high) + low;
        return random;
    }
    public void test(){
        List<Integer> probList = new ArrayList<>();
        probList.add(66);
        probList.add(22);
        probList.add(12);
        for(int i = 0; i <10; i++) {
            probList = updateProb(probList, randomNumber(3), 9, i);
        }
    }
    public void printQuestion(){
        loadQuestionDifficulty();
        readWriteQuestions activeQuestion;
        List<Integer> probList = new ArrayList<>();
        probList.add(66);
        probList.add(22);
        probList.add(12);
        int difficultyOfLastQuestion = 0;
        String question, difficulty, rightAnswer, a1, a2, a3, a4;
        for(int i = 0; i < questionList.size(); i++){
            activeQuestion = activeQuestion(probList, difficultyOfLastQuestion, questionList.size(), i);
            question = activeQuestion.QUESTION;
            difficulty = activeQuestion.DIFFICULTY;
            rightAnswer = activeQuestion.RIGHTAWNSER;
            a1 = activeQuestion.A1;
            a2 = activeQuestion.A2;
            a3 = activeQuestion.A3;
            a4 = activeQuestion.A4;
            switch (difficulty){
                case "Easy":
                    difficultyOfLastQuestion = 1;
                    break;
                case "Medium":
                    difficultyOfLastQuestion = 2;
                    break;
                case "Hard":
                    difficultyOfLastQuestion = 3;
                    break;
            }
            System.out.println("q: " + question);
            System.out.println("d: " + difficulty);
            System.out.println("r: " + rightAnswer);
            System.out.println("a1: "+ a1);
            System.out.println("a2: " + a2);
            System.out.println("a3: "+ a3);
            System.out.println("a4: " + a4);
            System.out.println();
            System.out.println(i);
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        pickQuestion temp = new pickQuestion();
        temp.printQuestion();
    }
}
