import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataManager {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/users.txt";
    private static final String PATIENTS_FILE = "data/patients.txt";

    static {
        initFiles();
    }

    private static void initFiles() {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File usersFile = new File(USERS_FILE);
            if (!usersFile.exists() || usersFile.length() == 0) {
                try (PrintWriter out = new PrintWriter(new FileWriter(usersFile, true))) {
                    out.println("admin|admin123|Administrator");
                    out.println("doctor|doc123|Dr. Sarah Smith");
                    out.println("nurse|nurse123|Nurse Joy");
                }
            }

            File patientsFile = new File(PATIENTS_FILE);
            if (!patientsFile.exists() || patientsFile.length() == 0) {
                try (PrintWriter out = new PrintWriter(new FileWriter(patientsFile, true))) {
                    out.println("P-101|John Doe|45|Male|Hypertension|Dr. Sarah Smith|+1 555-0101");
                    out.println("P-102|Emily Davis|29|Female|Asthma|Dr. Sarah Smith|+1 555-0102");
                    out.println("P-103|Robert Wilson|62|Male|Diabetes Type 2|Dr. Michael Lee|+1 555-0103");
                    out.println("P-104|Sophia Martinez|34|Female|Migraine|Dr. Sarah Smith|+1 555-0104");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authenticate user credentials
    public static boolean authenticate(String username, String password) {
        initFiles();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 2) {
                    if (parts[0].trim().equalsIgnoreCase(username.trim()) && parts[1].trim().equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register a new user
    public static boolean registerUser(String username, String password, String fullName) {
        initFiles();
        username = username.trim();
        if (username.isEmpty() || password.isEmpty() || fullName.trim().isEmpty()) {
            return false;
        }

        // Check if username already exists
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 1 && parts[0].trim().equalsIgnoreCase(username)) {
                    return false; // already exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Append new user
        try (PrintWriter out = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            out.println(username + "|" + password + "|" + fullName.trim());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all patient records
    public static List<Patient> getAllPatients() {
        initFiles();
        List<Patient> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Patient p = Patient.fromFileString(line);
                    if (p != null) {
                        list.add(p);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add a new patient record
    public static void addPatient(Patient patient) {
        initFiles();
        try (PrintWriter out = new PrintWriter(new FileWriter(PATIENTS_FILE, true))) {
            out.println(patient.toFileString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete a patient by ID
    public static void deletePatient(String patientId) {
        initFiles();
        List<Patient> current = getAllPatients();
        try (PrintWriter out = new PrintWriter(new FileWriter(PATIENTS_FILE, false))) {
            for (Patient p : current) {
                if (!p.getId().equalsIgnoreCase(patientId)) {
                    out.println(p.toFileString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper to generate the next patient ID
    public static String getNextPatientId() {
        List<Patient> patients = getAllPatients();
        int max = 100;
        for (Patient p : patients) {
            String idStr = p.getId().replaceAll("[^0-9]", "");
            try {
                int idNum = Integer.parseInt(idStr);
                if (idNum > max) {
                    max = idNum;
                }
            } catch (NumberFormatException ignored) {}
        }
        return "P-" + (max + 1);
    }
}
