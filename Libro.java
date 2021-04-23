import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Libro {
    private final Object controlReader = new Object();
    private final Object controlTotalReader = new Object();
    private final Object controlReviews = new Object();
    private final Object controlIsFinal = new Object();
    private PriorityLock lock;
    private static Integer nBook = 0;
    private Boolean isFinalVersion;
    private Integer reviews;
    private Integer reads;
    private Integer totalReads;
    private Integer id;
    private final Integer TIME = 200;

    public Libro(){
        this.lock = new PriorityLock();
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.totalReads = 0;
        this.id = ++nBook;
    }

    public String toString(){
        return "Libro ID(" + getId() + ")############( writes: " + getReviews() + " ; reads: " + getReads() + " ; totalReads: " + getTotalReads() +  " ; finalVersion: " + isFinal() + " )";
    }

    public void write() {
        lock.lockWriter();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(TIME));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            addReviews();
            if (getReviews() >= 10){ setIsFinal(); }
            lock.unlockWriter();
            Thread thread = Thread.currentThread();
            Log.addMessage("El " + thread.getName() + "(" + thread.getState() + ") esta ESCRIBIENDO");
        }
    }

    public Boolean read() {
        lock.lockReader();
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(TIME));
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            Boolean isFinalAux = isFinal();
            if(isFinalAux){addRead();}
            addTotalRead();
            lock.unlockReader();
            Thread thread = Thread.currentThread();
            Log.addMessage("El " + thread.getName() + "(" + thread.getState() + ") esta LEYENDO");
            return isFinalAux;
        }
    }

    public int getId(){
        return this.id;
    }

    public int getReviews(){
        synchronized (controlReviews){
            return reviews;
        }
    }

    public int getReads(){
        synchronized (controlReader){
            return reads;
        }
    }

    public int getTotalReads(){
        synchronized (controlTotalReader){
            return totalReads;
        }
    }

    public Boolean isFinal(){
        synchronized (controlIsFinal){
            return isFinalVersion;
        }
    }

    public void addRead(){
        synchronized (controlReader){
            reads++;
        }
    }

    public void addTotalRead(){
        synchronized (controlTotalReader){
            totalReads++;
        }
    }

    public void addReviews(){
        synchronized (controlReviews){
            reviews++;
        }
    }

    public void setIsFinal(){
        synchronized (controlIsFinal){
            isFinalVersion = true;
        }
    }

}



