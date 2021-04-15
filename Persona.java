import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class Persona implements Runnable {
    protected ArrayList<Libro> book;

    public Persona(ArrayList<Libro> book){
        setBook(book);
    }

    public void setBook(ArrayList<Libro> book){
        this.book = book;
    }

    public Libro getBook(){
        if (!book.isEmpty()){
            Libro libro = book.get(new Random().nextInt(book.size()));
            return libro;
        }

        throw new RuntimeException("No quedan libros pendientes para " + this.toString());

    }

    public abstract void run();
}
