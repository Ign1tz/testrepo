
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

import com.sun.jdi.connect.spi.TransportService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Questions {
    private List<Integer> easyQuestion = new ArrayList<>();
    private List<Integer> mediumQuestion = new ArrayList<>();
    private List<Integer> hardQuestion = new ArrayList<>();
    public JSONObject questions = new JSONObject();
    public static JSONObject[] List = new JSONObject[(int) Math.pow(2,10)];
    public List<JSONObject> List2 = new ArrayList<>();
    public JSONObject questionList = new JSONObject();
    public JSONObject inputQuestionList = new JSONObject();
    public JSONObject inputQuestionsOBJ = new JSONObject();
    public JSONObject inputQuestionArray = new JSONObject();
    public JSONArray[] inputList = new JSONArray[(int) Math.pow(2,10)];

    public void newQuestion() throws IOException {
        FileWriter file = new FileWriter("test2.json");
        Scanner scan = new Scanner(System.in);
        JSONObject testOBJ = new JSONObject();
        int count = 1, difficulty = 0;
        boolean stop = false, isInside = false;
        String dificultyS = "";
        while(!stop){
            String unnessary = scan.nextLine();
            System.out.print("A4: ");
            String a1 = scan.nextLine();
            System.out.print("A3: ");
            String a2 = scan.nextLine();
            System.out.print("A2: ");
            String a3 = scan.nextLine();
            System.out.print("A1: ");
            String a4 = scan.nextLine();
            System.out.print("Correct answer: ");
            String answer = scan.nextLine();
            System.out.print("Question: ");
            String question = scan.nextLine();
            while(!isInside) {
                System.out.print("Difficulty Level [1-3]: ");
                difficulty = scan.nextInt();
                if (difficulty < 4 && difficulty > 0){
                    isInside = true;
                }
            }
            isInside = false;
            switch (difficulty){
                case 1:
                    dificultyS = "Easy";
                    break;
                case 2:
                    dificultyS = "Medium";
                    break;
                case 3:
                    dificultyS = "Hard";
                    break;
            }
            questions.put("A1:", a1);
            questions.put("A2:", a2);
            questions.put("A3:", a3);
            questions.put("A4:", a4);
            questions.put("Correct answer: ", answer);
            questions.put("Difficulty:", dificultyS);

            testOBJ.put(question, questions);
            questionList.put(count, question);
            List[count] = testOBJ;
            file.append(List[count].toJSONString());
            System.out.print("Another Question? ");
            String choice = scan.next();
            count++;
            testOBJ.clear();
            if(Objects.equals(choice, "n")){
                stop = true;
            }
        }
        testOBJ.put("Question List:", questionList);
        List[0] = testOBJ;
        List2.add(testOBJ);
        file.append(List[0].toJSONString());
        file.flush();
        /*for (int i = 0; i < count-1; i++) {
            file.write(List[i].toJSONString());
        }
        file.flush();*/
    }
    public void readQuestion() throws IOException, ParseException {
        JSONObject tempOBJ;
        String temp;
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("test2.json");
        Object obj = parser.parse(reader);
        inputQuestionsOBJ = (JSONObject) obj;
        inputQuestionList = (JSONObject) inputQuestionsOBJ.get("Question List:");

        for(int count = 1; count <= inputQuestionList.size(); count++){
            tempOBJ = (JSONObject) inputQuestionsOBJ.get(inputQuestionList.get(String.valueOf(count)));
            temp = (String) tempOBJ.get("Difficulty:");
            switch (temp){
                case "Easy":
                    easyQuestion.add(count);
                    break;
                case "Medium":
                    mediumQuestion.add(count);
                    break;
                case "Hard":
                    hardQuestion.add(count);
                    break;
            }
        }
    }
    public String pickQuestion(int currentQuestion, final int numberOfQuestions, int lastQuestionPicked, float probEasy, float probMedium, float probHard){
        int random, questionPicked;
        String temp;
        random = randomNumber(1, 100);
        if(currentQuestion <= numberOfQuestions / 2){
            switch (lastQuestionPicked){
                case 0:
                    break;
                case 1:
                    if(probEasy >= 6){
                    probEasy = (probEasy - 5);
                    probMedium = (probMedium + 3);
                    probHard = (probHard + 2);
                    }
                    break;
                case 2:
                    if(probMedium >= 4) {
                        probEasy = (probEasy + 3);
                        probMedium = (probMedium - 3);
                    }
                    break;
                case 3:
                    if(probHard >= 4) {
                        probEasy = (probEasy + 3);
                        probHard = (probHard - 3);
                    }
                    break;
            }
            if(random <= probEasy){
                return easyQuestionLeft();
            } else if (random <= probMedium + probEasy){
                return mediumQuestionLeft();
            }else {
                return hardQuestionLeft();
            }
        }else if (currentQuestion <= numberOfQuestions - (Math.ceil(numberOfQuestions / 5))){
            probEasy = 12;
            probMedium = 66;
            probHard = 22;
            switch (lastQuestionPicked){
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
            if(random <= probMedium){
                return mediumQuestionLeft();
            } else if (random <= probMedium + probHard){
                return hardQuestionLeft();
            }else {
                return easyQuestionLeft();
            }
        } else if (currentQuestion == numberOfQuestions) {
            questionPicked = randomNumber(0, hardQuestion.size());
            temp = (String) inputQuestionList.get(hardQuestion.get(questionPicked));
            hardQuestion.remove(questionPicked);
            return temp;
        }
        else{
            hardQuestionLeft();
        }
        return null;
    }
    public String easyQuestionLeft(){
        int questionPicked;
        int tempQ;
        String temp;
        if(easyQuestion.size() > 0){
            questionPicked = randomNumber(0, easyQuestion.size());
            temp = (String) inputQuestionList.get(String.valueOf(easyQuestion.get(questionPicked)));
            easyQuestion.remove(questionPicked);
            return temp;
        } else if (randomNumber(1,100) > 66) {
            if(mediumQuestion.size() > 0) {
                questionPicked = randomNumber(0, mediumQuestion.size());
                tempQ = mediumQuestion.get(questionPicked);
                temp = (String) inputQuestionList.get(mediumQuestion.get(questionPicked));
                mediumQuestion.remove(questionPicked);
                return temp;
            } else{
                questionPicked = randomNumber(0, hardQuestion.size());
                tempQ = hardQuestion.get(questionPicked);
                temp = (String) inputQuestionList.get(tempQ);
                hardQuestion.remove(hardQuestion.get(questionPicked));
                return temp;
            }
        }else{
            questionPicked = randomNumber(0, hardQuestion.size());
            tempQ = hardQuestion.get(questionPicked);
            temp = (String) inputQuestionList.get(tempQ);
            hardQuestion.remove(questionPicked);
            return temp;
        }
    }
    public String mediumQuestionLeft(){
        int questionPicked;
        String temp;
        if(mediumQuestion.size() > 0){
            questionPicked = randomNumber(0, mediumQuestion.size());
            temp = (String) inputQuestionList.get(mediumQuestion.get(questionPicked));
            mediumQuestion.remove(questionPicked);
            return temp;
        } else if (randomNumber(1,100) > 66) {
            if(easyQuestion.size() > 0) {
                questionPicked = randomNumber(0, easyQuestion.size());
                temp = (String) inputQuestionList.get(String.valueOf(easyQuestion.get(questionPicked)));
                easyQuestion.remove(questionPicked);
                return temp;
            } else{
                questionPicked = randomNumber(0, hardQuestion.size());
                temp = (String) inputQuestionList.get(hardQuestion.get(questionPicked));
                hardQuestion.remove(questionPicked);
                return temp;
            }
        }else{
            questionPicked = randomNumber(0, hardQuestion.size());
            temp = (String) inputQuestionList.get(hardQuestion.get(questionPicked));
            hardQuestion.remove(questionPicked);
            return temp;
        }
    }
    public String hardQuestionLeft(){
        int questionPicked;
        String temp;
        if(hardQuestion.size() > 1){
            questionPicked = randomNumber(0, hardQuestion.size());
            temp = (String) inputQuestionList.get(hardQuestion.get(questionPicked));
            hardQuestion.remove(questionPicked);
            return temp;
        } else if (mediumQuestion.size() > 0) {
            questionPicked = randomNumber(0, mediumQuestion.size());
            temp = (String) inputQuestionList.get(mediumQuestion.get(questionPicked));
            mediumQuestion.remove(questionPicked);
            return temp;
        }else{
            questionPicked = randomNumber(0, easyQuestion.size());
            temp = (String) inputQuestionList.get(String.valueOf(easyQuestion.get(questionPicked)));
            easyQuestion.remove(questionPicked);
            return temp;
        }
    }
    public void readQuestion2() throws IOException, ParseException {
        int test;
        JSONObject temp = new JSONObject();
        List<String> questionNames = new ArrayList<>();
        String test2;
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("test2.json");
        Object obj = parser.parse(reader);
        inputQuestionsOBJ = (JSONObject) obj;
        System.out.println(inputQuestionsOBJ);
        test = inputQuestionsOBJ.size();
        System.out.println(test);
        System.out.println(inputQuestionsOBJ.get("test"));
        temp = (JSONObject) inputQuestionsOBJ.get("test");
        System.out.println(temp.get("Difficulty;"));
        inputQuestionList = (JSONObject)  inputQuestionsOBJ.get("Question List:");
        test = inputQuestionList.size();
        System.out.println(test);
        for(int i = 0; i < inputQuestionList.size(); i++){
            questionNames.add((String) inputQuestionList.get(String.valueOf(i+1)));
        }
        inputQuestionArray = (JSONObject) inputQuestionsOBJ.get("test");
        System.out.println(inputQuestionArray);
        test2 = (String) inputQuestionArray.get("A1:");
        System.out.println(test2);
        System.out.println(inputQuestionArray.size());
        System.out.println(questionNames);
        test2 = questionNames.get(1);
        System.out.println(test2);
    }
    public static int randomNumber(int low, int high){
        int random;
        Random rand = new Random();
        random =  rand.nextInt(high) + low;
        return random;
    }
    public void testPlay() throws IOException, ParseException {
        readQuestion();
        Scanner scan = new Scanner(System.in);
        int numberOfQuestions = 13;
        int currentQuestion;
        int lastQuestionPicked = 0;
        float probEasy = 60;
        float probMedium = 25;
        float probHard  = 15;
        String questionName;
        String temp;
        JSONObject questionCurrent;
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("test2.json");
        Object obj = parser.parse(reader);
        inputQuestionsOBJ = (JSONObject) obj;
        for(currentQuestion = 1; currentQuestion <= numberOfQuestions; currentQuestion++) {
            temp = pickQuestion(currentQuestion, numberOfQuestions, lastQuestionPicked, probEasy, probMedium, probHard);
            questionName = (String) inputQuestionList.get(temp);
            questionCurrent = (JSONObject) inputQuestionsOBJ.get(temp);
            System.out.println(temp);
            System.out.println(questionCurrent.get("A1:"));
            System.out.println(questionCurrent.get("A2:"));
            System.out.println(questionCurrent.get("A3:"));
            System.out.println(questionCurrent.get("A4:"));
            System.out.println(questionCurrent.get("Difficulty;"));
            temp = scan.next();
        }
    }
    public static void main( String[] args ) throws IOException, ParseException {
        Questions test = new Questions();
        //test.testPlay();
        test.newQuestion();
        //test.readQuestion2();
        //System.out.println(randomNumber(0,1));

    }
}