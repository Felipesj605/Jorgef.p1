CS 242 Problem Set – Lambda Expressions – Question 1: Assume we have a `HashMap<String, Integer>` called `map`. Use `Map.forEach` to print all of the keys and associated values stored in `map`.
Answer: `map.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));`

CS 242 Problem Set – Lambda Expressions – Question 2: Assume we have a `List<Integer>` called `list`. Use `List.replaceAll` to convert any odd number in `list` to an even number (by multiplying it by 2). Leave the even numbers alone.
Answer: `list.replaceAll(input -> (input % 2 != 0) ? input * 2 : input);`

CS 242 Problem Set – Lambda Expressions – Question 3: Assume we have a `List<Integer>` called `list` and a local integer variable called `factor`. Use `List.removeIf` to remove any numbers from `list` that can be divided evenly by `factor`.
Answer: `list.removeIf(input -> input % factor == 0);`

CS 242 Problem Set – Lambda Expressions – Question 4: Write `removeIf` for a `ULLinkedList`. You can assume that `ULLinkedList` has an iterator that supports `remove`.
Answer:
```java
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
```

CS 242 Problem Set – Brute Force Algorithms – Question 1: Develop a brute force algorithm to find the first instance of a substring in another string.
Answer: Scan the text from left to right. At each position, compare the characters of the pattern against the text sequentially. If a mismatch occurs, shift the starting position by one and repeat. The first position that matches every character is the answer; if none match, the substring does not appear.

CS 242 Problem Set – Brute Force Algorithms – Question 2: Write a brute force algorithm to solve the 8 Queens problem.
Answer: Place queens row by row. For the current row, test each column and place a queen only if no prior queen shares that column or either diagonal. If no column works, backtrack to the previous row, move that queen to its next feasible column, and continue. The first configuration that places eight queens without conflict is the solution.

CS 242 Problem Set – Brute Force Algorithms – Question 3: Fill in the function to generate the next possible combination for the 0-1 knapsack problem represented by a boolean array.
Answer:
```java
void generateNextCombination(boolean[] inKnapsack) {
    int index = inKnapsack.length - 1;
    while (index >= 0 && inKnapsack[index]) {
        inKnapsack[index] = false;
        index--;
    }
    if (index >= 0) {
        inKnapsack[index] = true;
    }
}
```

CS 242 Problem Set – Brute Force Algorithms – Question 4: Develop a brute force algorithm to play the breaker in Mastermind or Secret Password.
Answer: Enumerate all possible codes. Guess them in lexicographic order. After each response, discard every remaining code that would not have produced the same feedback for that guess. Continue until the current guess matches the hidden code.

CS 242 Problem Set – Decrease and Conquer Algorithms – Question 1: Use decrease and conquer to show which Tetris pieces can entirely cover a 16×16 board.
Answer: Identify each tetromino’s smallest tilable board (I on 4×1, O on 2×2, the remaining five on 4×4 arrangements). Replicate these minimal tilings by doubling both dimensions—copying the pattern into each quadrant—to tile 8×8 and then 16×16 boards. Every classic piece that tiles its minimal board continues to tile any board whose dimensions are powers of two, so all seven pieces can cover a 16×16 board.

CS 242 Problem Set – Decrease and Conquer Algorithms – Question 2: Provide a decrease-and-conquer solution for tiling an `m × m` board (power of two) with trominos, leaving one forbidden square uncovered.
Answer: Divide the board into four quadrants. Place one tromino at the center so it covers the squares adjacent to the true forbidden square in the three quadrants that do not contain it, creating a “local” forbidden square in each quadrant. Recurse on every quadrant. The base case is a 2×2 board with one forbidden square, which a single tromino covers exactly.

CS 242 Problem Set – Decrease and Conquer Algorithms – Question 3: Write insertion sort for an array of integers.
Answer:
```java
public static void insertionSort(int[] a) {
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
```

CS 242 Problem Set – Divide and Conquer – Question 1a: Given a sorted array of distinct integers `A`, determine if there exists an index `i` such that `A[i] == i`. Provide a divide-and-conquer solution in English that can be O(N).
Answer: Split the array into halves. Recursively search the left half; if no match is found, recursively search the right half. The recursive division satisfies divide-and-conquer, but in the worst case all elements are inspected, so the running time remains O(N).

CS 242 Problem Set – Divide and Conquer – Question 1b: Provide a decrease-and-conquer (O(log N)) solution to the fixed-point problem and justify its correctness.
Answer: Perform binary search. At midpoint `mid`, compare `A[mid]` with `mid`. If `A[mid] > mid`, any match must lie left because the array is strictly increasing; if `A[mid] < mid`, any match must lie right. If `A[mid] == mid`, return success. This halves the search space each step, giving O(log N) time.

CS 242 Problem Set – Divide and Conquer – Question 2: Fill in the recursive helper method for merge sort.
Answer:
```java
private static <E extends Comparable<? super E>> void mergeSort(E[] a, E[] tmpArray, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(a, tmpArray, left, mid);
        mergeSort(a, tmpArray, mid + 1, right);
        merge(a, left, mid + 1, right, tmpArray);
    }
}
```

CS 242 Problem Set – Divide and Conquer – Question 3: Merge sort uses a single temporary array. Explain why only one temporary array is needed.
Answer: Each merge step reads from the main array and writes into the shared temporary buffer covering the current subarray. Reusing the same buffer across recursive calls avoids repeated allocations while still providing the necessary workspace, maintaining O(N) auxiliary memory overall.

