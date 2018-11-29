package co.m1ke.matrix.util.container;

public class Triplet<K, V, H> {

    private K k;
    private V v;
    private H h;

    public Triplet(K k, V v, H h) {
        this.k = k;
        this.v = v;
        this.h = h;
    }

    public static <K, V, H> Triplet<K, V, H> construct(K k, V v, H h) {
        return new Triplet<>(k, v, h);
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public H getH() {
        return h;
    }

    public void setH(H h) {
        this.h = h;
    }
}
