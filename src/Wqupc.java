// Weighted quick union with path compression
public class Wqupc {
    

    // Array containing the parents of all the n*n elements in a n by n grid
    private int[] parent;

    // Size array
    private int[] size;

    public Wqupc(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    }

    // Connects p and q elements of the array to the same root
    public void union(int p, int q) {
        if (size[find(p)] > size[find(q)]) {
            parent[find(q)] = find(p);
            size[find(p)] += size[find(q)];
        } else {
            parent[find(p)] = find(q);
            size[find(q)] += size[find(p)];
        }
    }

    public boolean connected(int p, int q) {
        return (find(p) == find(q));
    }

    // Returns the root
    public int find(int a) {
        while (a != parent[a]) {
            parent[a] = parent[parent[a]];
            a = parent[a];
        }
        return a;
    }
}
