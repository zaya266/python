package eis;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// ===================== PRESENTATION LAYER (Swing GUI) =====================
public class Main extends JFrame {

    static final Color BG = new Color(0xF3F5FA);
    static final Font UI = new Font("SansSerif", Font.PLAIN, 14);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

    Main() {
        super("EIS Smart System – Кейс 1-5");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabs.addTab("1 · Ирц", new AttendanceTab());
        tabs.addTab("2 · Номын сан", new LibraryTab());
        tabs.addTab("3 · Үнэлгээ", new EvaluationTab());
        tabs.addTab("4 · Дотуур байр", new DormTab());
        tabs.addTab("5 · Эмнэлэг", new CareTab());
        add(tabs);
        setSize(1020, 660);
        setMinimumSize(new Dimension(900, 580));
        setLocationRelativeTo(null);
    }

    // ---------- Helpers ----------
    static JTextField text(String def) {
        JTextField f = new JTextField(def, 14);
        f.setBorder(new CompoundBorder(new LineBorder(new Color(0xCBD2E0)), new EmptyBorder(6, 8, 6, 8)));
        return f;
    }

    static String req(JTextField f, String fieldName) {
        String v = f.getText().trim();
        if (v.isEmpty()) {
            throw new IllegalArgumentException("\"" + fieldName + "\" талбарыг бөглөнө үү.");
        }
        return v;
    }

