Команда для запуска приложения в докер

Полезная статья про докер https://habr.com/ru/company/ruvds/blog/485650/
Полезно про монго https://www.youtube.com/watch?v=ssj0CGxv60k
Сборка
docker image build . -t sudoku-ninja:1.0.0
Запуск контейнера 
docker run -d --name sudoku-ninja -p 8080:8080 sudoku-ninja:1.0.0
docker-compose command