CS 242 Problem Set – Dynamic Programming – Question 1: Use the table provided to compute the Levenshtein distance between “planet” and “partner”.
Answer: The computed dynamic programming table is shown below (rows for “”, `P`, `L`, `A`, `N`, `E`, `T`; columns for “”, `P`, `A`, `R`, `T`, `N`, `E`, `R`). The distance is 4.

|   |   | P | A | R | T | N | E | R |
|---|---|---|---|---|---|---|---|---|
|   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
| P | 1 | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
| L | 2 | 1 | 1 | 2 | 3 | 4 | 5 | 6 |
| A | 3 | 2 | 1 | 2 | 3 | 4 | 5 | 6 |
| N | 4 | 3 | 2 | 2 | 3 | 3 | 4 | 5 |
| E | 5 | 4 | 3 | 3 | 3 | 4 | 3 | 4 |
| T | 6 | 5 | 4 | 4 | 3 | 4 | 4 | 4 |

CS 242 Problem Set – Greedy Algorithms – Question 1: Develop a greedy algorithm to select conduit lengths of 1′, 2′, 5′, and 15′ to connect two fire alarms with the fewest pieces.
Answer: Always choose the longest available conduit that does not exceed the remaining distance—first 15′, then 5′, then 2′, then 1′—subtracting each chosen length from the remaining run until it reaches zero. Because the lengths form a canonical system, this greedy strategy is optimal.

CS 242 Problem Set – Greedy Algorithms – Question 2: Develop a greedy algorithm for the 0/1 knapsack problem and state whether it always finds the optimal solution.
Answer: Sort items by value-to-weight ratio in descending order and add each item if it fits in the remaining capacity. This greedy method can fail—e.g., with capacity 10 and items (value, weight) pairs (10, 9), (9, 5), (9, 5), the greedy algorithm selects the two 9/5 items for value 18 but cannot add the 10/9 item, even though the true optimum involves a different combination. Therefore it is not always optimal.

CS 242 Problem Set – Greedy Algorithms – Question 3: Draw the Huffman tree for the symbols Q:4, R:9, S:55, T:18, V:4, X:10 and compute the compression ratio.
Answer: The Huffman tree (weights in parentheses) is:

```
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
```

Codes: `S = 1`, `T = 00`, `X = 010`, `R = 0111`, `Q = 01100`, `V = 01101`. Expected bits: `(55·1 + 18·2 + 10·3 + 9·4 + 4·5 + 4·5) = 197` for 100 symbols. A fixed-length 3-bit encoding needs 300 bits, so the compression ratio is approximately 300 / 197 ≈ 1.52.

CS 242 Problem Set – Backtracking – Question 1: Write the promising function for the backtracking 0-1 knapsack algorithm.
Answer:
```java
boolean isPromising(int i, int currentProfit, int currentWeight) {
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
```

CS 242 Problem Set – Backtracking – Question 2: Fill in the recursive function that solves the backtracking knapsack problem.
Answer:
```java
void knapsack(int i, int currentProfit, int currentWeight) {
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
```

CS 242 Problem Set – Randomized Algorithms – Question 1: Create a randomized algorithm to estimate the area of an irregular black shape on a white canvas stored as a boolean grid. Classify the algorithm as Monte Carlo or Las Vegas.
Answer: Uniformly sample `k` random cells, count how many are black, and estimate the area as `(blackCount / k) × totalCells`. Runtime is O(k); accuracy improves with larger k. The algorithm may return an approximate result, so it is Monte Carlo.

CS 242 Problem Set – Randomized Algorithms – Question 2: Implement the randomized area estimation algorithm.
Answer:
```java
int irregularArea(boolean[][] canvas) {
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
```

CS 242 Problem Set – Heap Sort – Question 1: Implement `siftDownHeapify`.
Answer:
```java
static <T extends Comparable<? super T>> void siftDownHeapify(T[] array, int pos, int n) {
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
```

CS 242 Problem Set – Heap Sort – Question 2: Implement `heapify`.
Answer:
```java
static <T extends Comparable<? super T>> void heapify(T[] array) {
    for (int i = (array.length / 2) - 1; i >= 0; i--) {
        siftDownHeapify(array, i, array.length);
    }
}
```

CS 242 Problem Set – Heap Sort – Question 3: Implement `heapSort`.
Answer:
```java
static <T extends Comparable<? super T>> void heapSort(T[] array) {
    heapify(array);
    for (int end = array.length - 1; end > 0; end--) {
        swap(array, 0, end);
        siftDownHeapify(array, 0, end);
    }
}
```

CS 242 Problem Set – Quick Sort – Question 1: Fill in the quicksort recursive helper function.
Answer:
```java
static final int CUTOFF = 10;

static <T extends Comparable<? super T>> void quicksort(T[] array, int left, int right) {
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
```

CS 242 Problem Set – Quick Sort – Question 2: Fill in the `medianOf3` method.
Answer:
```java
static <T extends Comparable<? super T>> T medianOf3(T[] array, int left, int right) {
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
```

CS 242 Problem Set – Non-comparison Sorts – Question 1: Perform a radix sort on 888, 523, 15, 5, 988, 196, 122 and show each pass.
Answer: Pass 1 (ones place) → `888, 988, 15, 5, 196, 122, 523`; Pass 2 (tens place) → `5, 15, 122, 523, 196, 888, 988`; Pass 3 (hundreds place) → `5, 15, 122, 196, 523, 888, 988`.

CS 242 Problem Set – Non-comparison Sorts – Question 2: Implement counting sort for integers.
Answer:
```java
static void countingSort(int[] array, int maxInt) {
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
```
