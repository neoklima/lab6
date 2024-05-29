package utility;

/**
 * Интерфейс Validatable представляет объект, который может быть проверен на корректность.
 */
public interface Validatable {
    /**
     * Проверяет объект на корректность.
     *
     * @return true, если объект корректен, в противном случае - false.
     */
    boolean validate();
}