    // ---------- Common tab layout ----------
    abstract static class CaseTab extends JPanel {
        final JTextArea log = new JTextArea();
        final JPanel form = new JPanel(new GridBagLayout());
        final JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 8));
        final Color accent;
        int row = 0;

        CaseTab(String title, String subtitle, Color accent) {
            this.accent = accent;
            setLayout(new BorderLayout(0, 14));
            setBackground(BG);
            setBorder(new EmptyBorder(0, 0, 14, 0));

            // Header
            JPanel header = new JPanel(new GridLayout(2, 1, 0, 2));
            header.setBackground(accent);
            header.setBorder(new EmptyBorder(14, 20, 14, 20));
            JLabel t = new JLabel(title);
            t.setForeground(Color.WHITE);
            t.setFont(new Font("SansSerif", Font.BOLD, 22));
            JLabel s = new JLabel(subtitle);
            s.setForeground(new Color(255, 255, 255, 215));
            s.setFont(new Font("SansSerif", Font.PLAIN, 13));
            header.add(t);
            header.add(s);
            add(header, BorderLayout.NORTH);

            // Left card: form + buttons
            form.setOpaque(false);
            buttons.setOpaque(false);
            JPanel buttonWrap = new JPanel(new BorderLayout());
            buttonWrap.setOpaque(false);
            buttonWrap.add(buttons, BorderLayout.NORTH);

            JPanel left = new JPanel(new BorderLayout(0, 16));
            left.setBackground(Color.WHITE);
            left.setBorder(new CompoundBorder(new LineBorder(new Color(0xDDE1EA)), new EmptyBorder(16, 16, 16, 16)));
            left.setPreferredSize(new Dimension(380, 0));
            left.add(form, BorderLayout.NORTH);
            left.add(buttonWrap, BorderLayout.CENTER);

            // Right: colored console log
            log.setEditable(false);
            log.setBackground(new Color(0x1E1E2E));
            log.setForeground(new Color(0xA6E3A1));
            log.setCaretColor(new Color(0xA6E3A1));
            log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            log.setMargin(new Insets(12, 12, 12, 12));
            JScrollPane sp = new JScrollPane(log);
            sp.setBorder(new LineBorder(accent, 2));

            JPanel body = new JPanel(new BorderLayout(14, 0));
            body.setBackground(BG);
            body.setBorder(new EmptyBorder(0, 14, 0, 14));
            body.add(left, BorderLayout.WEST);
            body.add(sp, BorderLayout.CENTER);
            add(body, BorderLayout.CENTER);
        }

        void addField(String label, JComponent c) {
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = row;
            g.anchor = GridBagConstraints.WEST;
            g.insets = new Insets(5, 0, 5, 12);
            JLabel l = new JLabel(label);
            l.setFont(UI.deriveFont(Font.BOLD));
            l.setForeground(accent.darker());
            form.add(l, g);
            g.gridx = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            g.insets = new Insets(5, 0, 5, 0);
            c.setFont(UI);
            form.add(c, g);
            row++;
        }

        void addButton(String text, Runnable action) {
            addButton(text, accent, action);
        }

        void addButton(String text, Color color, Runnable action) {
            JButton b = new JButton(text);
            b.setOpaque(true);
            b.setBackground(color);
            b.setForeground(Color.WHITE);
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setBorder(new EmptyBorder(11, 14, 11, 14));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(color.darker());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    b.setBackground(color);
                }
            });
            b.addActionListener(e -> run(action));
            buttons.add(b);
        }

        void addClearButton() {
            addButton("Лог цэвэрлэх", new Color(0x7F8C8D), () -> log.setText(""));
        }

        /** Service давхаргын гаралтыг энэ tab-ийн лог руу чиглүүлж ажиллуулна. */
        void run(Runnable r) {
            Log.out = s -> {
                log.append(s + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            };
            try {
                r.run();
            } catch (NumberFormatException ex) {
                Log.println("! Тоон утга буруу байна.");
            } catch (IllegalArgumentException ex) {
                Log.println("! " + ex.getMessage());
            }
            refresh();
        }

        void refresh() {
        }
    }

    // ---------- Кейс 1: Ирц ----------
    static class AttendanceTab extends CaseTab {
        final OnlineCourse course = new OnlineCourse("C101", "Java OOP", "https://meet.link/123");
        final JTextField id = text("S001");
        final JTextField name = text("Bat-Erdene");
        final JTextField date = text(LocalDate.now().toString());
        final JCheckBox present = new JCheckBox("Ирсэн (Present)", true);

        AttendanceTab() {
            super("Кейс 1 · Smart Attendance – Ирцийн систем",
                    "Хичээл: Java OOP (OnlineCourse) · https://meet.link/123", new Color(0x6C5CE7));
            present.setOpaque(false);
            addField("Оюутны ID", id);
            addField("Нэр", name);
            addField("Огноо", date);
            addField("Ирц", present);
            addButton("Ирц бүртгэх", () -> course.recordAttendance(
                    new Student(req(id, "Оюутны ID"), req(name, "Нэр")),
                    req(date, "Огноо"), present.isSelected()));
            addButton("Бүртгэл харах", new Color(0x00B894), course::showRecords);
            addClearButton();
        }
    }

    // ---------- Кейс 2: Номын сан ----------
    static class LibraryTab extends CaseTab {
        final DigitalLibrary lib = new DigitalLibrary("https://library.eis.edu.mn");
        final DefaultComboBoxModel<Book> model = new DefaultComboBoxModel<>();
        final JComboBox<Book> books = new JComboBox<>(model);
        final JTextField memberId = text("M001");
        final JTextField memberName = text("Anu");
        final JTextField newTitle = text("");
        final JTextField newAuthor = text("");

        LibraryTab() {
            super("Кейс 2 · Library – Номын сангийн систем",
                    "DigitalLibrary · https://library.eis.edu.mn", new Color(0x00A8A8));
            for (Book b : new Book[]{
                    new Book("B101", "Clean Code", "Robert C. Martin"),
                    new Book("B102", "Effective Java", "Joshua Bloch"),
                    new Book("B103", "Head First Java", "Kathy Sierra")}) {
                lib.addBook(b);
                model.addElement(b);
            }
            addField("Гишүүний ID", memberId);
            addField("Нэр", memberName);
            addField("Ном", books);
            addField("Шинэ ном", newTitle);
            addField("Зохиолч", newAuthor);
            addButton("Ном зээлэх", () -> lib.borrowBook(
                    new Member(req(memberId, "Гишүүний ID"), req(memberName, "Нэр")), selected()));
            addButton("Ном буцаах", new Color(0xE17055), () -> lib.returnBook(
                    new Member(req(memberId, "Гишүүний ID"), req(memberName, "Нэр")), selected()));
            addButton("Боломжит номууд", new Color(0x0984E3), lib::showAvailableBooks);
            addButton("Шинэ ном нэмэх", new Color(0x00B894), () -> {
                Book nb = new Book("B" + (101 + model.getSize()), req(newTitle, "Шинэ ном"), req(newAuthor, "Зохиолч"));
                lib.addBook(nb);
                model.addElement(nb);
                Log.println("Added: " + nb.title + " by " + nb.author);
                newTitle.setText("");
                newAuthor.setText("");
            });
            addClearButton();
        }

        Book selected() {
            Book b = (Book) books.getSelectedItem();
            if (b == null) throw new IllegalArgumentException("Ном сонгоно уу.");
            return b;
        }

        @Override
        void refresh() {
            books.repaint();
        }
    }

    // ---------- Кейс 3: Үнэлгээ ----------
    static class EvaluationTab extends CaseTab {
        final OnlineEvaluationService service = new OnlineEvaluationService("https://evaluation.eis.edu.mn");
        final JComboBox<CourseInfo> courses = new JComboBox<>(new CourseInfo[]{
                new CourseInfo("C101", "Software Engineering", "Dr. Enkhbayar"),
                new CourseInfo("C102", "Java OOP", "Dr. Bold"),
                new CourseInfo("C103", "Database Systems", "Dr. Sarnai")});
        final JTextField id = text("S001");
        final JTextField name = text("Tseren");
        final JSpinner rating = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        final JTextField comment = text("Great course with practical examples!");

        EvaluationTab() {
            super("Кейс 3 · Course Evaluation – Хичээлийн үнэлгээ",
                    "OnlineEvaluationService · https://evaluation.eis.edu.mn", new Color(0xE67E22));
            addField("Хичээл", courses);
            addField("Оюутны ID", id);
            addField("Нэр", name);
            addField("Үнэлгээ (1–5 ★)", rating);
            addField("Сэтгэгдэл", comment);
            addButton("Үнэлгээ илгээх", () -> service.submitEvaluation(
                    new Student(req(id, "Оюутны ID"), req(name, "Нэр")), course(),
                    (Integer) rating.getValue(), req(comment, "Сэтгэгдэл")));
            addButton("Сэтгэгдэл харах", new Color(0x0984E3), () -> service.showCourseFeedback(course()));
            addClearButton();
        }

        CourseInfo course() {
            return (CourseInfo) courses.getSelectedItem();
        }
    }

    // ---------- Кейс 4: Дотуур байр ----------
    static class DormTab extends CaseTab {
        final SmartDormitoryService dorm = new SmartDormitoryService("https://dorm.eis.edu.mn");
        final Map<String, Student> students = new HashMap<>();
        final JComboBox<Room> rooms = new JComboBox<>(new Room[]{
                new Room(201), new Room(202), new Room(203), new Room(204), new Room(205), new Room(206)});
        final JTextField id = text("S001");
        final JTextField name = text("Bolor");
        final JTextField dept = text("Computer Science");
        final JTextField date = text(LocalDate.now().toString());

        DormTab() {
            super("Кейс 4 · Smart Dormitory – Дотуур байр",
                    "SmartDormitoryService · https://dorm.eis.edu.mn", new Color(0xE84393));
            addField("Оюутны ID", id);
            addField("Нэр", name);
            addField("Тэнхим", dept);
            addField("Өрөө", rooms);
            addField("Огноо", date);
            addButton("Өрөө оноох", () -> {
                String i = req(id, "Оюутны ID"), n = req(name, "Нэр"), d = req(dept, "Тэнхим");
                Student s = students.computeIfAbsent(i, k -> new Student(k, n, d));
                dorm.assignRoom(s, (Room) rooms.getSelectedItem(), req(date, "Огноо"));
            });
            addButton("Өрөө чөлөөлөх", new Color(0xE17055), () -> {
                Student s = students.get(req(id, "Оюутны ID"));
                if (s == null) throw new IllegalArgumentException("Энэ оюутан бүртгэгдээгүй байна.");
                dorm.releaseRoom(s, (Room) rooms.getSelectedItem(), req(date, "Огноо"));
            });
            addButton("Байрны төлөв", new Color(0x0984E3), dorm::showRoomStatus);
            addClearButton();
        }

        @Override
        void refresh() {
            rooms.repaint();
        }
    }

    // ---------- Кейс 5: Эмнэлэг ----------
    static class CareTab extends CaseTab {
        final SmartPatientCareService care = new SmartPatientCareService("https://emcpatientcare.mn");
        final JComboBox<Doctor> doctors = new JComboBox<>(new Doctor[]{
                new Doctor("D001", "Enkhsaikhan", "Cardiology"),
                new Doctor("D002", "Oyun", "Neurology"),
                new Doctor("D003", "Batbayar", "Pediatrics")});
        final JTextField id = text("P001");
        final JTextField name = text("Nomin");
        final JTextField age = text("25");
        final JTextField date = text(LocalDate.now().toString());
        final JTextField diagnosis = text("Heart Checkup");

        CareTab() {
            super("Кейс 5 · Smart Patient Care – Эмнэлгийн систем",
                    "SmartPatientCareService · https://emcpatientcare.mn", new Color(0x2E86DE));
            addField("Өвчтөний ID", id);
            addField("Нэр", name);
            addField("Нас", age);
            addField("Эмч", doctors);
            addField("Огноо", date);
            addField("Онош", diagnosis);
            addButton("Цаг бүртгэх", () -> care.registerAppointment(
                    new Patient(req(id, "Өвчтөний ID"), req(name, "Нэр"), Integer.parseInt(age.getText().trim())),
                    (Doctor) doctors.getSelectedItem(), req(date, "Огноо"), req(diagnosis, "Онош")));
            addButton("Бүх цагууд харах", new Color(0x00B894), care::showAppointments);
            addClearButton();
        }
    }
}
