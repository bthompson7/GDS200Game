package util;
import java.util.Random;

/**
 * The Bucket Data Structure
 * 
 * @param <E> the object type
 */
public class Bucket<E> {

	private E[] bucketContents;
	private int bucketSize = 0;
	private int numOfItems = 0;

	/**
	 * @param size the size of the bucket
	 * @throws IllegalArgumentException if you try to pass a negative integer as the
	 *                                  bucket size
	 */
	@SuppressWarnings("unchecked")
	public Bucket(int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("Size must be a positive integer");
		}

		bucketContents = (E[]) new Object[size];
		this.bucketSize = size;
	}

	/**
	 * Default constructor inits a bucket of size 10
	 */
	@SuppressWarnings("unchecked")
	public Bucket() {
		bucketContents = (E[]) new Object[10];
		this.bucketSize = 10;
	}

	/**
	 * @param item the item to add
	 * @return true if succeeded in adding the item otherwise false
	 */
	@SuppressWarnings("unchecked")
	public boolean add(E item) {
		int size = 0;
		while (size < bucketSize) {
			if (bucketContents[size] == null) {
				bucketContents[size] = item;
				numOfItems++;
				return true;
			}
			size++;
		}

		int newSize = bucketSize * 2;
		E[] newArraySize = (E[]) new Object[newSize];
		System.arraycopy(bucketContents, 0, newArraySize, 0, bucketContents.length);
		bucketContents = newArraySize;
		this.bucketSize = newSize;
		size = 0;
		while (size < bucketSize) {
			if (bucketContents[size] == null) {
				bucketContents[size] = item;
				numOfItems++;
				return true;
			}
			size++;
		}
		return false;
	}
	
	/**
	 * @param index the index where the item is located
	 * @return the item requested
	 */
	public E get(int index){
		if(index > numOfItems) {
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+ numOfItems);
		}
		return bucketContents[index];
	}

	/**
	 * @param index
	 * @return true if the operation succeeded otherwise false
	 */
	public boolean remove(int index) {
		int size = 0;
		int currIndex = 0;
		while (size < bucketSize) {
			if (size == index) {
				bucketContents[size] = null;
				numOfItems--;
				currIndex = size;
				int count = 0;
				while (count < numOfItems) {
					currIndex++;
					bucketContents[size] = bucketContents[currIndex];
					bucketContents[currIndex] = null;
					size++;
					count++;
				}
				return true;
			}
			size++;
		}
		return true;
	}
	
	/**
	 * @return the size of the bucket
	 */
	public int size() {
		return numOfItems;
	}

	/**
	 * deletes all items from the bucket
	 * 
	 */
	public void clear() {
		int size = 0;
		while (size < bucketSize) {
			if (bucketContents[size] != null) {
				bucketContents[size] = null;
			}
			size++;
		}
		this.numOfItems = 0;
	}

	/**
	 * Displays the contents the bucket in the console
	 */
	public void showContents() {
		for (int i = 0; i < bucketContents.length; i++) {
			System.out.println(bucketContents[i]);
		}

	}

	/**
	 * deletes an rng amount
	 * 
	 * @return true if operation was successful false if it wasn't
	 */
	public boolean spill() {
		Random rng = new Random();
		int sizeToDelete = rng.nextInt(bucketSize);
		int s = 0;
		while (s < sizeToDelete) {
			bucketContents[s] = null;
			s++;
		}
		return true;
	}
	
	/**
	 * @param item the item to search for
	 * @return true, if it contains item otherwise if it doesn't contain the item
	 * it returns false
	 */
	public boolean contains(E item) {
		int size = 0;
		while(size < this.bucketSize) {
			if(item == bucketContents[size]) {
				return true;
			}
			size++;
		}
		return false;
		
	}
	
}
