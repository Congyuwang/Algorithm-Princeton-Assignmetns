package princeton.algo.queue;

import princeton.algo.sort.Shuffle;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * The RandomizedQueue class implements an Iterable data structure that provides
 * uniformly random access to items. {@code enqueue}, {@code dequeue}, {@code sample}
 * all takes amortized constant time. The {@code Iterator} takes linear time
 * to initialize, and each instantiation provides statistically independent
 * shuffle order.
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Queue<Item> {

    final Random random = new Random();

    @SuppressWarnings("unchecked")
    private Item[] s = (Item[]) new Object[1];
    private int size = 0;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (size == s.length) {
            grow(s.length << 1);
        }
        s[size++] = item;
    }

    /**
     * {@code dequeue()} returns a random item from the Queue and remove it.
     * After each removal, fill in the gap using the last item in the Queue.
     *
     * @return a random item from the Queue.
     */
    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        if (size == (s.length >>> 2)) {
            grow(s.length >>> 1);
        }
        int i = random.nextInt(size);
        Item item = s[i];
        s[i] = s[--size];
        s[size] = null;
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            return null;
        }
        return s[random.nextInt(size)];
    }

    /**
     * {@code sample()} returns a random item from the Queue without
     * removing the item.
     *
     * @return a random item from the Queue
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        return s[random.nextInt(size)];
    }

    private void grow(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) Array.newInstance(s.getClass().getComponentType(), capacity);
        System.arraycopy(s, 0, copy, 0, size);
        s = copy;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] shuffledItem = randomizeCondense();
        int i = shuffledItem.length;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledItem[--i];
        }
    }

    public void shuffle() {
        Shuffle.shuffle(s, 0, size);
    }

    private Item[] randomizeCondense() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) Array.newInstance(s.getClass().getComponentType(), size);
        System.arraycopy(s, 0, copy, 0, size);
        Shuffle.shuffle(copy);
        return copy;
    }

    /**
     * the {@code toArray()} method overrides the default method in Queue, using
     * System.arraycopy which is faster than the iterator.
     *
     * @return a shallow copy of the items
     */
    @Override
    public Item[] toArray() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) Array.newInstance(s.getClass().getComponentType(), size);
        System.arraycopy(s, 0, copy, 0, size);
        return copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("a");
        randomizedQueue.enqueue("b");
        randomizedQueue.enqueue("c");
        randomizedQueue.enqueue("d");
        randomizedQueue.enqueue("e");
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(randomizedQueue.sample() + " ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(randomizedQueue.dequeue() + " ");
        }
    }
}
