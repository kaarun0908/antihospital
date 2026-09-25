# 🏥 Hospital Management System (Java Swing)

A lightweight, standalone desktop Hospital Management System built in Java with an intuitive user interface and Maven build support.

## ✨ Features
- **User Authentication**:
  - **Login Screen**: Secure sign-in with credentials.
  - **Register Screen**: Registration for new hospital staff and administrators.
  - **Default Account**: `admin` / `admin123` (or `doctor` / `doc123`).
- **Patient Management**:
  - Admit new patients with full details (Name, Age, Gender, Disease/Diagnosis, Assigned Doctor, Phone).
  - Auto-generated Patient IDs (`P-101`, `P-102`, etc.).
  - Search/filter patients in real-time by name or diagnosis.
  - Discharge/Delete patient records with confirmation.
- **Local Persistence**:
  - Patient records and user accounts are saved locally in the `data/` folder as simple text files (`data/users.txt` and `data/patients.txt`).
  - No external database installation required!

## 🚀 How to Run

### Option 1: Using Maven
```bash
# Compile and run
mvn clean compile exec:java

# Or build executable JAR
mvn clean package
java -jar target/hospital-management-system-1.0.0.jar
```

### Option 2: In VS Code (Recommended)
1. Open this folder in VS Code.
2. Open `src/Main.java`.
3. Press **`F5`** or click **"Run Java"** at the top right.

### Option 3: 1-Click Run (`run.bat`)
- Simply double-click `run.bat` in this folder.

### Option 4: Terminal Command
```bash
javac -d bin src/*.java
java -cp bin Main
```

## 📁 Project Structure
```
hospital-management-system/
├── pom.xml               # Maven Project Object Model
├── .vscode/
│   ├── launch.json       # VS Code Run/Debug setup
│   └── settings.json     # Project paths
├── data/
│   ├── users.txt         # Saved staff credentials
│   └── patients.txt      # Saved patient records
├── src/
│   ├── Main.java           # Entry point
│   ├── LoginFrame.java     # Login UI
│   ├── RegisterFrame.java  # Staff Registration UI
│   ├── DashboardFrame.java # Main Hospital Dashboard UI
│   ├── DataManager.java    # Local file storage & authentication
│   └── Patient.java        # Patient model
├── run.bat               # 1-click Windows runner
└── README.md             # Documentation
```