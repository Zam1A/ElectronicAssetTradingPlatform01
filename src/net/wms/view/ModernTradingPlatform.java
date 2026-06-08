package net.wms.view;

import net.wms.bean.Goods;
import net.wms.bean.Storage;
import net.wms.bean.User;
import net.wms.util.LocalDataStore;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class ModernTradingPlatform extends JFrame {
    private static final String ADMIN_FLAG = "2";
    private static final String EMPLOYEE_FLAG = "1";

    private static final Color INK = new Color(24, 33, 46);
    private static final Color MUTED = new Color(92, 104, 120);
    private static final Color PAGE = new Color(246, 248, 251);
    private static final Color SURFACE = Color.WHITE;
    private static final Color LINE = new Color(222, 228, 236);
    private static final Color SIDEBAR = new Color(31, 42, 56);
    private static final Color SIDEBAR_ACTIVE = new Color(50, 67, 87);
    private static final Color ACCENT = new Color(41, 128, 110);
    private static final Color ACCENT_DARK = new Color(30, 96, 84);
    private static final Font UI = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font UI_BOLD = new Font("Segoe UI", Font.BOLD, 14);

    private final CardLayout rootCards = new CardLayout();
    private final JPanel root = new JPanel(rootCards);
    private final CardLayout contentCards = new CardLayout();
    private final JPanel content = new JPanel(contentCards);

    private User currentUser;
    private JPanel appPanel;
    private JPanel sidebarPanel;
    private JLabel headerTitle;
    private JLabel userSummary;
    private JLabel statUsers;
    private JLabel statGoods;
    private JLabel statOrders;
    private JLabel statBalance;
    private JLabel accountName;
    private JLabel accountRole;
    private JLabel accountBalance;

    private JTable usersTable;
    private JTable adminGoodsTable;
    private JTable adminOrdersTable;
    private JTable marketplaceTable;
    private JTable myGoodsTable;
    private JTable myOrdersTable;

    private DefaultTableModel usersModel;
    private DefaultTableModel adminGoodsModel;
    private DefaultTableModel adminOrdersModel;
    private DefaultTableModel marketplaceModel;
    private DefaultTableModel myGoodsModel;
    private DefaultTableModel myOrdersModel;

    public static void main(String[] args) {
        installLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocalDataStore.initialize();
                ModernTradingPlatform app = new ModernTradingPlatform();
                app.setVisible(true);
            }
        });
    }

    public ModernTradingPlatform() {
        setTitle("Electronic Asset Trading Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setSize(preferredWindowSize());
        setLocationRelativeTo(null);
        setContentPane(root);
        root.add(createLoginPanel(), "login");
        rootCards.show(root, "login");
    }

    private static void installLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("defaultFont", UI);
        } catch (Exception ignored) {
        }
    }

    private Dimension preferredWindowSize() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(Math.max(1100, (int) (screen.width * 0.82)), Math.max(720, (int) (screen.height * 0.82)));
    }

    private JPanel createLoginPanel() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setBackground(PAGE);

        JPanel shell = new JPanel(new GridLayout(1, 2, 0, 0));
        shell.setPreferredSize(new Dimension(980, 560));
        shell.setBorder(new LineBorder(LINE));
        shell.add(new BrandPanel());
        shell.add(createLoginForm());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        page.add(shell, gbc);
        return page;
    }

    private JPanel createLoginForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(SURFACE);
        form.setBorder(new EmptyBorder(56, 64, 56, 64));

        JLabel title = label("Sign in", 30, Font.BOLD, INK);
        JLabel subtitle = label("Access your trading workspace", 14, Font.PLAIN, MUTED);
        JTextField username = field();
        JPasswordField password = passwordField();
        JComboBox<String> role = new JComboBox<String>(new String[] {"admin", "employee"});
        styleCombo(role);
        JButton signIn = primaryButton("Sign in");

        addFormRow(form, title, 0, 0, 1, 0, 0);
        addFormRow(form, subtitle, 0, 1, 1, 0, 12);
        addFormRow(form, smallLabel("Username"), 0, 2, 1, 26, 6);
        addFormRow(form, username, 0, 3, 1, 0, 16);
        addFormRow(form, smallLabel("Password"), 0, 4, 1, 0, 6);
        addFormRow(form, password, 0, 5, 1, 0, 16);
        addFormRow(form, smallLabel("Account type"), 0, 6, 1, 0, 6);
        addFormRow(form, role, 0, 7, 1, 0, 24);
        addFormRow(form, signIn, 0, 8, 1, 0, 20);

        JLabel hint = label("Default: admin / 1 or java / 1", 13, Font.PLAIN, MUTED);
        addFormRow(form, hint, 0, 9, 1, 4, 0);

        signIn.addActionListener(e -> login(username.getText(), new String(password.getPassword()), role.getSelectedItem().toString()));
        getRootPane().setDefaultButton(signIn);
        return form;
    }

    private void login(String username, String password, String role) {
        User user = LocalDataStore.findUser(username.trim());
        String expectedFlag = "admin".equals(role) ? ADMIN_FLAG : EMPLOYEE_FLAG;
        if (user == null || !password.equals(user.getuserpwd()) || !expectedFlag.equals(user.getFlag())) {
            JOptionPane.showMessageDialog(this, "Login failed. Check username, password, and account type.", "Login failed",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        currentUser = user;
        buildApplicationShell();
        rootCards.show(root, "app");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void buildApplicationShell() {
        if (appPanel != null) {
            root.remove(appPanel);
        }
        appPanel = new JPanel(new BorderLayout());
        appPanel.setBackground(PAGE);
        appPanel.add(createHeader(), BorderLayout.NORTH);
        appPanel.add(createSidebar(), BorderLayout.WEST);
        content.removeAll();
        content.setBackground(PAGE);
        content.setBorder(new EmptyBorder(22, 22, 22, 22));
        appPanel.add(content, BorderLayout.CENTER);
        root.add(appPanel, "app");

        if (isAdmin()) {
            content.add(createAdminOverviewPanel(), "overview");
            content.add(createUsersPanel(), "users");
            content.add(createAdminGoodsPanel(), "goods");
            content.add(createAdminOrdersPanel(), "orders");
            content.add(createAccountPanel(), "account");
            selectRoute("overview", "Overview");
        } else {
            content.add(createEmployeeOverviewPanel(), "overview");
            content.add(createMarketplacePanel(), "marketplace");
            content.add(createMyListingsPanel(), "listings");
            content.add(createMyOrdersPanel(), "orders");
            content.add(createAccountPanel(), "account");
            selectRoute("overview", "Overview");
        }
        refreshAll();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SURFACE);
        header.setBorder(new CompoundBorder(new MatteLineBorder(0, 0, 1, 0, LINE), new EmptyBorder(16, 24, 16, 24)));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        headerTitle = label("Overview", 22, Font.BOLD, INK);
        JLabel subtitle = label("Electronic Asset Trading Platform", 13, Font.PLAIN, MUTED);
        text.add(headerTitle);
        text.add(Box.createVerticalStrut(3));
        text.add(subtitle);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actions.setOpaque(false);
        userSummary = label("", 13, Font.PLAIN, MUTED);
        JButton logout = ghostButton("Logout");
        logout.addActionListener(e -> logout());
        actions.add(userSummary);
        actions.add(logout);

        header.add(text, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);
        return header;
    }

    private JPanel createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBackground(SIDEBAR);
        sidebarPanel.setBorder(new EmptyBorder(22, 16, 22, 16));

        JLabel brand = label("Asset Trade", 24, Font.BOLD, Color.WHITE);
        JLabel role = label(isAdmin() ? "Administrator Console" : "Employee Workspace", 13, Font.PLAIN, new Color(183, 193, 206));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(brand);
        sidebarPanel.add(Box.createVerticalStrut(4));
        sidebarPanel.add(role);
        sidebarPanel.add(Box.createVerticalStrut(28));

        addNav(sidebarPanel, "Overview", "overview");
        if (isAdmin()) {
            addNav(sidebarPanel, "Users", "users");
            addNav(sidebarPanel, "Goods", "goods");
            addNav(sidebarPanel, "Orders", "orders");
        } else {
            addNav(sidebarPanel, "Marketplace", "marketplace");
            addNav(sidebarPanel, "My Listings", "listings");
            addNav(sidebarPanel, "Orders", "orders");
        }
        addNav(sidebarPanel, "Account", "account");
        sidebarPanel.add(Box.createVerticalGlue());
        return sidebarPanel;
    }

    private void addNav(JPanel sidebar, final String title, final String route) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(UI_BOLD);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setForeground(new Color(224, 231, 239));
        button.setBackground(SIDEBAR);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> {
            selectRoute(route, title);
        });
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(8));
    }

    private void selectRoute(String route, String title) {
        contentCards.show(content, route);
        if (headerTitle != null) {
            headerTitle.setText(title);
        }
        refreshAll();
        markSelectedNav(title);
    }

    private void markSelectedNav(String title) {
        if (sidebarPanel == null) {
            return;
        }
        Component[] components = sidebarPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JButton) {
                JButton button = (JButton) components[i];
                boolean active = title.equals(button.getText());
                button.setBackground(active ? SIDEBAR_ACTIVE : SIDEBAR);
                button.setForeground(active ? Color.WHITE : new Color(224, 231, 239));
            }
        }
    }

    private JPanel createAdminOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setOpaque(false);
        JPanel stats = new JPanel(new GridLayout(1, 4, 18, 18));
        stats.setOpaque(false);
        statUsers = statValue();
        statGoods = statValue();
        statOrders = statValue();
        statBalance = statValue();
        stats.add(statCard("Users", statUsers, "Active system accounts"));
        stats.add(statCard("Goods", statGoods, "Assets currently listed"));
        stats.add(statCard("Orders", statOrders, "Completed purchases"));
        stats.add(statCard("Admin", statBalance, "Current signed-in user"));
        panel.add(stats, BorderLayout.NORTH);
        panel.add(infoPanel("Admin workflow",
                "Manage accounts, review listed assets, audit completed orders, and update your own password from the Account page."),
                BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEmployeeOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setOpaque(false);
        JPanel stats = new JPanel(new GridLayout(1, 4, 18, 18));
        stats.setOpaque(false);
        statGoods = statValue();
        statOrders = statValue();
        statBalance = statValue();
        statUsers = statValue();
        stats.add(statCard("Marketplace", statGoods, "Available assets"));
        stats.add(statCard("My listings", statUsers, "Assets you are selling"));
        stats.add(statCard("My orders", statOrders, "Purchased assets"));
        stats.add(statCard("Balance", statBalance, "Available credits"));
        panel.add(stats, BorderLayout.NORTH);
        panel.add(infoPanel("Employee workflow",
                "Buy assets from the marketplace, list your own assets, manage active listings, and track purchases from Orders."),
                BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUsersPanel() {
        usersModel = new NonEditableTableModel(new String[] {"ID", "Username", "Role", "Balance"});
        usersTable = table(usersModel);
        JPanel toolbar = toolbar();
        JButton add = primaryButton("Add user");
        JButton password = secondaryButton("Change password");
        JButton delete = dangerButton("Delete");
        JTextField search = searchField("Search users");
        installSearch(search, usersTable);
        add.addActionListener(e -> addUser());
        password.addActionListener(e -> changeSelectedUserPassword());
        delete.addActionListener(e -> deleteSelectedUser());
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(add);
        toolbar.add(password);
        toolbar.add(delete);
        return tablePanel("System users", "Create employee/admin accounts and manage access.", toolbar, usersTable);
    }

    private JPanel createAdminGoodsPanel() {
        adminGoodsModel = new NonEditableTableModel(new String[] {"ID", "Name", "Type", "Price", "Seller"});
        adminGoodsTable = table(adminGoodsModel);
        JPanel toolbar = toolbar();
        JButton delete = dangerButton("Remove listing");
        JTextField search = searchField("Search goods");
        installSearch(search, adminGoodsTable);
        delete.addActionListener(e -> deleteGoodsFrom(adminGoodsTable));
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(delete);
        return tablePanel("Goods", "Review or remove any listed asset.", toolbar, adminGoodsTable);
    }

    private JPanel createAdminOrdersPanel() {
        adminOrdersModel = new NonEditableTableModel(new String[] {"ID", "Asset", "Type", "Price", "Buyer"});
        adminOrdersTable = table(adminOrdersModel);
        JPanel toolbar = toolbar();
        JButton delete = dangerButton("Delete order");
        JTextField search = searchField("Search orders");
        installSearch(search, adminOrdersTable);
        delete.addActionListener(e -> deleteSelectedOrder(adminOrdersTable));
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(delete);
        return tablePanel("Orders", "Audit completed marketplace purchases.", toolbar, adminOrdersTable);
    }

    private JPanel createMarketplacePanel() {
        marketplaceModel = new NonEditableTableModel(new String[] {"ID", "Name", "Type", "Price", "Seller"});
        marketplaceTable = table(marketplaceModel);
        JPanel toolbar = toolbar();
        JButton buy = primaryButton("Buy selected");
        JTextField search = searchField("Search marketplace");
        installSearch(search, marketplaceTable);
        buy.addActionListener(e -> buySelectedAsset());
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(buy);
        return tablePanel("Marketplace", "Browse assets and buy with your balance.", toolbar, marketplaceTable);
    }

    private JPanel createMyListingsPanel() {
        myGoodsModel = new NonEditableTableModel(new String[] {"ID", "Name", "Type", "Price", "Seller"});
        myGoodsTable = table(myGoodsModel);
        JPanel toolbar = toolbar();
        JButton add = primaryButton("Add listing");
        JButton edit = secondaryButton("Edit");
        JButton remove = dangerButton("Remove");
        JTextField search = searchField("Search listings");
        installSearch(search, myGoodsTable);
        add.addActionListener(e -> addListing());
        edit.addActionListener(e -> editListing());
        remove.addActionListener(e -> deleteGoodsFrom(myGoodsTable));
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(add);
        toolbar.add(edit);
        toolbar.add(remove);
        return tablePanel("My listings", "Create and maintain the assets you are selling.", toolbar, myGoodsTable);
    }

    private JPanel createMyOrdersPanel() {
        myOrdersModel = new NonEditableTableModel(new String[] {"ID", "Asset", "Type", "Price", "Buyer"});
        myOrdersTable = table(myOrdersModel);
        JPanel toolbar = toolbar();
        JTextField search = searchField("Search orders");
        installSearch(search, myOrdersTable);
        toolbar.add(search);
        toolbar.add(Box.createHorizontalGlue());
        return tablePanel("Orders", "Your completed asset purchases.", toolbar, myOrdersTable);
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setOpaque(false);

        JPanel details = card();
        details.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        accountName = label("", 22, Font.BOLD, INK);
        accountRole = label("", 14, Font.PLAIN, MUTED);
        accountBalance = label("", 14, Font.PLAIN, MUTED);
        JButton changePassword = primaryButton("Change password");
        changePassword.addActionListener(e -> changeCurrentPassword());

        gbc.gridx = 0;
        gbc.gridy = 0;
        details.add(accountName, gbc);
        gbc.gridy = 1;
        details.add(accountRole, gbc);
        gbc.gridy = 2;
        details.add(accountBalance, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(24, 10, 10, 10);
        details.add(changePassword, gbc);

        panel.add(details, BorderLayout.NORTH);
        panel.add(infoPanel("Data location",
                "This desktop app stores local data in the project data folder. Delete that folder to reset to the default demo accounts."),
                BorderLayout.CENTER);
        return panel;
    }

    private void refreshAll() {
        if (currentUser != null) {
            currentUser = LocalDataStore.findUser(currentUser.getusername());
        }
        refreshHeader();
        refreshStats();
        refreshUsers();
        refreshGoods();
        refreshOrders();
        refreshAccount();
    }

    private void refreshHeader() {
        if (currentUser == null || userSummary == null) {
            return;
        }
        userSummary.setText(currentUser.getusername() + " | " + roleName(currentUser.getFlag()));
    }

    private void refreshStats() {
        if (statGoods == null) {
            return;
        }
        if (isAdmin()) {
            statUsers.setText(String.valueOf(LocalDataStore.getUsers().size()));
            statGoods.setText(String.valueOf(LocalDataStore.getGoods().size()));
            statOrders.setText(String.valueOf(LocalDataStore.getStorage().size()));
            statBalance.setText(currentUser == null ? "-" : currentUser.getusername());
        } else {
            statGoods.setText(String.valueOf(LocalDataStore.getGoods().size()));
            statUsers.setText(String.valueOf(LocalDataStore.getGoodsByOwner(currentUser.getusername()).size()));
            statOrders.setText(String.valueOf(LocalDataStore.getStorageBySeller(currentUser.getusername()).size()));
            statBalance.setText(balanceText(currentUser));
        }
    }

    private void refreshUsers() {
        if (usersModel == null) {
            return;
        }
        usersModel.setRowCount(0);
        for (User user : LocalDataStore.getUsers()) {
            usersModel.addRow(new Object[] {user.getId(), user.getusername(), roleName(user.getFlag()), balanceText(user)});
        }
    }

    private void refreshGoods() {
        if (adminGoodsModel != null) {
            loadGoods(adminGoodsModel, LocalDataStore.getGoods());
        }
        if (marketplaceModel != null) {
            loadGoods(marketplaceModel, LocalDataStore.getGoods());
        }
        if (myGoodsModel != null && currentUser != null) {
            loadGoods(myGoodsModel, LocalDataStore.getGoodsByOwner(currentUser.getusername()));
        }
    }

    private void loadGoods(DefaultTableModel model, List<Goods> goodsList) {
        model.setRowCount(0);
        for (Goods goods : goodsList) {
            model.addRow(new Object[] {goods.getId(), goods.getGoodsname(), goods.getGoodsstyle(), goods.getGoodsnumber(),
                    goods.getStorageID()});
        }
    }

    private void refreshOrders() {
        if (adminOrdersModel != null) {
            loadOrders(adminOrdersModel, LocalDataStore.getStorage());
        }
        if (myOrdersModel != null && currentUser != null) {
            loadOrders(myOrdersModel, LocalDataStore.getStorageBySeller(currentUser.getusername()));
        }
    }

    private void loadOrders(DefaultTableModel model, List<Storage> orders) {
        model.setRowCount(0);
        for (Storage order : orders) {
            model.addRow(new Object[] {order.getId(), order.getStoragename(), order.getStoragestyle(), order.getStorageID(),
                    order.getSeller()});
        }
    }

    private void refreshAccount() {
        if (currentUser == null || accountName == null) {
            return;
        }
        accountName.setText(currentUser.getusername());
        accountRole.setText("Role: " + roleName(currentUser.getFlag()));
        accountBalance.setText("Balance: " + balanceText(currentUser));
    }

    private void addUser() {
        JTextField username = field();
        JPasswordField password = passwordField();
        JComboBox<String> role = new JComboBox<String>(new String[] {"employee", "admin"});
        styleCombo(role);
        JSpinner balance = new JSpinner(new SpinnerNumberModel(1000, 0, 1000000, 100));

        JPanel form = dialogForm();
        addDialogRow(form, "Username", username, 0);
        addDialogRow(form, "Password", password, 1);
        addDialogRow(form, "Role", role, 2);
        addDialogRow(form, "Balance", balance, 3);

        if (confirm(form, "Add user")) {
            String name = username.getText().trim();
            if (name.length() == 0 || password.getPassword().length == 0) {
                warn("Username and password are required.");
                return;
            }
            if (LocalDataStore.findUser(name) != null) {
                warn("That username already exists.");
                return;
            }
            User user = new User();
            user.setusername(name);
            user.setuserpwd(new String(password.getPassword()));
            user.setFlag("admin".equals(role.getSelectedItem()) ? ADMIN_FLAG : EMPLOYEE_FLAG);
            user.setIntegrate(String.valueOf(balance.getValue()));
            LocalDataStore.addUser(user);
            refreshAll();
        }
    }

    private void changeSelectedUserPassword() {
        String username = selectedString(usersTable, 1);
        if (username == null) {
            warn("Select a user first.");
            return;
        }
        JPasswordField password = passwordField();
        JPanel form = dialogForm();
        addDialogRow(form, "New password", password, 0);
        if (confirm(form, "Change password")) {
            if (password.getPassword().length == 0) {
                warn("Password cannot be empty.");
                return;
            }
            LocalDataStore.updatePassword(username, new String(password.getPassword()));
            refreshAll();
        }
    }

    private void deleteSelectedUser() {
        String username = selectedString(usersTable, 1);
        if (username == null) {
            warn("Select a user first.");
            return;
        }
        if (currentUser != null && username.equals(currentUser.getusername())) {
            warn("You cannot delete the account you are currently using.");
            return;
        }
        if (ask("Delete user " + username + "?")) {
            LocalDataStore.deleteUser(username);
            refreshAll();
        }
    }

    private void buySelectedAsset() {
        Integer id = selectedInteger(marketplaceTable, 0);
        if (id == null) {
            warn("Select an asset first.");
            return;
        }
        Goods goods = LocalDataStore.findGoodsById(id.intValue());
        if (goods == null) {
            warn("This asset is no longer available.");
            refreshAll();
            return;
        }
        if (goods.getStorageID().equals(currentUser.getusername())) {
            warn("You cannot buy your own listing.");
            return;
        }
        int price = goods.getGoodsnumber();
        int balance = parseInt(currentUser.getIntegrate(), 0);
        if (balance < price) {
            warn("Your balance is not enough for this asset.");
            return;
        }
        if (!ask("Buy " + goods.getGoodsname() + " for " + price + " credits?")) {
            return;
        }
        Storage order = new Storage();
        order.setStoragename(goods.getGoodsname());
        order.setStoragestyle(goods.getGoodsstyle());
        order.setStorageID(String.valueOf(price));
        order.setSeller(currentUser.getusername());
        LocalDataStore.addStorage(order);
        LocalDataStore.adjustIntegrate(currentUser.getusername(), -price);
        LocalDataStore.deleteGoods(goods.getId());
        refreshAll();
    }

    private void addListing() {
        ListingInput input = listingDialog("Add listing", null);
        if (input == null) {
            return;
        }
        Goods goods = new Goods();
        goods.setGoodsname(input.name);
        goods.setGoodsstyle(input.type);
        goods.setGoodsnumber(input.price);
        goods.setStorageID(currentUser.getusername());
        LocalDataStore.addGoods(goods);
        refreshAll();
    }

    private void editListing() {
        Integer id = selectedInteger(myGoodsTable, 0);
        if (id == null) {
            warn("Select a listing first.");
            return;
        }
        Goods goods = LocalDataStore.findGoodsById(id.intValue());
        if (goods == null || !currentUser.getusername().equals(goods.getStorageID())) {
            warn("This listing is no longer available.");
            refreshAll();
            return;
        }
        ListingInput input = listingDialog("Edit listing", goods);
        if (input == null) {
            return;
        }
        goods.setGoodsname(input.name);
        goods.setGoodsstyle(input.type);
        goods.setGoodsnumber(input.price);
        goods.setStorageID(currentUser.getusername());
        LocalDataStore.updateGoods(goods.getId(), goods);
        refreshAll();
    }

    private void deleteGoodsFrom(JTable table) {
        Integer id = selectedInteger(table, 0);
        if (id == null) {
            warn("Select a listing first.");
            return;
        }
        if (ask("Remove selected listing?")) {
            LocalDataStore.deleteGoods(id.intValue());
            refreshAll();
        }
    }

    private void deleteSelectedOrder(JTable table) {
        Integer id = selectedInteger(table, 0);
        if (id == null) {
            warn("Select an order first.");
            return;
        }
        if (ask("Delete selected order?")) {
            LocalDataStore.deleteStorage(id.intValue());
            refreshAll();
        }
    }

    private void changeCurrentPassword() {
        JPasswordField first = passwordField();
        JPasswordField second = passwordField();
        JPanel form = dialogForm();
        addDialogRow(form, "New password", first, 0);
        addDialogRow(form, "Confirm", second, 1);
        if (confirm(form, "Change password")) {
            String a = new String(first.getPassword());
            String b = new String(second.getPassword());
            if (a.length() == 0) {
                warn("Password cannot be empty.");
                return;
            }
            if (!a.equals(b)) {
                warn("Passwords do not match.");
                return;
            }
            LocalDataStore.updatePassword(currentUser.getusername(), a);
            refreshAll();
        }
    }

    private ListingInput listingDialog(String title, Goods existing) {
        JTextField name = field();
        JTextField type = field();
        JSpinner price = new JSpinner(new SpinnerNumberModel(100, 1, 1000000, 10));
        if (existing != null) {
            name.setText(existing.getGoodsname());
            type.setText(existing.getGoodsstyle());
            price.setValue(existing.getGoodsnumber());
        }
        JPanel form = dialogForm();
        addDialogRow(form, "Name", name, 0);
        addDialogRow(form, "Type", type, 1);
        addDialogRow(form, "Price", price, 2);
        if (!confirm(form, title)) {
            return null;
        }
        if (name.getText().trim().length() == 0 || type.getText().trim().length() == 0) {
            warn("Name and type are required.");
            return null;
        }
        return new ListingInput(name.getText().trim(), type.getText().trim(), ((Number) price.getValue()).intValue());
    }

    private void logout() {
        currentUser = null;
        rootCards.show(root, "login");
        setExtendedState(JFrame.NORMAL);
        setSize(preferredWindowSize());
        setLocationRelativeTo(null);
    }

    private JPanel tablePanel(String title, String subtitle, JPanel toolbar, JTable table) {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(label(title, 24, Font.BOLD, INK));
        text.add(Box.createVerticalStrut(4));
        text.add(label(subtitle, 13, Font.PLAIN, MUTED));
        top.add(text, BorderLayout.NORTH);
        top.add(toolbar, BorderLayout.SOUTH);

        JPanel tableCard = card();
        tableCard.setLayout(new BorderLayout());
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        panel.add(top, BorderLayout.NORTH);
        panel.add(tableCard, BorderLayout.CENTER);
        return panel;
    }

    private JPanel toolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setOpaque(false);
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        return toolbar;
    }

    private JTable table(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(UI);
        table.setRowHeight(38);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(233, 237, 243));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        JTableHeader header = table.getTableHeader();
        header.setFont(UI_BOLD);
        header.setBackground(new Color(239, 243, 248));
        header.setForeground(INK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        return table;
    }

    private JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(new CompoundBorder(new LineBorder(LINE), new EmptyBorder(18, 18, 18, 18)));
        return panel;
    }

    private JPanel statCard(String title, JLabel value, String subtitle) {
        JPanel card = card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel name = label(title, 13, Font.BOLD, MUTED);
        JLabel sub = label(subtitle, 12, Font.PLAIN, MUTED);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        value.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(name);
        card.add(Box.createVerticalStrut(8));
        card.add(value);
        card.add(Box.createVerticalStrut(8));
        card.add(sub);
        return card;
    }

    private JPanel infoPanel(String title, String text) {
        JPanel panel = card();
        panel.setLayout(new BorderLayout(0, 8));
        panel.add(label(title, 20, Font.BOLD, INK), BorderLayout.NORTH);
        JLabel body = label("<html><body style='width: 620px'>" + text + "</body></html>", 14, Font.PLAIN, MUTED);
        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private JLabel statValue() {
        return label("-", 34, Font.BOLD, INK);
    }

    private JButton primaryButton(String text) {
        return button(text, ACCENT, Color.WHITE, ACCENT_DARK);
    }

    private JButton secondaryButton(String text) {
        return button(text, new Color(238, 243, 248), INK, new Color(224, 232, 240));
    }

    private JButton ghostButton(String text) {
        return button(text, SURFACE, INK, new Color(238, 243, 248));
    }

    private JButton dangerButton(String text) {
        return button(text, new Color(184, 65, 70), Color.WHITE, new Color(150, 47, 54));
    }

    private JButton button(String text, Color background, Color foreground, Color border) {
        JButton button = new JButton(text);
        button.setFont(UI_BOLD);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(new CompoundBorder(new LineBorder(border), new EmptyBorder(9, 16, 9, 16)));
        return button;
    }

    private JTextField field() {
        JTextField field = new JTextField();
        field.setFont(UI);
        field.setPreferredSize(new Dimension(260, 42));
        field.setBorder(new CompoundBorder(new LineBorder(LINE), new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    private JPasswordField passwordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(UI);
        field.setPreferredSize(new Dimension(260, 42));
        field.setBorder(new CompoundBorder(new LineBorder(LINE), new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    private JTextField searchField(String text) {
        JTextField field = field();
        field.setMaximumSize(new Dimension(280, 42));
        field.setToolTipText(text);
        return field;
    }

    private void installSearch(JTextField field, JTable table) {
        TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
        field.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            protected void update() {
                String query = field.getText().trim();
                sorter.setRowFilter(query.length() == 0 ? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(query)));
            }
        });
    }

    private JLabel label(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    private JLabel smallLabel(String text) {
        return label(text, 13, Font.BOLD, MUTED);
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setModel((DefaultComboBoxModel<String>) combo.getModel());
        combo.setFont(UI);
        combo.setPreferredSize(new Dimension(260, 42));
    }

    private void addFormRow(JPanel panel, JComponent component, int x, int y, int width, int top, int bottom) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(top, 0, bottom, 0);
        panel.add(component, gbc);
    }

    private JPanel dialogForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
        return panel;
    }

    private void addDialogRow(JPanel panel, String label, JComponent input, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 12);
        panel.add(smallLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(input, gbc);
    }

    private boolean confirm(JComponent component, String title) {
        return JOptionPane.showConfirmDialog(this, component, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
                == JOptionPane.OK_OPTION;
    }

    private boolean ask(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION;
    }

    private void warn(String message) {
        JOptionPane.showMessageDialog(this, message, "Action needed", JOptionPane.WARNING_MESSAGE);
    }

    private Integer selectedInteger(JTable table, int column) {
        String value = selectedString(table, column);
        return value == null ? null : Integer.valueOf(value);
    }

    private String selectedString(JTable table, int column) {
        if (table == null || table.getSelectedRow() < 0) {
            return null;
        }
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        Object value = table.getModel().getValueAt(modelRow, column);
        return value == null ? null : value.toString();
    }

    private boolean isAdmin() {
        return currentUser != null && ADMIN_FLAG.equals(currentUser.getFlag());
    }

    private String roleName(String flag) {
        return ADMIN_FLAG.equals(flag) ? "admin" : "employee";
    }

    private String balanceText(User user) {
        return user == null ? "0" : String.valueOf(parseInt(user.getIntegrate(), 0));
    }

    private int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    private static class NonEditableTableModel extends DefaultTableModel {
        NonEditableTableModel(String[] columns) {
            super(columns, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private static class ListingInput {
        final String name;
        final String type;
        final int price;

        ListingInput(String name, String type, int price) {
            this.name = name;
            this.type = type;
            this.price = price;
        }
    }

    private abstract static class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        protected abstract void update();

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }
    }

    private static class MatteLineBorder extends javax.swing.border.MatteBorder {
        MatteLineBorder(int top, int left, int bottom, int right, Color color) {
            super(top, left, bottom, right, color);
        }
    }

    private class BrandPanel extends JPanel {
        BrandPanel() {
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(56, 56, 56, 56));
            setBackground(SIDEBAR);
            JPanel text = new JPanel();
            text.setOpaque(false);
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
            JLabel name = label("Electronic Asset", 34, Font.BOLD, Color.WHITE);
            JLabel name2 = label("Trading Platform", 34, Font.BOLD, Color.WHITE);
            JLabel body = label("<html><body style='width: 320px'>A modern desktop workspace for accounts, listings, marketplace purchases, and order tracking.</body></html>",
                    15, Font.PLAIN, new Color(215, 224, 235));
            text.add(name);
            text.add(name2);
            text.add(Box.createVerticalStrut(18));
            text.add(body);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            add(text, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setPaint(new GradientPaint(0, 0, SIDEBAR, getWidth(), getHeight(), new Color(38, 82, 92)));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}
