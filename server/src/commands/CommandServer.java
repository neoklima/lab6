package commands;

import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием
 */
public abstract class CommandServer implements Describable, Executable {
    private final String name;
    private final String description;

    public CommandServer(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Название и использование команды.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}