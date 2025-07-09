import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

public class TaskManagerTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private TaskManager tm;
    //HOla
    @BeforeEach
    void setUp() {
        // Capturar la salida de consola
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        tm = new TaskManager();
    }

    @AfterEach
    void tearDown() {
        // Restaurar la salida de consola original
        System.setOut(originalOut);
    }
    
    @Test
    void pruebaDeVerificacion() {
        // Esta es una prueba sencilla para verificar que el entorno de pruebas funciona.
        // No afecta el código de la aplicación, solo confirma que se pueden ejecutar aserciones.
        assertTrue(true, "Esta prueba debe pasar siempre.");
    }

    @Test
    void testAddTask() {
        tm.addTask("Test Task");
        String output = outContent.toString();
        assertTrue(output.contains("Task added."), "Debe indicar que la tarea fue agregada");
    }

    @Test
    void testListTasksAfterAdd() {
        tm.addTask("Task 1");
        outContent.reset();
        tm.listTasks();
        String output = outContent.toString();
        assertTrue(output.contains("Task 1: Task 1"), "Debe listar la tarea agregada con su índice");
    }

    @Test
    void testRemoveTask() {
        tm.addTask("Task A");
        tm.addTask("Task B");
        outContent.reset();

        tm.removeTask(1);
        String removeOutput = outContent.toString();
        assertTrue(removeOutput.contains("Task removed."), "Debe indicar que la tarea fue eliminada");

        outContent.reset();
        tm.listTasks();
        String listOutput = outContent.toString();
        assertFalse(listOutput.contains("Task 1: Task A"), "No debe listar la tarea eliminada");
        assertTrue(listOutput.contains("Task 1: Task B"), "Debe renumerar y listar la tarea restante");
    }

    @Test
    void testRemoveTaskInvalidIdThrows() {
        // Sin tareas, remover debe lanzar excepción
        assertThrows(IndexOutOfBoundsException.class, () -> tm.removeTask(1));
    }

    @Test
    void testListTasksEmpty() {
        outContent.reset();
        tm.listTasks();
        String output = outContent.toString();
        assertTrue(output.isEmpty(), "No debe imprimir nada si no hay tareas");
    }
}
