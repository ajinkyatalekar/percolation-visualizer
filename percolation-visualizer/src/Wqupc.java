import edu.princeton.cs.algs4.StdDraw;

public class Wqupc {
    //Weighted quick union path compressed
    private int[] id;
    private int[] sz;

    public Wqupc(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            sz[i] = 1;
        }

    }

    public void union(int p, int q) {
        if (sz[root(p)] > sz[root(q)]) {
            id[root(q)] = root(p);
            sz[root(p)] += sz[root(q)];
        } else {
            id[root(p)] = root(q);
            sz[root(q)] += sz[root(p)];
        }
    }

    public boolean connected(int p, int q) {
        return (root(p) == root(q));
    }

    private int root(int a) {
        while (a != id[a]) {
            id[a] = id[id[a]];
            a = id[a];
        }
        return a;
    }

    public int[] getId() {
        return this.id;
    }

    public int find(int i) {
        int max = i;

        return max;
    }
}
