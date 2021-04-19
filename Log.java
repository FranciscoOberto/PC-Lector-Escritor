import java.io.FileWriter;
import java.util.HashSet;

public class Log{
    private static HashSet<String> message = new HashSet<String>();
    private FileWriter fileWriter;

    public Log(){
        try{
            fileWriter = new FileWriter("file.txt");
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
