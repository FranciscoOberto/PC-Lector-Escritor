public class Libro {
    private static Integer nBook = 0;
    private Boolean isFinalVersion;
    private Integer reviews;
    private Integer reads;
    private Integer id;

    public Libro(){
        this.isFinalVersion = false;
        this.reviews = 0;
        this.reads = 0;
        this.id = ++nBook;
    }

    public int getId(){
        return this.id;
    }

    public void addReviews() {
        this.reviews++;
    }

    public void addReads() {
        this.reviews++;
    }

}
