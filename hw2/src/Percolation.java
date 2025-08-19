import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    int N;
    int openSitesCount;
    boolean[][] gridstatus;
    int virtual_top;
    int virtual_bottom;
    WeightedQuickUnionUF set_withtop;
    WeightedQuickUnionUF set_withtop_bottom;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.N=N;
        openSitesCount=0;
        gridstatus=new boolean[N][N];
        virtual_top=N*N;
        virtual_bottom=N*N+1;
        set_withtop=new WeightedQuickUnionUF(N*N+1);
        set_withtop_bottom=new WeightedQuickUnionUF(N*N+2);
    }

    private int xyTo1D(int row,int col){
        return row*N+col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Row or col index out of bounds");
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int id=xyTo1D(row,col);
        gridstatus[row][col]=true;
        openSitesCount++;
        if(id<N){
            set_withtop.union(id,virtual_top);
            set_withtop_bottom.union(id,virtual_top);
        }
        if (id>=N*(N-1)) {
            set_withtop_bottom.union(id,virtual_bottom);
        }
        if(row>0 && isOpen(row-1,col)){
            set_withtop.union(id,xyTo1D(row-1,col));
            set_withtop_bottom.union(id,xyTo1D(row-1,col));
        }
        if(row<N-1 && isOpen(row+1,col)){
            set_withtop.union(id,xyTo1D(row+1,col));
            set_withtop_bottom.union(id,xyTo1D(row+1,col));
        }
        if(col>0 && isOpen(row,col-1)){
            set_withtop.union(id,xyTo1D(row,col-1));
            set_withtop_bottom.union(id,xyTo1D(row,col-1));
        }
        if(col<N-1 && isOpen(row,col+1)){
            set_withtop.union(id,xyTo1D(row,col+1));
            set_withtop_bottom.union(id,xyTo1D(row,col+1));
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        validate(row,col);
        return gridstatus[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        validate(row,col);
        return set_withtop.connected(virtual_top,xyTo1D(row,col));
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openSitesCount;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return set_withtop_bottom.connected(virtual_bottom,virtual_top);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
