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
    private int QUESTIONCOUNT;

    public List<readWriteQuestions> questionList = readQuestion("test3.json");
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public readWriteQuestions(String QUESTION, String A1, String A2, String A3, String A4, String DIFFICULTY, String RIGHTAWNSER, int QUESTIONCOUNT) throws IOException {
        this.QUESTION = QUESTION;
        this.A1 = A1;
        this.A2 = A2;
        this.A3 = A3;
        this.A4 = A4;
        this.DIFFICULTY = DIFFICULTY;
        this.RIGHTAWNSER = RIGHTAWNSER;
        this.QUESTIONCOUNT = QUESTIONCOUNT;
    }
    public readWriteQuestions(int QUESTIONCOUNT) throws IOException {
        this.QUESTIONCOUNT = QUESTIONCOUNT;
    }

    public void writeQuestions(String filePath) throws IOException {
        List<readWriteQuestions> tempList = new ArrayList<>();
        Writer writer = Files.newBufferedWriter(Paths.get(filePath));
        Scanner scan = new Scanner(System.in);
        int count = 1, difficulty = 0;
        boolean stop = false, isInside = false;
        String dificultyS = "";
        while (!stop) {
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
            newQuestion = new readWriteQuestions(question, a1, a2, a3, a4, dificultyS, answer, count);
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
        readWriteQuestions question;
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        List<readWriteQuestions> tempList = new Gson().fromJson(reader, new TypeToken<List<readWriteQuestions>>() {}.getType());
        reader.close();
        question = tempList.get(1);
        return tempList;
    }

    public static void main(String[] args) throws IOException {
        readWriteQuestions test = new readWriteQuestions(0);
        //test.writeQuestions("test3.json");
        test.readQuestion("test3.json");
        System.out.println(test.questionList);
    }
}

