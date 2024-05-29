package repositories;

import managers.DumpManager;
import models.Vehicle;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Управляет коллекцией продуктов, используя TreeSet.
 */
public class VehicleRepository {
    private TreeSet<Vehicle> collection;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    /**
     * Конструктор ProductRepository, загружающий начальную коллекцию.
     * @param dumpManager Менеджер, отвечающий за сохранение и загрузку коллекции.
     */
    public VehicleRepository(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
        this.collection = new TreeSet<>();
        load();
    }

    /**
     * Возвращает коллекцию продуктов.
     * @return TreeSet, содержащий все продукты.
     */
    public TreeSet<Vehicle> get() {
        return collection;
    }

    /**
     * Возвращает время последней инициализации.
     * @return LocalDateTime последней инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Возвращает время последнего сохранения.
     * @return LocalDateTime последнего сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Возвращает имя типа коллекции.
     * @return Строковое представление типа коллекции.
     */
    public String type() {
        return collection.getClass().getName();
    }

    /**
     * Возвращает размер коллекции.
     * @return Количество продуктов в коллекции.
     */
    public int size() {
        return collection.size();
    }

    /**
     * Возвращает первый продукт в коллекции или null, если коллекция пуста.
     * @return Первый продукт в TreeSet или null.
     */
    public Vehicle first() {
        return collection.isEmpty() ? null : collection.first();
    }

    /**
     * Возвращает последний продукт в коллекции или null, если коллекция пуста.
     * @return Последний продукт в TreeSet или null.
     */
    public Vehicle last() {
        return collection.isEmpty() ? null : collection.last();
    }

    /**
     * Возвращает отсортированный список продуктов.
     * @return Список, содержащий продукты в их естественном порядке.
     */
    public List<Vehicle> sorted() {
        return new ArrayList<>(collection);
    }

    /**
     * Получает продукт по его ID.
     * @param id ID продукта.
     * @return Продукт с указанным ID или null, если такой не найден.
     */
    public Vehicle getById(long id) {
        return collection.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Проверяет, существует ли продукт с указанным ID в коллекции.
     * @param id ID продукта.
     * @return true, если продукт существует, иначе false.
     */
    public boolean checkExist(long id) {
        return collection.stream().anyMatch(product -> product.getId() == id);
    }

    /**
     * Добавляет продукт в коллекцию.
     * @param product Продукт для добавления.
     */
    public int add(Vehicle product) {
        collection.add(product);
        return (int) product.getId();
    }

    /**
     * Удаляет продукт по его ID из коллекции.
     * @param id ID продукта для удаления.
     */
    public void remove(long id) {
        collection.removeIf(product -> product.getId() == id);
    }

    /**
     * Очищает всю коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Сохраняет коллекцию в файл с помощью DumpManager.
     */
    public void save() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Проверяет, есть ли продукт с указанным ID в коллекции.
     * @param id ID продукта.
     * @return true, если продукт есть в коллекции, иначе false.
     */
    public boolean contains(long id) {
        return checkExist(id);
    }
    /**
     * Загружает коллекцию из файла с помощью DumpManager.
     */
    private void load() {
        TreeSet<Vehicle> newCollection = (TreeSet<Vehicle>) dumpManager.readCollection(collection);
        if (newCollection != null) {
            collection = newCollection;
        } else {
            collection = new TreeSet<>();
        }
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Возвращает все продукты в коллекции.
     * @return Коллекция всех продуктов.
     */
    public Collection<Vehicle> getAll() {
        return new ArrayList<>(collection);
    }
}