package com.mariiapasichna;

/*
1) Есть класс User:
class User {
int id;
String name;
int age;
}
Написать интерфейс Storage, и класс FileStorage который в конструкторе принимает имя файла где будут храниться данные:
- void removeAll()
- void removeUser(int id)
- void removeUserByName(String name)
- void addUser(User user)
- void updateUser(User user)
- User getUser(int id)
- List<User> getAllUsers()
Продемонстрирвоать работу всех методов.
Данные должны храниться в тектовом файле в формате JSON.
При добавлении метод addUser должен назначить User уникальный id - порядковый номер.
*/

public class Task1 {

    public static final String STORAGE = "Storage.txt";

    public static void main(String[] args) {
        Storage storage = FileStorage.getInstance(STORAGE);
        storage.addUser(new User("Alex", 22));
        storage.addUser(new User("Ben", 33));
        storage.addUser(new User("Carl", 33));
        storage.getAllUsers();
        storage.removeAll();
        storage.getAllUsers();
        storage.addUser(new User("Jon", 33));
        storage.addUser(new User("Anna", 33));
        storage.addUser(new User("Bob", 33));
        storage.addUser(new User("Helen", 33));
        storage.getAllUsers();
        storage.removeUserByName("Anna");
        storage.getAllUsers();
        System.out.println(storage.getUser(3));
        storage.updateUser(new User(2, "Bob", 44));
        storage.getAllUsers();
        storage.removeUser(2);
        storage.getAllUsers();
        storage.addUser(new User("Bill", 11));
        storage.getAllUsers();
        storage.removeUser(1);
        storage.getAllUsers();
        storage.addUser(new User("Anna", 23));
        storage.getAllUsers();
    }
}