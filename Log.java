import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Log implements Runnable{
    private static ArrayList<String> message = new ArrayList<>();
    private FileWriter actividades;
    private FileWriter registro;
    private Integer totalRegistro = 0;

    public Log(){
        try{
            actividades = new FileWriter(new File("./actividades.txt"));
            registro = new FileWriter(new File("./registro.txt"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getTotalRegistros(){
        return totalRegistro;
    }

    public static synchronized void addMessage(String string){
        message.add(string);
    }

    public void write(){
        System.out.println("Total de actividades: " + message.size());
        for (String string: message){
            try{
                actividades.write(string + "\n");
                actividades.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void run(){
        System.out.println("Escribiendo.....");
        try{
            registro.write("\n-------------------------------------------------------------------------------------\n");
            registro.write(Biblioteca.getString());
            registro.write("-------------------------------------------------------------------------------------\n");
            registro.write(Biblioteca.getResultado());
            registro.write("\n-------------------------------------------------------------------------------------\n");
            registro.flush();
            System.out.println("Listo");
            totalRegistro++;
            Thread.sleep(2000);
            this.run();
        }catch (InterruptedException e){
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("El hilo de registros fue interrumpido");
            System.out.println("-------------------------------------------------------------------------------------");
        }catch (Exception e){
            e.printStackTrace();
            this.run();
        }
    }
}
