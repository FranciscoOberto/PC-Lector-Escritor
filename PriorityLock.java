import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PriorityLock extends ReentrantReadWriteLock {

    private final PriorityWriteLock priorityWriteLock = new PriorityWriteLock(this);
    private final PriorityReadLock priorityReadLock = new PriorityReadLock(this);
    private HashSet<Thread> writers = new HashSet<Thread>();
    private Object controlWriters = new Object();

    public synchronized PriorityLock.PriorityWriteLock getWriteLock(){
        return priorityWriteLock;
    }

    public synchronized PriorityLock.PriorityReadLock getReadLock(){
        return priorityReadLock;
    }

    public void addWriter(Thread thread){
        synchronized (controlWriters){
            this.writers.add(thread);
        }
    }

    public void removeWriter(Thread thread){
        synchronized (controlWriters){
            if (!this.writers.remove(thread)){
                throw new RuntimeException("No existe el hilo en la lista");
            }
        }
    }

    public Boolean isEmpty(){
        synchronized (controlWriters){
            return writers.isEmpty();
        }
    }

    public static class PriorityWriteLock extends WriteLock{
        private PriorityLock lock;

        public PriorityWriteLock(PriorityLock lock){
            super(lock);
            this.lock = lock;
        }

        public void lock(){
            lock.addWriter(Thread.currentThread());
            super.lock();
        }

        public void unlock(){
            super.unlock();
            lock.removeWriter(Thread.currentThread());
            /*if (lock.isEmpty()){
                notifyAll();
            }*/
        }

    }

    public static class PriorityReadLock extends ReadLock{
        private PriorityLock lock;

        public PriorityReadLock(PriorityLock lock){
            super(lock);
            this.lock = lock;
        }

        public void lock(){
            if (lock.isEmpty()){
                if (!super.tryLock()){
                   waitToLock();
                }
            }else{
                waitToLock();
            }
        }

        private void waitToLock(){
            try {
                //wait();
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                this.lock();
            }
        }

        public void unlock(){
            super.unlock();
        }

    }
}
