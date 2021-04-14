import java.util.HashSet;

public class Escritor extends Persona implements Runnable {
    private static Integer nEscritores = 0;
    private Integer id;

    public  Escritor(HashSet<Libro> book){
        super(book);
        this.id = ++nEscritores;
    }

    public void run(){
        while (!this.book.isEmpty()){
            Libro libro = getBook();
            this.book.remove(libro);
            //System.out.printf("El %s va a escribir el %s\n",this.toString(),libro.toString());
            libro.write();
        }
    }

    public String toString(){
        return "Escritor " + this.id;
    }
}
