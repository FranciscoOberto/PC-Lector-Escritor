import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PriorityLock extends ReentrantReadWriteLock {

    private final PriorityWriteLock priorityWriteLock = new PriorityWriteLock(this);
    private final PriorityReadLock priorityReadLock = new PriorityReadLock(this);
    private HashSet<Thread> writers = new HashSet<Thread>();
    private HashSet<Thread> readers = new HashSet<Thread>();
    private final Object writerControl = new Object();
    private final Object readerControl = new Object();



    public synchronized PriorityLock.PriorityWriteLock getWriteLock(){
        return priorityWriteLock;
    }
    public synchronized PriorityLock.PriorityReadLock getReadLock(){
        return priorityReadLock;
    }

    public synchronized void addWriter(Thread writer){
        synchronized (this.writerControl){
            if (!this.writers.contains(writer)){
                this.writers.add(writer);
                System.out.printf("El hilo %s agrega writer a %s y ahora tiene %d \n",Thread.currentThread(), this.toString(),this.writers.size());
            }
        }
    }
    public synchronized void addReader(Thread reader){
        synchronized (this.readerControl){
            if (!this.readers.contains(reader)){
                this.readers.add(reader);
                System.out.printf("El hilo %s agrega reader a %s y ahora tiene %d \n",Thread.currentThread(), this.toString(),this.readers.size());
            }
        }
    }

    public synchronized boolean writerIsEmpty(){
        synchronized (writerControl){
            return writers.isEmpty();
        }
    }
    public synchronized boolean readerIsEmpty(){
        synchronized (readerControl){
            return readers.isEmpty();
        }
    }

    public synchronized Thread getWriter(){
        if (!writerIsEmpty()){
            synchronized (writerControl){
                Thread thread = writers.iterator().next();
                //writers.iterator().remove();
                return thread;
            }
        }
        throw new RuntimeException("No hay writers esperando");
    }

    public synchronized Thread getReader(){
        if (!readerIsEmpty()){
            synchronized (readerControl){
                Thread thread = readers.iterator().next();
                //readers.iterator().remove();
                return thread;
            }
        }
        throw new RuntimeException("No hay readers esperando");
    }

    public static class PriorityWriteLock extends WriteLock{
        private PriorityLock lock;

        public PriorityWriteLock(PriorityLock lock){
            super(lock);
            this.lock = lock;
        }

        public void lock(){
            if (!super.tryLock()){
                this.lock.addWriter(Thread.currentThread());
                try {
                    Thread.currentThread().wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    this.lock();
                }
            }
        }

        public void unlock(){
            try {
                this.lock.getWriter().notify();
            }catch (RuntimeException e){
                this.lock.getReadLock().unlock();
                //e.printStackTrace();
            }
        }

    }

    public static class PriorityReadLock extends ReadLock{
        private PriorityLock lock;

        public PriorityReadLock(PriorityLock lock){
            super(lock);
            this.lock = lock;
        }

        public void lock(){
            if (this.lock.writerIsEmpty()){
                if (!super.tryLock()){
                    this.lock.addReader(Thread.currentThread());
                    try {
                        Thread.currentThread().wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        this.lock();
                    }
                }
            }else {
                this.lock();
            }
        }

        public void unlock(){
            if(this.lock.writerIsEmpty()){
                try {
                    this.lock.getReader().notify();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                this.lock.getWriteLock().unlock();
            }
        }

    }
}
