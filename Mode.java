import java.util.ArrayList;
import java.util.HashSet;

public class Mode {
    static ArrayList<Libro> libro;
    static HashSet<Persona> persona;
    static HashSet<Thread> thread;

    public static void iniciar(){
        libro = new ArrayList<Libro>();
        persona = new HashSet<Persona>();
        thread = new HashSet<Thread>();

        for (int i = 0 ; i < 24 ; i++){
            libro.add(new Libro());
        }

        for (int i = 0 ; i < 10 ; i++){
            persona.add( new Escritor( (ArrayList<Libro>)  libro.clone()) );
        }

        for (int i = 0 ; i < 20 ; i++){
            persona.add( new Lector((ArrayList<Libro>) libro.clone()) );
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
    }

    public static void iterarSinLock(){
        while (true){
            iniciar();

            for(Libro l:libro){
                System.out.println(l.toString());
                if (!l.isFinal()){
                    System.exit(13);
                }
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }

    public static void sinLock(){
        iniciar();

        for(Libro l:libro){
            System.out.println(l.toString());
        }
    }


}
