package com.mariiapasichna;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {
    private static FileStorage instance;
    List<User> list = new ArrayList<>();
    private String fileName;
    private int idAdd = 1;

    public static synchronized FileStorage getInstance(String fileName) {
        if (instance == null) {
            instance = new FileStorage(fileName);
        }
        return instance;
    }

    public FileStorage(String fileName) {
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
        SaveStorage saveStorage = load();
        this.idAdd = saveStorage.idAdd;
        this.list = saveStorage.list;
        idAdd = 1;
        for (User user : list) {
            user.setId(idAdd);
            idAdd++;
        }
        save();
        System.out.println(list);
        return list;
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Gson gson = new GsonBuilder().create();
            SaveStorage saveStorage = new SaveStorage(idAdd, list);
            writer.write(gson.toJson(saveStorage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SaveStorage load() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String load;
            while ((load = br.readLine()) != null) {
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(load, SaveStorage.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class SaveStorage {
        int idAdd;
        List<User> list;

        public SaveStorage(int idAdd, List<User> list) {
            this.idAdd = idAdd;
            this.list = list;
        }
    }
}