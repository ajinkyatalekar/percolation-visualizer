// Weighted quick union with path compression
public class Wqupc {
    

    // Array containing all the n*n elements in a n by n grid
    private int[] id;

    // Parent array
    private int[] parent;

    public Wqupc(int n) {
        id = new int[n];
        parent = new int[n];

        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    // Connects p and q elements of the array to the same root
    public void union(int p, int q) {
        if (parent[find(p)] > parent[find(q)]) {
            id[find(q)] = find(p);
            parent[find(p)] += parent[find(q)];
        } else {
            id[find(p)] = find(q);
            parent[find(q)] += parent[find(p)];
        }
    }

    public boolean connected(int p, int q) {
        return (find(p) == find(q));
    }

    // Returns the root
    public int find(int a) {
        while (a != id[a]) {
            id[a] = id[id[a]];
            a = id[a];
        }
        return a;
    }
}
