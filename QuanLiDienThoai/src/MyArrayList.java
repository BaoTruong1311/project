

	public class MyArrayList<E> {
		public static final int CAPACITY = 100;
		private int size = 0;
		private E[] data;

		public MyArrayList() {
			this(CAPACITY);
		}

		@SuppressWarnings("unchecked")
		public MyArrayList(int capacity) {
			if (capacity < 0) {
				throw new IllegalArgumentException("capacity: " + capacity);

			}
			data = (E[]) new Object[capacity];
		}
		public int size() {
	        return size;
	    }
		public boolean isEmpty() {
			return size == 0;

		}

		public boolean isFull() {
			return size == data.length;
		}

		protected void checkIndex(int index, int n) {
			if (index < 0 || index >= n) {
				throw new IndexOutOfBoundsException("index: " + index);
			}
		}

		public E get(int index) {
			checkIndex(index, size + 1);
			return data[index];
		}

		public void set(int index, E e) {
			checkIndex(index, size);
			E temp = data[index];
			data[index] = e;

		}

		public void add(int i, E e) {
			checkIndex(i, size + 1);
			if (size == data.length) {
				throw new IllegalStateException("full");
			}
			for (int j = size - 1; j > i; j--) {
				data[j + 1] = data[j];
			}

			data[i] = e;
			size++;
		}

		public void add(E e) throws IndexOutOfBoundsException,
				IllegalStateException {
			add(size, e);
		}

		@Override
		public String toString() {
			String result = "[" + data[0];
			if (size == 0) {
				return "[]";
			}

			else {
				for (int i = 1; i < size; i++) {
					result += ", " + data[i];
				}

			}
			return result + "]";
		}

		public void removeAt(int index) {
			checkIndex(index, size + 1);
			for (int i = index; i < size - 1; i++) {
				data[i] = data[i + 1];
			}
			data[size - 1] = null;
			size--;
		}

		public void remove(E e) {
			for (int i = 0; i < size; i++) {
				if (data[i] == e) {
					removeAt(i);
					i--;

				}

			}
		}

		public E remove(int i) throws IndexOutOfBoundsException {
			checkIndex(i, size);
			E temp = data[i];
			for (int k = i; k < size - 1; k++)
				// shift elements to fill hole
				data[k] = data[k + 1];
			data[size - 1] = null; // help garbage collection
			size--;
			return temp;
		}

		public int search(E e) {
			int result = 0;
			for (int i = 0; i < size; i++) {
				if (data[i] == e) {
					result = i;
				}
			}
			return result;
		}
		
		
	}



