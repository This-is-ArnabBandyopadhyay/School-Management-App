# 🎓 School Management App

📱 A **Java-based Android application** designed to manage student information and streamline communication between **administrators, students, and parents**.
The app features **role-based login** and separate panels for each user type with dedicated functionality.

---

## ✨ Features

### 🏫 Admin Panel

* 👩‍🎓 Add & manage **student profiles**
* 📢 Send **notices** to students & parents
* 💬 View & **respond** to student queries
* 📝 **Approve / ❌ Reject** leave applications
* 👨‍💼 Create **admins** & manage student groups
* 🔒 Secure **login / logout** functionality

### 🎒 Student Panel

* 🧑‍💻 View & update **profile**
* 📰 Read **notices** shared by admin
* 📊 Check **attendance records**
* 📅 View **holiday list**
* 📨 Apply for **leave**
* ❓ Submit **queries** to admin
* 🔒 **Login / logout** functionality

### 👨‍👩‍👧 Parent Panel

* 🧾 Access **student profile details**
* 📰 View **notices** from admin
* 📊 Track **attendance & leave status** of child
* 📅 Access **holiday list**
* 💬 Communicate with admin via **queries**
* 🔒 **Login / logout** functionality

---

## 🛠️ Tech Stack

* ☕ **Language**: Java (Android)
* 🗄️ **Database**: SQLite
* 🖥️ **IDE**: Android Studio
* 🌍 **Version Control**: Git & GitHub

---

## 🤝 Collaborators

| 🔧 Feature/Module                 | 👨‍💻 Contributor       |
| --------------------------------- | ----------------------- |
| 📊 Attendance + Additional Part   |**Arnab Bandopadhyay**  |
| 📨 Leave Management               | **Aritra Das**          |
| 📢 Notice & Holiday Management    | **Avishek Shaw**        |
| 🖥️ Dashboard Design + Login Page | **Soham Bhattacharyya** |
| 🗄️ Database                      | **Vaibhav Kundu**       |
| 💬 Query Resolution               | **Prashant Kumar Jha**  |

---

## 📌 Notes

* 📂 The repository is **public** for the time being.
* 🚀 Future improvements may include **Firebase integration**, **push notifications**, and **cloud database support** for scalability.

---

## ⚡ How to Install & Run

Follow these steps to set up and run the **School Management App** on your local machine:

### 🔹 1️⃣ Clone the Repository

```bash
git clone https://github.com/This-is-ArnabBandyopadhyay/School-Management-App.git
```

### 🔹 2️⃣ Open in Android Studio

* 📂 Open **Android Studio** → `Open an existing project`
* Select the cloned folder **School-Management-App**

### 🔹 3️⃣ Clean the Project

```bash
./gradlew clean
```

### 🔹 4️⃣ Build the APK (Debug Mode)

```bash
./gradlew assembleDebug
```

📍 Output APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

### 🔹 5️⃣ Run the App on Emulator / Device

* ▶️ In Android Studio → Click **Run** (Shift + F10)
* Or install the APK manually on your Android device:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🛑 Prerequisites

* ☕ **Java JDK 8 or above** installed
* 🤖 **Android Studio** (latest stable version)
* 📱 **Android Emulator** or USB debugging enabled device
* 🐙 **Git** installed on your system

---

✨ You’re all set! Now you can run the **School Management App** locally and explore all its features 🚀
