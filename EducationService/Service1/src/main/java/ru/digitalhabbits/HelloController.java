package ru.digitalhabbits;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        //  Почему в ArrayList<> необязательно писать тип String.
        /*
         * Не нужно указывать параметр типа, надо
         * просто указать, что он имеется, с помощью оператора бубны, или ромба (<>).
         * После этого компилятор выведет правильный фактический параметр типа
         */

        ExecutorService executorService = Executors.newWorkStealingPool();

        List<? extends String> stringList = new ArrayList<>();
        // Если в List<String> поместить 1_000_000 записей стрим будет брать по одной или сразу все?
        /*
            По одной
            https://annimon.com/article/2778
         */
        stringList.stream()
                // Главный вопрос.
                // Откуда берутся потоки чтобы можно было использовать parallel (что за потоки, сколько их и т.д.)
                /*
                 Фреймворк fork/join был разработан для рекурсивного разделения параллелизуемой задачи на более мелкие задачи,
                 а затем объединения результатов каждой подзадачи для получения общего результата.
                 Это реализация ExecutorService интерфейс, который распределяет эти подзадачи для рабочих потоков в пуле потоков, называемый ForkJoinPool

                 Алгоритм, который разделяет поток на несколько частей, является рекурсивным процессом.
                 На первом этапе метод называется trySplit вызывается на первом Spliterator и генерирует второй.
                 Затем на шаге 2 он снова вызывается на этих двух Spliterator, в результате чего в общей сложности четыре.
                 Фреймворк продолжает вызывать метод trySplit на Spliterator до тех пор, пока он не вернет значение null,
                 чтобы указать, что обрабатываемая структура данных больше не делится.
                 Наконец, этот процесс рекурсивного разбиения завершается, когда все Spliterators вернули null для вызова trySplit.

                 Параллельные потоки внутренне используют значение по умолчанию ForkJoinPool,
                 который по умолчанию имеет столько потоков, сколько у вас есть процессоров,
                 что возвращается Runtime.getRuntime().availableProcessors(),
                 Но вы можете изменить размер этого пула, используя системное свойство
                 java.util.concurrent.ForkJoinPool.common.parallelism

                */
                .parallel()
                .map(s -> s)
                // Придумать задачу при котором нужен parallel затем sequential
                /*

                 */
                .sequential()
                .map(s -> s)
                .collect(Collectors.toList());

        return "Greetings from Spring Boot!";
    }
}