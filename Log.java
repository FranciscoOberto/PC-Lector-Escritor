import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Log{
    private static ArrayList<String> message = new ArrayList<>();
    private FileWriter fileWriter;

    public Log(){
        try{
            fileWriter = new FileWriter(new File("./file.txt"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static synchronized void addMessage(String string){
        message.add(string);
    }

    public void write(){
        System.out.println(message.size());
        for (String string: message){
            try{
                fileWriter.write(string + "\n");
                fileWriter.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
