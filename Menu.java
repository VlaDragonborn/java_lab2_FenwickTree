import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Menu {
    private static FenwickTree currentTree;
    private static int[] currentArray;
    private static final Scanner scanner = new Scanner(System.in);

    public static int[][] generateTestArrays() {
        return new int[][] {
                {1},
                {2, 7},
                {-3, 5, -2, 8, -1},
                {0, 0, 0, 0, 0, 0},
                generateRandomArray(6, -10, 10),
                generateRandomArray(10, 1, 100),
                {1000, 2000, 3000, 4000, 5000},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                generateRandomArray(15, -50, 50)
        };
    }

    public static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Ошибка: Ввод не может быть пустым. Попробуйте снова.");
                    continue;
                }

                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.printf("Ошибка: Введите число от %d до %d. Попробуйте снова.\n", min, max);
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное целое число. Попробуйте снова.");
            }
        }
    }

    private static int readInt(String prompt) {
        return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public void tests() {
        int[][] test = generateTestArrays();

        for(int i = 0; i < test.length; i++) {
            int[] arr = test[i];
            FenwickTree f_tree = new FenwickTree(arr);

            System.out.println("=== Test_" + (i+1) + " ===");
            System.out.println("Array: " + Arrays.toString(arr));

            System.out.print("Prefix sums: [");
            for(int j = 0; j < f_tree.getSize(); j++) {
                System.out.print(f_tree.prefixSum(j));
                if (j < f_tree.getSize() - 1) System.out.print(", ");
            }
            System.out.println("]");

            if (arr.length > 1) {
                System.out.print("Range sums: ");
                for (int left = 0; left < Math.min(2, arr.length); left++) {
                    for (int right = left + 1; right < Math.min(left + 3, arr.length); right++) {
                        System.out.printf("[%d-%d]=%d ", left, right, f_tree.rangeSum(left, right));
                    }
                }
                System.out.println();
            }
            System.out.println("==========\n");
        }

        System.out.println("Нажмите Enter для продолжения...");
        scanner.nextLine();
    }

    public void manualTest() {
        System.out.println("=== Ручное тестирование ===");
        System.out.println("1) Создать массив вручную");
        System.out.println("2) Использовать случайный массив");

        int choice = readInt("Выберите вариант: ", 1, 2);

        if (choice == 1) {
            createManualArray();
        } else {
            createRandomArray();
        }

        if (currentArray != null) {
            currentTree = new FenwickTree(currentArray);
            System.out.println("Создан массив: " + Arrays.toString(currentArray));
            System.out.println("Дерево Фенвика построено!\n");
            testFunctions();
        }
    }

    private void createManualArray() {
        int size = readInt("Введите размер массива (1-100): ", 1, 100);
        currentArray = new int[size];

        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            currentArray[i] = readInt("arr[" + i + "] = ");
        }
    }

    private void createRandomArray() {
        int size = readInt("Введите размер массива (1-100): ", 1, 100);
        int min = readInt("Введите минимальное значение: ");
        int max = readInt("Введите максимальное значение: ");

        if (min > max) {
            System.out.println("Ошибка: Минимальное значение не может быть больше максимального. Они будут заменены");
            int temp = min;
            min = max;
            max = temp;
        }

        currentArray = generateRandomArray(size, min, max);
    }

    public void testFunctions() {
        if (currentTree == null) {
            System.out.println("Сначала создайте дерево Фенвика!");
            return;
        }

        while (true) {
            System.out.println("\n=== Тестирование функций ===");
            System.out.println("Текущий массив: " + Arrays.toString(currentArray));
            System.out.println("Размер: " + currentArray.length);
            System.out.println("1) Показать префиксные суммы");
            System.out.println("2) Вычислить сумму на диапазоне");
            System.out.println("3) Обновить элемент");
            System.out.println("4) Показать дерево");
            System.out.println("5) Вернуться в главное меню");

            int choice = readInt("Выберите функцию: ", 1, 6);

            switch (choice) {
                case 1:
                    showPrefixSums();
                    break;
                case 2:
                    calculateRangeSum();
                    break;
                case 3:
                    updateElement();
                    break;
                case 4:
                    showTree();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void showPrefixSums() {
        System.out.print("Префиксные суммы: [");
        for (int i = 0; i < currentTree.getSize(); i++) {
            System.out.print(currentTree.prefixSum(i));
            if (i < currentTree.getSize() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    private void calculateRangeSum() {
        int left = readInt("Введите левую границу (0-" + (currentArray.length - 1) + "): ",
                0, currentArray.length - 1);
        int right = readInt("Введите правую границу (" + left + "-" + (currentArray.length - 1) + "): ",
                left, currentArray.length - 1);

        try {
            int result = currentTree.rangeSum(left, right);
            System.out.printf("rangeSum(%d, %d) = %d\n", left, right, result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void updateElement() {
        int index = readInt("Введите индекс элемента (0-" + (currentArray.length - 1) + "): ",
                0, currentArray.length - 1);
        int delta = readInt("Введите значение, которое хотите прибавить: ");

        try {
            System.out.printf("До обновления: arr[%d] = %d\n", index, currentArray[index]);
            currentTree.update(index, delta);
            currentArray[index] += delta;
            System.out.printf("После обновления: arr[%d] = %d\n", index, currentArray[index]);
            System.out.println("Массив обновлен: " + Arrays.toString(currentArray));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showTree() {
        System.out.println("Внутреннее дерево (1-индексация):");
        for (int i = 1; i <= currentTree.getSize(); i++) {
            System.out.printf("tree[%d] = %d\n", i, getTreeValue(currentTree, i));
        }
    }

    private int getTreeValue(FenwickTree tree, int index) {
        try {
            java.lang.reflect.Field field = FenwickTree.class.getDeclaredField("tree");
            field.setAccessible(true);
            int[] treeArray = (int[]) field.get(tree);
            return treeArray[index];
        } catch (Exception e) {
            System.out.println("Не удалось получить доступ к внутреннему дереву");
            return -1;
        }
    }

    public void start() {
        System.out.println("=== ДЕРЕВО ФЕНВИКА - ТЕСТИРОВАНИЕ ===");
        System.out.println("Программа для тестирования реализации дерева Фенвика");

        while (true) {
            try {
                System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
                System.out.println("1) Автоматические тесты");
                System.out.println("2) Ручное тестирование");
                System.out.println("3) Выход");

                int choose = readInt("Ваш выбор: ", 1, 3);

                switch (choose) {
                    case 1:
                        tests();
                        break;
                    case 2:
                        manualTest();
                        break;
                    case 3:
                        System.out.println("Выход из программы");
                        return;
                }

            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
                System.out.println("Попробуйте снова.");
            }
        }
    }
}
