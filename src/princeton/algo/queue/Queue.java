package princeton.algo.queue;

import java.util.NoSuchElementException;

/**
 * The Queue interface is an iterable Generic class that implements the Queue
 * data structure, which generally follows the FIFO (first in first out) rule
 * (except for the {@code RandomizedQueue} class).
 * <p>
 * {@code Item dequeue()} retrieves one item from the queue and removes it.
 * {@code void enqueue()} pushes one item into the queue.
 * </p>
 *
 * @param <Item> The generic type parameter.
 */
public interface Queue<Item> extends Iterable<Item> {

    /**
     * returns an item from the Queue, and remove it.
     * @return an item in the Queue
     * @throws NoSuchElementException throws if the Queue is empty.
     */
    Item dequeue();

    /**
     * push an item into the Queue
     * @param item the item to be pushed
     * @throws IllegalArgumentException throws if the item is null.
     */
    void enqueue(Item item) throws IllegalArgumentException;

    boolean isEmpty();

    /**
     * shuffle the queue. For array implementation, the operation takes linear time
     * and requires no extra memory. For linked-list implementation, the operation
     * takes log(n) time, and requires no extra memory.
     * @throws UnsupportedOperationException if shuffling is not supported.
     */
    void shuffle();

    /**
     * peek the first item to dequeue, but do not remove.
     * @return {@code null} if empty
     */
    Item peek();

    int size();

    /**
     * the {@code toArray()} method is a default method that utilizes the iterator
     * of Queue to build a shallow array copy of the queue.
     *
     * @return an array containing a shallow copy of the elements of this Queue
     */
    default Object[] toArray() {
        Object[] copy = new Object[size()];
        int pos = 0;
        for (Item item : this) {
            copy[pos++] = item;
        }
        return copy;
    }

}
