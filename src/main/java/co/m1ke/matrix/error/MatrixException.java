package co.m1ke.matrix.error;

public abstract class MatrixException extends RuntimeException {

    private String message;

    public MatrixException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
