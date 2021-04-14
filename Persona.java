import java.util.HashSet;
import java.util.Iterator;

public abstract class Persona implements Runnable {
    protected HashSet<Libro> book;

    public Persona(HashSet<Libro> book){
        setBook(book);
    }

    public void setBook(HashSet<Libro> book){
        this.book = book;
    }

    public Libro getBook(){
        Iterator<Libro> iterator = this.book.iterator();
        if (iterator.hasNext()){
            Libro libro = iterator.next();
            //iterator.remove();
            return libro;
        }else {
            return null;
        }

    }

    public abstract void run();
}
