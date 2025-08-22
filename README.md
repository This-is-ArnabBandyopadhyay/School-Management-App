# 🎓 School Management App

📱 A **Java-based Android application** designed to manage student information and streamline communication between **administrators, students, and parents**.
The app features **role-based login** and separate panels for each user type with dedicated functionality.

---

## ✨ Features

### 🏫 Admin Panel

* 👩‍🎓 Add & manage **Student Profiles**
* 📢 Send **Notices** to students & parents
* 💬 View & **Respond** to student queries
* 📝 **Approve / ❌ Reject** leave applications
* 👨‍💼 Create **Admins** & manage student groups
* 📅 View **Upcoming Holidays**
* 🧑‍💻 View & update **My Profile**
* 🔒 Secure **Login / Logout** functionality


<img src="https://github.com/This-is-ArnabBandyopadhyay/School-Management-App/blob/main/IMG_20250822_202820.jpg?raw=true" width="300" height = "600" alt="screenshot description" />  


### 🎒 Student Panel

* 🧑‍💻 View & update **Profile**
* 📰 Read **Notices** shared by admin
* 📊 Check **Attendance Records**
* 📅 View **Holiday List**
* 📨 Apply for **Leave**
* ❓ Submit **Queries** to admin
* 🔒 **Login / Logout** functionality


<img src="https://github.com/This-is-ArnabBandyopadhyay/School-Management-App/blob/main/IMG_20250822_202803.jpg?raw=true" width="300" height = "600" alt="screenshot description" /> 

### 👨‍👩‍👧 Parent Panel

* 🧾 Access **Student Profile Details**
* 📰 View **Notices** from admin
* 📊 Track **Attendance & Leave Status** of child
* 📅 Access **Holiday List**
* 💬 Communicate with admin via **Queries**
* 🔒 **Login / Logout** functionality


<img src="https://github.com/This-is-ArnabBandyopadhyay/School-Management-App/blob/main/IMG_20250822_202734.jpg?raw=true" width="300" height = "600" alt="screenshot description" />

---

## 🛠️ Tech Stack

* ☕ **Language**: Java (Android)
* 🗄️ **Database**: SQLite
* 🖥️ **IDE**: Android Studio
* 🌍 **Version Control**: Git & GitHub
* 🎨 **UI/UX**: XML

---

## 🤝 Collaborators

| 🔧 Feature/Module                 | 👨‍💻 Contributor       |
| --------------------------------  | ----------------------- |
| 📊 Attendance + Additional Part   | **Arnab Bandyopadhyay** |
| 📨 Leave Management               | **Aritra Das**          |
| 📢 Notice & Holiday Management    | **Avishek Shaw**        |
| 🖥️ Dashboard Design + Login Page  | **Soham Bhattacharyya** |
| 🗄️ Database                       | **Vaibhav Kundu**       |
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
