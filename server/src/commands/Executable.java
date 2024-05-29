package commands;

import requests.Request;
import responses.Response;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    /**
     * Выполнить что-либо.
     * @param request запрос с данными для выполнения команды
     * @return результат выполнения
     */
    Response apply(Request request);
}