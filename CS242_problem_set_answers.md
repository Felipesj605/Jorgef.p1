CS 242 Problem Set
Lambda Expressions

1. Assume we have a HashMap<String, Integer> called map. Use Map.forEach to print all of the keys and associated values stored in map.
	map.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));

2. Assume we have a List<Integer> called list. Use List.replaceAll to convert any odd number in list to an even number (by multiplying it by 2). Leave the even numbers alone.
	list.replaceAll(input -> (input % 2 != 0) ? input * 2 : input);

3. Assume we have a List<Integer> called list and a local integer variable called factor. Use List.removeIf remove any numbers from list that can be divided evenly by factor.
	list.removeIf(input -> input % factor == 0);

4. Write removeIf for a ULLinkedList. You can assume that ULLinkedList has an iterator that supports remove.
	public <E> boolean removeIf(Predicate<? super E> predicate) {
	    Iterator<E> iterator = iterator();
	    boolean removedAny = false;
	    while (iterator.hasNext()) {
	        E value = iterator.next();
	        if (predicate.test(value)) {
	            iterator.remove();
	            removedAny = true;
	        }
	    }
	    return removedAny;
	}

CS 242 Problem Set
Brute Force Algorithms

1. Develop a brute force algorithm to find the first instance of a substring in another string, e.g., find the first instance of ‘oat’ in the sentence “I lent my boat to Sammy.” No code, just a description of the algorithm.
	Scan the text from left to right. At each starting index compare every character of the pattern to the text; if any character mismatches, advance the start by one and repeat. The first starting index that matches every character is the answer; if none match, the substring does not occur.

2. Write a brute force algorithm to solve the 8 Queens problem. No code, just a description of the algorithm.
	Place queens one row at a time. For the current row try each column, keeping any placement that conflicts with no earlier queen in column or diagonal. If a row has no legal column, backtrack to the previous row and move that queen to its next legal column. The first full placement of eight queens without conflicts solves the problem.

3. Fill in the following function to generate the next possible combination for the 0-1 knapsack problem. If the ith index of the boolean array is true, the item is in the knapsack.
	void generateNextCombination(boolean[] inKnapsack){
	    int index = inKnapsack.length - 1;
	    while (index >= 0 && inKnapsack[index]) {
	        inKnapsack[index] = false;
	        index--;
	    }
	    if (index >= 0) {
	        inKnapsack[index] = true;
	    }
	}

4. Develop a brute force algorithm to play the breaker in a game of Mastermind or Secret Password. No code, just a description of the algorithm.
	List all codes in the search space and guess them in a fixed order. After each feedback response, discard every remaining code that would not produce the same feedback if it had been guessed. The first remaining code that matches the feedback exactly is the secret.

CS 242 Problem Set
Decrease and Conquer Algorithms

1. Recall the pieces from a game of Tetris. Use decrease and conquer to show which pieces can be used to entirely cover a 16x16 board.
	Verify each tetromino tiles its smallest board (I on a 4×1 strip, O on a 2×2 square, the others on 4×4 layouts). Duplicate each tiling across quadrants to cover 8×8 and then 16×16 boards. Every piece that tiles its minimal board continues to tile boards whose dimensions double, so all seven tetrominoes can cover 16×16.

2. Find a decrease and conquer solution to the tromino tiling problem with one forbidden square.
	Split the board into four quadrants. Place a single tromino at the center so it covers the three squares adjacent to the forbidden square in the other quadrants, creating one “forbidden” square per quadrant. Recurse on each quadrant until reaching a 2×2 board, which a single tromino covers.

3. Write insertion sort for an array of integers.
	public static void insertionSort(int[] a){
	    for (int i = 1; i < a.length; i++) {
	        int key = a[i];
	        int j = i - 1;
	        while (j >= 0 && a[j] > key) {
	            a[j + 1] = a[j];
	            j--;
	        }
	        a[j + 1] = key;
	    }
	}

CS 242 Problem Set
Divide and Conquer

