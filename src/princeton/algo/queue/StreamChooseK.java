package princeton.algo.queue;

import java.util.Random;

/**
 * A memory efficient way to choose k items from a stream of inputs, which
 * features constant operation time and constant memory usage proportional to k.
 * It provides a uniform choice function regardless of the input length.
 * Put input item in {@code void update()} to update the collection.
 *
 * @param <Item> item type parameter
 */
public class StreamChooseK<Item> {

    private final int k;
    private int count;
    private double p = 1.0;
    private final RandomizedQueue<Item> collection;
    private final Random random = new Random();

    public StreamChooseK(int k) {
        this.k = k;
        collection = new RandomizedQueue<>();
    }

    /**
     * After the first k items, update the new item after with a diminishing
     * probability. The k + 1 item is updated with probability k / (1+k). The k + n
     * item is updated with probability P(n), which is calculated by P(n) = k * P(n
     * - 1) / (k + P(n - 1))
     *
     * @param item the added item for updating the collection
     */
    public void update(Item item) {
        if (count < k) {
            collection.enqueue(item);
        } else {
            p = k * p / (p + k);
            if (random.nextDouble() < p) {
                collection.dequeue();
                collection.enqueue(item);
            }
        }
        count++;
    }

    public RandomizedQueue<Item> getCollection() {
        return collection;
    }
}
