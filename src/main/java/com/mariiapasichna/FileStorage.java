package com.mariiapasichna;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {
    private static FileStorage instance;
    private String fileName;
    @Expose
    private List<User> list = new ArrayList<>();
    @Expose
    private int idAdd = 1;

    public static synchronized FileStorage getInstance(String fileName) {
        if (instance == null) {
            instance = new FileStorage(fileName);
        }
        instance.setFileName(fileName);
        return instance;
    }

    private FileStorage(String fileName) {
        this.fileName = fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void removeAll() {
        list.clear();
        idAdd = 1;
        save();
    }

    @Override
    public void removeUser(int id) {
        list.removeIf(user -> user.getId() == id);
        save();
    }

    @Override
    public void removeUserByName(String name) {
        list.removeIf(user -> user.getName().equals(name));
        save();
    }

    @Override
    public void addUser(User user) {
        user.setId(idAdd);
        list.add(user);
        idAdd++;
        save();
    }

    @Override
    public void updateUser(User user) {
        for (User user1 : list) {
            if (user1.getId() == user.getId()) {
                user1.setName(user.getName());
                user1.setAge(user.getAge());
                break;
            }
        }
        save();
    }

    @Override
    public User getUser(int id) {
        for (User user : list) {
            if (user.getId() == id) {
                return list.get(list.indexOf(user));
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        load();
        idAdd = 1;
        for (User user : list) {
            user.setId(idAdd);
            idAdd++;
        }
        save();
        return list;
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            bufferedWriter.write(gson.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String load;
            while ((load = bufferedReader.readLine()) != null) {
                Gson gson = new GsonBuilder().create();
                FileStorage fileStorage = gson.fromJson(load, FileStorage.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}