1. Given a sorted array of distinct integers A. We need to determine if there exists an entry A[i] == i.
   a. Provide a divide-and-conquer solution to the problem (in English). The algorithm can be O(N).
	Split the array into halves; search the left half, and if no match exists search the right half. This recursion divides the problem but in the worst case inspects every element, so it is O(N).
   b. Provide a decrease-and-conquer solution to the problem (in English). The algorithm should be O(logN). Demonstrate it works by showing some cases or equivalence partitions.
	Apply binary search: compare A[mid] to mid. If A[mid] > mid search the left half, if A[mid] < mid search the right half, and if equal return success. Each step halves the range, giving O(log N) time.

2. Fill in this recursive helper method for mergeSort.
	private static <E extends Comparable<? super E>> void mergeSort( E[] a, E[] tmpArray, int left, int right){
	    if (left < right) {
	        int mid = left + (right - left) / 2;
	        mergeSort(a, tmpArray, left, mid);
	        mergeSort(a, tmpArray, mid + 1, right);
	        merge(a, left, mid + 1, right, tmpArray);
	    }
	}

3. Performs a merge sort on the array. Explain why we only need one temporary array.
	Every merge reads from the primary array and writes into the shared temporary buffer covering that subarray. Reusing one buffer across recursive calls supplies all needed workspace without repeated allocations, so a single temporary array suffices.

CS 242 Problem Set
Dynamic Programming

1. Use the table below to calculate the Levenshtein distance between planet and partner.
	Distance = 4. Completed table (rows “” P L A N E T; columns “” P A R T N E R):
	0 1 2 3 4 5 6 7
	1 0 1 2 3 4 5 6
	2 1 1 2 3 4 5 6
	3 2 1 2 3 4 5 6
	4 3 2 2 3 3 4 5
	5 4 3 3 3 4 3 4
	6 5 4 4 3 4 4 4

CS 242 Problem Set
Greedy Algorithms

1. Bobby needs conduit pieces (1′, 2′, 5′, 15′). Develop a greedy algorithm to use as few pieces as possible without cutting.
	Repeatedly pick the longest piece that does not exceed the remaining distance—first 15′, then 5′, then 2′, then 1′—until the run is exactly filled. Canonical lengths guarantee optimality.

2. Develop a greedy algorithm for the 0/1 knapsack problem. Does this algorithm always find the optimal solution?
	Sort items by value-to-weight ratio descending and add each item that fits. This greedy strategy can miss the optimum (e.g., capacity 10 with items (10,9), (9,5), (9,5)); therefore it is not always optimal.

3. Draw the Huffman tree for Q:4, R:9, S:55, T:18, V:4, X:10. Compute the compression ratio.
	Huffman tree:
	        (100)
	       /     \
	    (45)      S55
	    /  \
	  T18  (27)
	       /  \
	     X10  (17)
	          /  \
	        (8)   R9
	       /  \
	     Q4   V4
	Codes: S=1, T=00, X=010, R=0111, Q=01100, V=01101. Expected bits = 197 for 100 symbols; fixed 3-bit encoding uses 300 bits, so compression ratio ≈ 300/197 ≈ 1.52.

CS 242 Problem Set
Backtracking

1. Write the algorithm for the promising function.
	boolean isPromising(int i, int currentProfit, int currentWeight){
	    if (currentWeight > maxWeight) {
	        return false;
	    }
	    double bound = currentProfit;
	    int weight = currentWeight;
	    int k = i;
	    while (k < weights.length && weight + weights[k] <= maxWeight) {
	        weight += weights[k];
	        bound += values[k];
	        k++;
	    }
	    if (k < weights.length) {
	        bound += (double) values[k] / weights[k] * (maxWeight - weight);
	    }
	    return bound > maxprofit;
	}

2. Assume that the promising function is provided. Fill in the recursive knapsack solver.
	void knapsack(int i, int currentProfit, int currentWeight){
	    if (currentWeight <= maxWeight && currentProfit > maxprofit) {
	        maxprofit = currentProfit;
	        for (int k = 0; k < include.length; k++) {
	            bestset[k] = include[k];
	        }
	    }
	    if (i < include.length && isPromising(i, currentProfit, currentWeight)) {
	        include[i] = true;
	        knapsack(i + 1, currentProfit + values[i], currentWeight + weights[i]);
	        include[i] = false;
	        knapsack(i + 1, currentProfit, currentWeight);
	    }
	}

CS 242 Problem Set
Randomized Algorithms

