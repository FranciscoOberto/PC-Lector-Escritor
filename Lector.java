import java.util.ArrayList;

public class Lector extends Persona implements Runnable{
    private static Integer nLectores = 0;
    private Integer id;

    public Lector(ArrayList<Libro> book){
        super(book);
        this.id = ++nLectores;
    }

    @Override
    public void run() {
        while (!this.book.isEmpty()){
            try{
                Libro libro = getBook();
                //System.out.printf("El %s va a leer el %s\n",this.toString(),libro.toString());
                if (libro.read()){
                    this.book.remove(libro);
                }
                //System.out.printf("El %s leyó el %s\n",this.toString(),libro.toString());
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String toString(){
        return "Lector " + this.id;
    }
}
