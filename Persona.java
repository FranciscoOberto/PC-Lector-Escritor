import java.util.HashSet;

public abstract class Persona {
    private HashSet<Libro> book;

    public Persona(){
        this.book= new HashSet<Libro>();
    }

    public void setBook(HashSet<Libro> book){
       this.book = book;
    }
}