1. Create an algorithm to find the area of an irregular black shape on a white canvas. Classify it as Monte Carlo or Las Vegas.
	Sample k random cells, count how many are black, and estimate area as (blackCount / k) × totalCells. Runtime is O(k); result is probabilistic, so it is a Monte Carlo algorithm.

2. Code it up: int irregularArea(boolean[][] canvas){
	int irregularArea(boolean[][] canvas){
	    int rows = canvas.length;
	    int cols = rows == 0 ? 0 : canvas[0].length;
	    if (rows == 0 || cols == 0) {
	        return 0;
	    }
	    int samples = Math.max(1, rows * cols / 10);
	    java.util.Random random = new java.util.Random();
	    int black = 0;
	    for (int t = 0; t < samples; t++) {
	        int r = random.nextInt(rows);
	        int c = random.nextInt(cols);
	        if (canvas[r][c]) {
	            black++;
	        }
	    }
	    double fraction = (double) black / samples;
	    return (int) Math.round(fraction * rows * cols);
	}

CS 242 Problem Set
Heap Sort

1. This method should move the item at pos down the array until it is in the correct place. pos is the position of the item to sift down and n is the logical size of the heap (needed for part 3).
	static <T extends Comparable>
	void siftDownHeapify(T[] array, int pos, int n){
	    int child = pos * 2 + 1;
	    while (child < n) {
	        int right = child + 1;
	        int largerChild = child;
	        if (right < n && array[right].compareTo(array[child]) > 0) {
	            largerChild = right;
	        }
	        if (array[largerChild].compareTo(array[pos]) > 0) {
	            swap(array, pos, largerChild);
	            pos = largerChild;
	            child = pos * 2 + 1;
	        } else {
	            break;
	        }
	    }
	}

2. This method should “heapify” an array.
	static <T extends Comparable>
	void heapify( T[] array ){
	    for (int i = (array.length / 2) - 1; i >= 0; i--) {
	        siftDownHeapify(array, i, array.length);
	    }
	}

3. This function should heap sort the array.
	static <T extends Comparable>
	void heapSort(T[] array){
	    heapify(array);
	    for (int end = array.length - 1; end > 0; end--) {
	        swap(array, 0, end);
	        siftDownHeapify(array, 0, end);
	    }
	}

CS 242 Problem Set
Quick Sort

1. Fill in the quicksort recursive helper function.
	static final int CUTOFF = 10;
	static <T extends Comparable>
	void quicksort(T[] array, int left, int right){
	    if (left + CUTOFF <= right) {
	        T pivot = medianOf3(array, left, right);
	        int i = left;
	        int j = right - 1;
	        while (true) {
	            while (array[++i].compareTo(pivot) < 0) { }
	            while (array[--j].compareTo(pivot) > 0) { }
	            if (i < j) {
	                swap(array, i, j);
	            } else {
	                break;
	            }
	        }
	        swap(array, i, right - 1);
	        quicksort(array, left, i - 1);
	        quicksort(array, i + 1, right);
	    } else {
	        insertionSort(array, left, right);
	    }
	}

2. Fill in the medianOf3 method.
	static <T extends Comparable>
	T medianOf3(T[] array, int left, int right){
	    int center = left + (right - left) / 2;
	    if (array[center].compareTo(array[left]) < 0) {
	        swap(array, left, center);
	    }
	    if (array[right].compareTo(array[left]) < 0) {
	        swap(array, left, right);
	    }
	    if (array[right].compareTo(array[center]) < 0) {
	        swap(array, center, right);
	    }
	    swap(array, center, right - 1);
	    return array[right - 1];
	}

CS 242 Problem Set
Non-comparison Sorts

1. Perform a radix sort of the following list of numbers: 888, 523, 15, 5, 988, 196, 122. Show each pass.
	After ones pass: 888, 988, 15, 5, 196, 122, 523. After tens pass: 5, 15, 122, 523, 196, 888, 988. After hundreds pass: 5, 15, 122, 196, 523, 888, 988.

2. Implement counting sort for integers.
	static void countingSort(int[] array, int maxInt){
	    int[] counts = new int[maxInt + 1];
	    for (int value : array) {
	        counts[value]++;
	    }
	    int index = 0;
	    for (int value = 0; value <= maxInt; value++) {
	        for (int c = 0; c < counts[value]; c++) {
	            array[index++] = value;
	        }
	    }
	}
