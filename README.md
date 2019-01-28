# Sandbox

DataGenerator - модуль позволяет сгенерировать тестовые данные согласно формату
DataGroupingProcessor - позволяет обработать тестовые данные и записать результаты в файлы

Из модулей было убрано логирование log4j для облегчения понимания. 
Кроме того, использовалось минимальное число зависимостей. Spring не был задействован, я посчитал что в таком небольшом
проекте использование тяжеловесного фреймворка чрезмерно. Внедрение зависимостей происходит вручную.

Программа расширяема, для обработки большого объема тестовых данных можно написать свою реализацию Counter,
например с храненнием промежуточных данных в базе данных, или в распределенном хранилище Redis.
Был выбрана простая реализация - на мапе.
При необходимости можно так же добавлять другие реализации ResourceReader или Writer 
для чтения\записи из разных источников - из базы данных, из удаленных сервисов и т.д.