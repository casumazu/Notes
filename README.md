# Notes
Приложение для заметок

Возможности:

Создание пользователя

        POST http://localhost:8080/users
        {
            "name":"Bogdan",
            "email": "sergeev.bog@yandexsss.ru"
        }
        
        
Создание заметки

        POST http://localhost:8080/note/::userId
        
        {  
            "name": "Заголовок заметки",
            "description": "Описание 1 заметки"
        }

Обновление заметки (для создателя и списка приглашенных людей)
        
        PATH http://localhost:8080/note/::userId/::noteId
        
Просмотр заметок пользователя

        GET http://localhost:8080/note/::userId
        
Передача заметки от пользователя к пользователю (копирование к другому пользователю)

        POST http://localhost:8080/note/sendNote/::userIdFrom/::userIdTo/::noteId
        
Принятие заметки от пользователя к пользователю (из запросов)

        POST http://localhost:8080/note/sendNote/::userId/::noteId
        
Добавление общего пользователя в заметку (для общего редактирования)

        http://localhost:8080/note/add/2/1


