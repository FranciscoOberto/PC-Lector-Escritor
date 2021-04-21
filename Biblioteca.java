import java.util.ArrayList;
import java.util.HashSet;

public class Biblioteca {
    private static ArrayList<Libro> libro;
    private static HashSet<Persona> persona;
    private static HashSet<Thread> thread;
    private static Log log;

    private static void iniciar(){
        libro = new ArrayList<Libro>();
        persona = new HashSet<Persona>();
        thread = new HashSet<Thread>();
        log = new Log();
        Thread logThread = new Thread(log,"logThread");

        for (int i = 0 ; i < 24 ; i++){
            libro.add(new Libro());
        }

        for (int i = 0 ; i < 10 ; i++){
            persona.add( new Escritor( (ArrayList<Libro>)  libro.clone()) );
        }

        for (int i = 0 ; i < 20 ; i++){
            persona.add( new Lector((ArrayList<Libro>) libro.clone()) );
        }

        int i = 0;
        for (Persona p:persona) {
            Thread t = new Thread(p,"Thread-" + ++i);
            if(p instanceof Escritor){t.setPriority(t.MAX_PRIORITY);}
            else if(p instanceof Lector){t.setPriority(t.MIN_PRIORITY);}
            thread.add(t);
            t.start();
        }

        logThread.start();

        for(Thread t:thread){
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        logThread.interrupt();

        try {
            logThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void iterar(){
        while (true){
            iniciar();

            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println(getString());
            verificar();
        }
    }

    public static void ejecutar(){
        iniciar();

        System.out.println(getString());

        verificar();
        log.write();
        System.out.println("Total de registros: " + log.getTotalRegistros());
    }

    private static void verificar(){
        Boolean exito = true;

        for (Libro libro:Biblioteca.libro){
            if ( !(libro.getReviews() == 10 && libro.getReads() == 20 && libro.isFinal()) ){
                exito = false;
                break;
            }
        }

        System.out.println("-----------------------------------------------------------------------------");
        if (exito){
            System.out.println("La ejecucion fue EXITOSA, cada libro fue revisado 10 veces y leido 20.\n" + getResultado());
        }else{
            System.out.println("La ejecucion fue FALLIDA.");
            System.exit(-1);
        }
        System.out.println("-----------------------------------------------------------------------------\n");
    }

    public static String getString(){
        String libros = "";
        for(Libro l:libro){
            libros += l.toString() + "\n";
        }
        return libros;
    }

    public static String getResultado(){
        Integer reads = 0;
        Integer reviews = 0;
        Integer totalReads = 0;

        for (Libro libro:Biblioteca.libro){
            reads += libro.getReads();
            reviews += libro.getReviews();
            totalReads += libro.getTotalReads();
        }

        Integer total = reviews+totalReads;

        return "RESULTADO: [ reviews( " + reviews + " ) ; reads( " + reads + " ) ; totalReads( " + totalReads + " ) ; total( " + total + " ) ]";
    }


}
