
import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Questions {
    public JSONObject questions = new JSONObject();
    public JSONObject[] List = new JSONObject[(int) Math.pow(2,10)];
    public JSONObject inputQuestionsOBJ = new JSONObject();
    public JSONObject inputQuestionArray = new JSONObject();
    public JSONArray[] inputList = new JSONArray[(int) Math.pow(2,10)];

    public void newQuestion() throws IOException {
        Scanner scan = new Scanner(System.in);
        JSONObject testOBJ = new JSONObject();
        int count = 0, dificulty = 0;
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
            System.out.print("Question: ");
            String question = scan.nextLine();
            while(!isInside) {
                System.out.print("Dificulty Level [1-3]: ");
                dificulty = scan.nextInt();
                if (dificulty < 4 && dificulty > 0){
                    isInside = true;
                }
            }
            isInside = false;
            switch (dificulty){
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
            questions.put("A4:", a4);
            questions.put("A3:", a3);
            questions.put("A2:", a2);
            questions.put("A1:", a1);
            questions.put("Difficulty;", dificultyS);
            testOBJ.put(question, questions);
            List[count] = testOBJ;
            FileWriter file = new FileWriter("test2.json");

            file.write(List[count].toJSONString());
            file.flush();

            count++;
            System.out.print("Another Question? ");
            String choice = scan.next();
            if(Objects.equals(choice, "n")){
                stop = true;
            }
        }
    }
    public void readQuestion() throws IOException, ParseException {
        int test;
        String test2;
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("test2.json");
        Object obj = parser.parse(reader);
        inputQuestionsOBJ = (JSONObject) obj;
        System.out.println(inputQuestionsOBJ);
        test = inputQuestionsOBJ.size();
        System.out.println(test);
        System.out.println(inputQuestionsOBJ.get("Easy"));
        inputQuestionArray = (JSONObject) inputQuestionsOBJ.get("Easy");
        System.out.println(inputQuestionArray);
        test2 = (String) inputQuestionArray.get("A1:");
        System.out.println(test2);
        System.out.println(inputQuestionArray.size());

    }
    public static void main( String[] args ) throws IOException, ParseException {
        Questions test = new Questions();
        //test.newQuestion();
        test.readQuestion();
    }
}