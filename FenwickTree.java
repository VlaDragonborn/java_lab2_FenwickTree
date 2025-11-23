public class FenwickTree {
    private int[] tree;
    private int size;

    public FenwickTree(int size) {
        this.size = size;
        this.tree = new int[size + 1];
    }

    public FenwickTree(int[] arr) {
        build(arr);
    }

    public void build(int[] arr) {
        this.size = arr.length;
        this.tree = new int[this.size + 1];

        for(int i = 0; i < arr.length; i++) {
            update(i, arr[i]);
        }
    }

    public void update(int index, int delta) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        index++;
        while (index <= size) {
            tree[index] += delta;
            index += index & -index;
        }
    }

    public int prefixSum(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int sum = 0;
        index++;
        while (index > 0) {
            sum += tree[index];
            index -= index & -index;
        }
        return sum;
    }

    public int rangeSum(int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("left should be <= right");
        }

        if (left == 0) {
            return prefixSum(right);
        }
        return prefixSum(right) - prefixSum(left - 1);
    }

    public int getSize() {
        return size;
    }
}
