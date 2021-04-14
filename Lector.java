import java.util.HashSet;

public class Lector extends Persona implements Runnable{
    private static Integer nLectores = 0;
    private Integer id;

    public Lector(HashSet<Libro> book){
        super(book);
        this.id = ++nLectores;
    }

    @Override
    public void run() {
        while (!this.book.isEmpty()){
            Libro libro = getBook();
            //System.out.printf("El %s va a leer el %s\n",this.toString(),libro.toString());
            if (libro.read()){
                this.book.remove(libro);
            }
        }
    }

    public String toString(){
        return "Lector " + this.id;
    }
}
