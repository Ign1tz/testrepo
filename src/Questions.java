
import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Questions {
    public JSONObject questions = new JSONObject();
    public JSONObject[] List = new JSONObject[(int) Math.pow(2,10)];
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
            questions.put("Difficulty;", dificultyS);
            testOBJ.put(question, questions);
            questionList.put(count, question);
            List[count] = testOBJ;
            System.out.print("Another Question? ");
            String choice = scan.next();

            count++;
            if(Objects.equals(choice, "n")){
                stop = true;
            }
        }
        testOBJ.put("Question List:", questionList);
        List[0] = testOBJ;
        file.write(List[0].toJSONString());
        file.flush();
    }
    public void readQuestion() throws IOException, ParseException {
        int test;
        List<String> questionNames = new ArrayList<String>();
        String test2;
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("test2.json");
        Object obj = parser.parse(reader);
        inputQuestionsOBJ = (JSONObject) obj;
        System.out.println(inputQuestionsOBJ);
        test = inputQuestionsOBJ.size();
        System.out.println(test);
        System.out.println(inputQuestionsOBJ.get("test"));
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
    }
    public static void main( String[] args ) throws IOException, ParseException {
        Questions test = new Questions();
        //test.newQuestion();
        test.readQuestion();
    }
}