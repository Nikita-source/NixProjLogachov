import java.util.Arrays;

public class MathSet<T extends Number & Comparable<? super T>> {
    private T[] elements;

    public MathSet() {
        elements = (T[]) new Number[10];
    }

    public MathSet(int capacity) {
        elements = (T[]) new Number[capacity];
    }

    public MathSet(T[] numbers) {
        elements = (T[]) new Number[numbers.length];
        add(numbers);
    }

    public MathSet(T[]... numbers) {
        int size = 0;
        for (int i = 0; i < numbers.length; i++) {
            size += numbers[i].length;
        }
        elements = (T[]) new Number[size];
        for (int i = 0; i < numbers.length; i++) {
            add(numbers[i]);
        }
    }

    public MathSet(MathSet numbers) {
        elements = (T[]) new Number[numbers.elements.length];
        join(numbers);
    }

    public MathSet(MathSet... numbers) {
        int size = 0;
        for (int i = 0; i < numbers.length; i++) {
            size += numbers[i].elements.length;
        }
        elements = (T[]) new Number[size];
        join(numbers);
    }

    public void add(T n) {
        if (contains(n)) {
            return;
        }
        if (emptyCount() > 0) {
            elements[elements.length - emptyCount()] = n;
        } else if (emptyCount() == 0) {
            T[] newArr = (T[]) new Number[elements.length + (elements.length / 2)];
            for (int i = 0; i < elements.length; i++) {
                newArr[i] = elements[i];
            }
            newArr[elements.length] = n;
            elements = newArr;
        }
    }

    public void add(T... n) {
        for (int i = 0; i < n.length; i++) {
            add(n[i]);
        }
    }

    public void join(MathSet ms) {
        for (int i = 0; i < ms.elements.length; i++) {
            add((T) ms.elements[i]);
        }
    }

    public void join(MathSet... ms) {
        for (int i = 0; i < ms.length; i++) {
            join(ms[i]);
        }
    }

    public void sortDesc() {
        sortDesc(1, elements.length - emptyCount());
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        T temp;
        int size = elements.length - emptyCount();
        if (firstIndex > 0 && lastIndex <= size && lastIndex >= firstIndex) {
            firstIndex--;
            for (int i = lastIndex - 1; i >= firstIndex + 1; i--) {
                for (int j = firstIndex; j < i; j++) {
                    if (elements[j].compareTo(elements[j + 1]) < 0) {
                        temp = elements[j];
                        elements[j] = elements[j + 1];
                        elements[j + 1] = temp;
                    }
                }
            }
        }
    }

    public void sortDesc(T value) {
        for (int i = 0; i < elements.length; i++) {
            if (value.equals(elements[i])) {
                sortDesc(1, i);
            }
        }
    }

    public void sortAsc() {
        sortAsc(1, elements.length - emptyCount());
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        T temp;
        int size = elements.length - emptyCount();
        if (firstIndex > 0 && lastIndex <= size && lastIndex >= firstIndex) {
            firstIndex--;
            for (int i = lastIndex - 1; i >= firstIndex + 1; i--) {
                for (int j = firstIndex; j < i; j++) {
                    if (elements[j].compareTo(elements[j + 1]) > 0) {
                        temp = elements[j];
                        elements[j] = elements[j + 1];
                        elements[j + 1] = temp;
                    }
                }
            }
        }
    }

    public void sortAsc(T value) {
        for (int i = 0; i < elements.length; i++) {
            if (value.equals(elements[i])) {
                sortAsc(1, i);
            }
        }
    }

    public T get(int index) {
        int size = elements.length - emptyCount();
        if (index > 0 && index <= size) {
            return elements[index - 1];
        }
        return null;
    }

    public T getMax() {
        T max = elements[0];
        int size = elements.length - emptyCount();
        for (int i = 0; i < size; i++) {
            if (max.compareTo(elements[i]) < 0) {
                max = elements[i];
            }
        }
        return max;
    }

    public T getMin() {
        T min = elements[0];
        int size = elements.length - emptyCount();
        for (int i = 0; i < size; i++) {
            if (min.compareTo(elements[i]) > 0) {
                min = elements[i];
            }
        }
        return min;
    }

    public Number getAverage() {
        int valueAverage = 0;
        if (elements == null || elements.length == 0) {
            return valueAverage;
        }
        for (T element : elements) {
            if (element != null) {
                valueAverage += (Integer) element;
            }
        }
        return (double) valueAverage / elements.length;
    }

    public Number getMedian() {
        if (elements == null || elements.length == 0 || elements.length == emptyCount()) {
            return null;
        }
        MathSet<T> copy = new MathSet<>(this);
        copy.sortAsc();
        int size = copy.elements.length - copy.emptyCount();

        if (size % 2 == 0) {
            return (((Double) copy.elements[size / 2] + (Double) copy.elements[size / 2 - 1]) / 2f);
        }

        return copy.elements[size / 2];
    }

    public T[] toArray() {
        return toArray(1, elements.length - emptyCount());
    }

    public T[] toArray(int firstIndex, int lastIndex) {
        int size = elements.length - emptyCount();
        if (firstIndex > 0 && lastIndex <= size && lastIndex >= firstIndex) {
            firstIndex--;
            T[] copy = (T[]) new Number[lastIndex - firstIndex];
            for (int i = 0; i < lastIndex - firstIndex; i++) {
                copy[i] = elements[firstIndex + i];
            }
            return copy;
        }
        return null;
    }

    public MathSet squash(int firstIndex, int lastIndex) {
        return new MathSet(toArray(firstIndex, lastIndex));
    }

    public void clear() {
        elements = (T[]) new Number[elements.length];
    }

    public void clear(T[] numbers) {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (elements[i] != null && elements[i].equals(numbers[j])) {
                    elements[i] = null;
                }
            }
        }
    }

    public boolean contains(T n) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == n) {
                return true;
            }
        }
        return false;
    }

    private int emptyCount() {
        int count = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "MathSet{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
