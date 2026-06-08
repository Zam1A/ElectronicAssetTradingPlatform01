package net.wms.util;

import net.wms.bean.Goods;
import net.wms.bean.Storage;
import net.wms.bean.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LocalDataStore {
    private static final Path DATA_DIR = Paths.get(System.getProperty("wms.data.dir", "data"));
    private static final Path USERS_FILE = DATA_DIR.resolve("users.tsv");
    private static final Path GOODS_FILE = DATA_DIR.resolve("goods.tsv");
    private static final Path STORAGE_FILE = DATA_DIR.resolve("storage.tsv");

    private LocalDataStore() {
    }

    public static synchronized void initialize() {
        try {
            Files.createDirectories(DATA_DIR);
            if (missingOrEmpty(USERS_FILE)) {
                writeRows(USERS_FILE, Arrays.asList(
                        row("1", "admin", "1", "1000", "2"),
                        row("2", "java", "1", "8600", "1"),
                        row("4", "d", "123", "300", "1"),
                        row("8", "xiaoming", "1", "10000", "1")));
            }
            if (missingOrEmpty(GOODS_FILE)) {
                writeRows(GOODS_FILE, Arrays.asList(
                        row("14", "apple", "fruit", "123", "java")));
            }
            if (missingOrEmpty(STORAGE_FILE)) {
                writeRows(STORAGE_FILE, Arrays.asList(
                        row("12", "blue", "aa", "400", "d"),
                        row("13", "343", "32325", "325", "java"),
                        row("14", "milk", "caw", "400", "java"),
                        row("16", "rtrt", "fefe", "200", "d")));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to initialize local data files", e);
        }
    }

    public static synchronized List<User> getUsers() {
        initialize();
        List<User> users = new ArrayList<User>();
        for (String[] values : readRows(USERS_FILE, 5)) {
            User user = new User();
            user.setId(toInt(values[0], 0));
            user.setusername(values[1]);
            user.setuserpwd(values[2]);
            user.setIntegrate(values[3]);
            user.setFlag(values[4]);
            users.add(user);
        }
        return users;
    }

    public static synchronized User findUser(String username) {
        for (User user : getUsers()) {
            if (safeEquals(user.getusername(), username)) {
                return copyUser(user);
            }
        }
        return null;
    }

    public static synchronized void addUser(User user) {
        List<User> users = getUsers();
        User saved = copyUser(user);
        saved.setId(nextUserId(users));
        if (isBlank(saved.getFlag())) {
            saved.setFlag("1");
        }
        if (isBlank(saved.getIntegrate())) {
            saved.setIntegrate("0");
        }
        users.add(saved);
        saveUsers(users);
    }

    public static synchronized boolean deleteUser(String username) {
        List<User> users = getUsers();
        boolean removed = false;
        for (int i = users.size() - 1; i >= 0; i--) {
            if (safeEquals(users.get(i).getusername(), username)) {
                users.remove(i);
                removed = true;
            }
        }
        if (removed) {
            saveUsers(users);
        }
        return removed;
    }

    public static synchronized boolean updatePassword(String username, String password) {
        List<User> users = getUsers();
        boolean updated = false;
        for (User user : users) {
            if (safeEquals(user.getusername(), username)) {
                user.setuserpwd(password);
                updated = true;
            }
        }
        if (updated) {
            saveUsers(users);
        }
        return updated;
    }

    public static synchronized boolean adjustIntegrate(String username, int delta) {
        List<User> users = getUsers();
        boolean updated = false;
        for (User user : users) {
            if (safeEquals(user.getusername(), username)) {
                int current = toInt(user.getIntegrate(), 0);
                user.setIntegrate(String.valueOf(current + delta));
                updated = true;
            }
        }
        if (updated) {
            saveUsers(users);
        }
        return updated;
    }

    public static synchronized List<Goods> getGoods() {
        initialize();
        List<Goods> goodsList = new ArrayList<Goods>();
        for (String[] values : readRows(GOODS_FILE, 5)) {
            Goods goods = new Goods();
            goods.setId(toInt(values[0], 0));
            goods.setGoodsname(values[1]);
            goods.setGoodsstyle(values[2]);
            goods.setGoodsnumber(toInt(values[3], 0));
            goods.setStorageID(values[4]);
            goodsList.add(goods);
        }
        return goodsList;
    }

    public static synchronized Goods findGoodsById(int id) {
        for (Goods goods : getGoods()) {
            if (goods.getId() == id) {
                return copyGoods(goods);
            }
        }
        return null;
    }

    public static synchronized List<Goods> getGoodsByOwner(String owner) {
        List<Goods> result = new ArrayList<Goods>();
        for (Goods goods : getGoods()) {
            if (safeEquals(goods.getStorageID(), owner)) {
                result.add(copyGoods(goods));
            }
        }
        return result;
    }

    public static synchronized void addGoods(Goods goods) {
        List<Goods> goodsList = getGoods();
        Goods saved = copyGoods(goods);
        saved.setId(nextGoodsId(goodsList));
        goodsList.add(saved);
        saveGoods(goodsList);
    }

    public static synchronized boolean deleteGoods(int id) {
        List<Goods> goodsList = getGoods();
        boolean removed = false;
        for (int i = goodsList.size() - 1; i >= 0; i--) {
            if (goodsList.get(i).getId() == id) {
                goodsList.remove(i);
                removed = true;
            }
        }
        if (removed) {
            saveGoods(goodsList);
        }
        return removed;
    }

    public static synchronized boolean updateGoods(int id, Goods updatedGoods) {
        List<Goods> goodsList = getGoods();
        boolean updated = false;
        for (Goods goods : goodsList) {
            if (goods.getId() == id) {
                goods.setGoodsname(updatedGoods.getGoodsname());
                goods.setGoodsstyle(updatedGoods.getGoodsstyle());
                goods.setGoodsnumber(updatedGoods.getGoodsnumber());
                if (!isBlank(updatedGoods.getStorageID())) {
                    goods.setStorageID(updatedGoods.getStorageID());
                }
                updated = true;
            }
        }
        if (updated) {
            saveGoods(goodsList);
        }
        return updated;
    }

    public static synchronized List<Storage> getStorage() {
        initialize();
        List<Storage> storageList = new ArrayList<Storage>();
        for (String[] values : readRows(STORAGE_FILE, 5)) {
            Storage storage = new Storage();
            storage.setId(toInt(values[0], 0));
            storage.setStoragename(values[1]);
            storage.setStoragestyle(values[2]);
            storage.setStorageID(values[3]);
            storage.setSeller(values[4]);
            storageList.add(storage);
        }
        return storageList;
    }

    public static synchronized List<Storage> getStorageBySeller(String seller) {
        List<Storage> result = new ArrayList<Storage>();
        for (Storage storage : getStorage()) {
            if (safeEquals(storage.getSeller(), seller)) {
                result.add(copyStorage(storage));
            }
        }
        return result;
    }

    public static synchronized void addStorage(Storage storage) {
        List<Storage> storageList = getStorage();
        Storage saved = copyStorage(storage);
        saved.setId(nextStorageId(storageList));
        storageList.add(saved);
        saveStorage(storageList);
    }

    public static synchronized boolean deleteStorage(int id) {
        List<Storage> storageList = getStorage();
        boolean removed = false;
        for (int i = storageList.size() - 1; i >= 0; i--) {
            if (storageList.get(i).getId() == id) {
                storageList.remove(i);
                removed = true;
            }
        }
        if (removed) {
            saveStorage(storageList);
        }
        return removed;
    }

    public static synchronized boolean updateStorage(int id, Storage updatedStorage) {
        List<Storage> storageList = getStorage();
        boolean updated = false;
        for (Storage storage : storageList) {
            if (storage.getId() == id) {
                storage.setStoragename(updatedStorage.getStoragename());
                storage.setStoragestyle(updatedStorage.getStoragestyle());
                storage.setStorageID(updatedStorage.getStorageID());
                if (!isBlank(updatedStorage.getSeller())) {
                    storage.setSeller(updatedStorage.getSeller());
                }
                updated = true;
            }
        }
        if (updated) {
            saveStorage(storageList);
        }
        return updated;
    }

    private static void saveUsers(List<User> users) {
        List<String> rows = new ArrayList<String>();
        for (User user : users) {
            rows.add(row(String.valueOf(user.getId()), user.getusername(), user.getuserpwd(),
                    user.getIntegrate(), user.getFlag()));
        }
        writeRows(USERS_FILE, rows);
    }

    private static void saveGoods(List<Goods> goodsList) {
        List<String> rows = new ArrayList<String>();
        for (Goods goods : goodsList) {
            rows.add(row(String.valueOf(goods.getId()), goods.getGoodsname(), goods.getGoodsstyle(),
                    String.valueOf(goods.getGoodsnumber()), goods.getStorageID()));
        }
        writeRows(GOODS_FILE, rows);
    }

    private static void saveStorage(List<Storage> storageList) {
        List<String> rows = new ArrayList<String>();
        for (Storage storage : storageList) {
            rows.add(row(String.valueOf(storage.getId()), storage.getStoragename(), storage.getStoragestyle(),
                    storage.getStorageID(), storage.getSeller()));
        }
        writeRows(STORAGE_FILE, rows);
    }

    private static User copyUser(User source) {
        User user = new User();
        user.setId(source.getId());
        user.setusername(source.getusername());
        user.setuserpwd(source.getuserpwd());
        user.setIntegrate(source.getIntegrate());
        user.setFlag(source.getFlag());
        return user;
    }

    private static Goods copyGoods(Goods source) {
        Goods goods = new Goods();
        goods.setId(source.getId());
        goods.setGoodsname(source.getGoodsname());
        goods.setGoodsstyle(source.getGoodsstyle());
        goods.setGoodsnumber(source.getGoodsnumber());
        goods.setStorageID(source.getStorageID());
        return goods;
    }

    private static Storage copyStorage(Storage source) {
        Storage storage = new Storage();
        storage.setId(source.getId());
        storage.setStoragename(source.getStoragename());
        storage.setStoragestyle(source.getStoragestyle());
        storage.setStorageID(source.getStorageID());
        storage.setSeller(source.getSeller());
        return storage;
    }

    private static int nextUserId(List<User> users) {
        int max = 0;
        for (User user : users) {
            max = Math.max(max, user.getId());
        }
        return max + 1;
    }

    private static int nextGoodsId(List<Goods> goodsList) {
        int max = 0;
        for (Goods goods : goodsList) {
            max = Math.max(max, goods.getId());
        }
        return max + 1;
    }

    private static int nextStorageId(List<Storage> storageList) {
        int max = 0;
        for (Storage storage : storageList) {
            max = Math.max(max, storage.getId());
        }
        return max + 1;
    }

    private static List<String[]> readRows(Path file, int columns) {
        List<String[]> rows = new ArrayList<String[]>();
        try {
            if (!Files.exists(file)) {
                return rows;
            }
            BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0) {
                        continue;
                    }
                    String[] parts = split(line, columns);
                    rows.add(parts);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read local data file: " + file, e);
        }
        return rows;
    }

    private static void writeRows(Path file, List<String> rows) {
        try {
            Files.createDirectories(file.getParent());
            BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
            try {
                for (String row : rows) {
                    writer.write(row);
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to write local data file: " + file, e);
        }
    }

    private static boolean missingOrEmpty(Path file) throws IOException {
        return !Files.exists(file) || Files.size(file) == 0;
    }

    private static String row(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append('\t');
            }
            builder.append(escape(values[i]));
        }
        return builder.toString();
    }

    private static String[] split(String line, int columns) {
        String[] raw = line.split("\t", -1);
        String[] values = new String[columns];
        for (int i = 0; i < columns; i++) {
            values[i] = i < raw.length ? unescape(raw[i]) : "";
        }
        return values;
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static String unescape(String value) {
        StringBuilder builder = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (escaped) {
                if (ch == 't') {
                    builder.append('\t');
                } else if (ch == 'r') {
                    builder.append('\r');
                } else if (ch == 'n') {
                    builder.append('\n');
                } else {
                    builder.append(ch);
                }
                escaped = false;
            } else if (ch == '\\') {
                escaped = true;
            } else {
                builder.append(ch);
            }
        }
        if (escaped) {
            builder.append('\\');
        }
        return builder.toString();
    }

    private static boolean safeEquals(String first, String second) {
        return first == null ? second == null : first.equals(second);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private static int toInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }
}
