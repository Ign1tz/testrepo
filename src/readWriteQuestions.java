import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class readWriteQuestions {
    private static readWriteQuestions newQuestion;
    public String QUESTION;
    public String A1;
    public String A2;
    public String A3;
    public String A4;
    public String DIFFICULTY;
    public String RIGHTAWNSER;

    public List<readWriteQuestions> questionList(String filePath) throws IOException {
        List<readWriteQuestions> temp = readQuestion(filePath);
        return temp;
    }

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public readWriteQuestions(String eQuestion, String eA1, String eA2, String eA3, String eA4, String eDifficulty, String eAnswer) throws IOException {
        this.QUESTION = eQuestion;
        this.A1 = eA1;
        this.A2 = eA2;
        this.A3 = eA3;
        this.A4 = eA4;
        this.DIFFICULTY = eDifficulty;
        this.RIGHTAWNSER = eAnswer;
    }
    public readWriteQuestions() throws IOException {

    }

    public void writeQuestions(String filePath) throws IOException {
        List<readWriteQuestions> tempList = new ArrayList<>();
        tempList = questionList(filePath);
        Writer writer = Files.newBufferedWriter(Paths.get(filePath));
        Scanner scan = new Scanner(System.in);
        int count = 1, difficulty = 0;
        boolean stop = false, isInside = false;
        String dificultyS = "";
        while (!stop) {
            String unnessary = scan.nextLine();
            System.out.print("Question: ");
            String question = scan.nextLine();
            System.out.print("A1: ");
            String a1 = scan.nextLine();
            System.out.print("A2: ");
            String a2 = scan.nextLine();
            System.out.print("A3: ");
            String a3 = scan.nextLine();
            System.out.print("A4: ");
            String a4 = scan.nextLine();
            System.out.print("Correct answer: ");
            String answer = scan.nextLine();
            while (!isInside) {
                System.out.print("Difficulty Level [1-3]: ");
                difficulty = scan.nextInt();
                if (difficulty < 4 && difficulty > 0) {
                    isInside = true;
                }
            }
            isInside = false;
            switch (difficulty) {
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
            byte[] eQuestion = Base64.getEncoder().encode(question.getBytes());
            question = new String(eQuestion);
            byte[] eA1 = Base64.getEncoder().encode(a1.getBytes());
            a1 = new String(eA1);
            byte[] eA2 = Base64.getEncoder().encode(a2.getBytes());
            a2 = new String(eA2);
            byte[] eA3 = Base64.getEncoder().encode(a3.getBytes());
            a3 = new String(eA3);
            byte[] eA4 = Base64.getEncoder().encode(a4.getBytes());
            a4 = new String(eA4);
            byte[] eDifficulty = Base64.getEncoder().encode(dificultyS.getBytes());
            dificultyS = new String(eDifficulty);
            byte[] eAnswer = Base64.getEncoder().encode(answer.getBytes());
            answer = new String(eAnswer);
            newQuestion = new readWriteQuestions(question, a1, a2, a3, a4, dificultyS, answer);
            tempList.add(newQuestion);
            System.out.print("Another Question? ");
            String choice = scan.next();
            count++;
            if(Objects.equals(choice, "n")){
                stop = true;
            }
        }
        gson.toJson(tempList, writer);
        writer.close();
    }

    public List<readWriteQuestions> readQuestion(String filePath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        List<readWriteQuestions> tempList = new Gson().fromJson(reader, new TypeToken<List<readWriteQuestions>>() {}.getType());
        reader.close();
        return tempList;
    }

    public static void main(String[] args) throws IOException {
        readWriteQuestions test = new readWriteQuestions();
        test.writeQuestions("test3.json");
        //test.readQuestion("test3.json");
        //System.out.println(test.questionList);
    }
}

