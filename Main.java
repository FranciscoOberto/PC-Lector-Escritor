import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]){
        while (true){
            HashSet<Libro> libro = new HashSet<Libro>();
            HashSet<Persona> persona = new HashSet<Persona>();
            HashSet<Thread> thread = new HashSet<Thread>();

            for (int i = 0 ; i < 24 ; i++){
                libro.add(new Libro());
            }

            for (int i = 0 ; i < 10 ; i++){
                persona.add( new Escritor((HashSet<Libro>) libro.clone()) );
            }

            for (int i = 0 ; i < 20 ; i++){
                persona.add( new Lector((HashSet<Libro>) libro.clone()) );
            }

            for (Persona p:persona) {
                Thread t = new Thread(p);
                thread.add(t);
                t.start();
            }

            for(Thread t:thread){
                try {
                    t.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            for(Libro l:libro){
                System.out.println("El " + l.toString() + " tiene ( " + l.reviews + " ; " + l.reads +  " ; " + l.isFinalVersion + " )");
                if (l.reviews != 10 || l.isFinalVersion != true){
                    System.exit(1);
                }
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
