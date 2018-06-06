package domain;

/**
 *
 * @author Marco y Maria
 */
public class SynchronizedBuffer implements Buffer {

    //atributos
    private int[][] buffers;
    private boolean occupied;

    //constructor
    public SynchronizedBuffer() {
        this.occupied = false;
        buffers = new int[15][17];
    }

    
    @Override
    public synchronized void set(int row, int column) {
        switch (Thread.currentThread().getName()) {
            case "MItem":
                
                if (getBuffers()[row][column] == 3) {
                    getBuffers()[row][column] = 0;
                } else if (getBuffers()[row][column] == 0) {
                    getBuffers()[row][column] = 3;
                }   break;
            case "Smart":
                
                if (getBuffers()[row][column] == 2) {
                    getBuffers()[row][column] = 0;
                } else if (getBuffers()[row][column] == 0) {
                    getBuffers()[row][column] = 2;
                }   break;
            default:
                if (getBuffers()[row][column] == 0) {
                    getBuffers()[row][column] = 1;
                } else if (getBuffers()[row][column] == 1) {
                    getBuffers()[row][column] = 0;
                }   break;
        }
    }

    public void setZero(int row, int column) {
        getBuffers()[row][column] = 0;
    }

    //salida
    public void setExit(int row, int colum) {
        if (this.buffers[row][colum] == 4) {
            this.buffers[row][colum] = 1;
        } else {
            this.buffers[row][colum] = 4;
        }
    }

    //metodos set y get
    
    @Override
    public int get(int row, int column) {
        this.occupied = true;
        this.occupied = false;
        return buffers[row][column];
    }

    public int[][] getBuffers() {
        return buffers;
    }

    public void setBuffers(int[][] buffers) {
        this.buffers = buffers;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
