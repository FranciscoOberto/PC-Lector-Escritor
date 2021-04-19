import java.util.ArrayList;
import java.util.HashSet;

public class Biblioteca {
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
            if(p instanceof Escritor){t.setPriority(t.MAX_PRIORITY);}
            else if(p instanceof Lector){t.setPriority(t.MIN_PRIORITY);}
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

    public static void iterar(){
        while (true){
            iniciar();

            System.out.println("-----------------------------------------------------------------------------------");
            for(Libro l:libro){
                System.out.println(l.toString());
                if (!l.isFinal()){
                    System.exit(13);
                }
                if (l.getReads()  != 20){
                    System.exit(14);
                }
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }

    public static void ejecutar(){
        iniciar();

        int t = 0;
        for(Libro l:libro){
            System.out.println(l.toString());
            t += l.getTotalReads();
        }
        System.out.println("Total de lecturas: " + t);
        new Log().write();
    }


}
