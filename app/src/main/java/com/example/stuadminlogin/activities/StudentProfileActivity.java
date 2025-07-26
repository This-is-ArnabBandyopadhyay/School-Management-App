// package com.example.stuadminlogin.activities;

// import android.animation.ObjectAnimator;
// import android.app.Activity;
// import android.database.Cursor;
// import android.os.Bundle;
// import android.os.Handler;
// import android.text.Editable;
// import android.text.TextWatcher;
// import android.view.View;
// import android.widget.*;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class StudentProfileActivity extends Activity {

//     TextView tvRollNo, tvRegNo, tvClassSection, tvAdmissionDate, tvNameHeader;
//     EditText etName, etEmail, etPhone, etFather, etMother, etAddress, etDob, etPassword;
//     Button btnUpdate, btnEdit;
//     ImageView ivProfile;
//     DatabaseHelper db;
//     int studentId;
//     boolean isEditMode = false;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_student_profile);

//         db = new DatabaseHelper(this);
//         // studentId = SharedPrefManager.getUserId(this);

//         studentId = getIntent().getIntExtra("student_id", -1);
//         if (studentId == -1) {
//             Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         // Views
//         ivProfile = findViewById(R.id.imgProfilePhoto);
//         tvNameHeader = findViewById(R.id.tvNameHeader);
//         tvRollNo = findViewById(R.id.tvRollNo);
//         tvRegNo = findViewById(R.id.tvRegNo);
//         tvClassSection = findViewById(R.id.tvClassSection);
//         tvAdmissionDate = findViewById(R.id.tvAdmissionDate);
//         etName = findViewById(R.id.etName);
//         etEmail = findViewById(R.id.etEmail);
//         etPhone = findViewById(R.id.etPhone);
//         etFather = findViewById(R.id.etFather);
//         etMother = findViewById(R.id.etMother);
//         etAddress = findViewById(R.id.etAddress);
//         etDob = findViewById(R.id.etDob);
//         etPassword = findViewById(R.id.etPassword);

//         btnEdit = findViewById(R.id.btnEditProfile);
//         btnUpdate = findViewById(R.id.btnUpdate);

//         loadProfile();
//         setEditable(false);

//         btnEdit.setOnClickListener(v -> {
//             isEditMode = true;
//             setEditable(true);
//             btnUpdate.setVisibility(View.VISIBLE);
//         });

//         btnUpdate.setOnClickListener(v -> {
//             updateProfile();
//             setEditable(false);
//             isEditMode = false;
//             btnUpdate.setVisibility(View.GONE);
//         });

//         setupTextWatchers();

//         // 🔸 Animate the profile panel sliding from the left
//         new Handler().postDelayed(() -> {
//             ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.profilePanel), "translationX", 0f);
//             animator.setDuration(300);
//             animator.start();
//         }, 100); // Delay to ensure the view is laid out before animation
//     }

//     private void loadProfile() {
//         Cursor cursor = db.getReadableDatabase().rawQuery(
//                 "SELECT s.*, c.class_name, sec.section_name FROM students s " +
//                         "JOIN classes c ON s.class_id = c.class_id " +
//                         "JOIN sections sec ON s.section_id = sec.section_id WHERE student_id = ?",
//                 new String[]{String.valueOf(studentId)});
//         if (cursor.moveToFirst()) {
//             String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//             tvNameHeader.setText(name);
//             tvRollNo.setText("Roll No: " + cursor.getString(cursor.getColumnIndexOrThrow("roll_no")));
//             tvRegNo.setText("Reg No: " + cursor.getString(cursor.getColumnIndexOrThrow("registration_no")));
//             tvClassSection.setText("Class: " + cursor.getString(cursor.getColumnIndexOrThrow("class_name"))
//                     + " | Section: " + cursor.getString(cursor.getColumnIndexOrThrow("section_name")));
//             tvAdmissionDate.setText("Admission Date: " + cursor.getString(cursor.getColumnIndexOrThrow("admission_date")));

//             etName.setText(name);
//             etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
//             etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone_no")));
//             etFather.setText(cursor.getString(cursor.getColumnIndexOrThrow("fathername")));
//             etMother.setText(cursor.getString(cursor.getColumnIndexOrThrow("mothername")));
//             etAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
//             etDob.setText(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
//             etPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
//         }
//         cursor.close();
//     }

//     private void updateProfile() {
//         db.getWritableDatabase().execSQL("UPDATE students SET name=?, email=?, phone_no=?, fathername=?, mothername=?, address=?, dob=?, password=? WHERE student_id=?",
//                 new Object[]{
//                         etName.getText().toString().trim(),
//                         etEmail.getText().toString().trim(),
//                         etPhone.getText().toString().trim(),
//                         etFather.getText().toString().trim(),
//                         etMother.getText().toString().trim(),
//                         etAddress.getText().toString().trim(),
//                         etDob.getText().toString().trim(),
//                         etPassword.getText().toString().trim(),
//                         studentId
//                 });
//         Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//         tvNameHeader.setText(etName.getText().toString());
//     }

//     private void setEditable(boolean enabled) {
//         etName.setEnabled(enabled);
//         etEmail.setEnabled(enabled);
//         etPhone.setEnabled(enabled);
//         etFather.setEnabled(enabled);
//         etMother.setEnabled(enabled);
//         etAddress.setEnabled(enabled);
//         etDob.setEnabled(enabled);
//         etPassword.setEnabled(enabled);
//     }

//     private void setupTextWatchers() {
//         TextWatcher watcher = new TextWatcher() {
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//             public void afterTextChanged(Editable s) {}
//             public void onTextChanged(CharSequence s, int start, int before, int count) {
//                 if (isEditMode) {
//                     btnUpdate.setEnabled(areAllFieldsFilled());
//                 }
//             }
//         };
//         etName.addTextChangedListener(watcher);
//         etEmail.addTextChangedListener(watcher);
//         etPhone.addTextChangedListener(watcher);
//         etFather.addTextChangedListener(watcher);
//         etMother.addTextChangedListener(watcher);
//         etAddress.addTextChangedListener(watcher);
//         etDob.addTextChangedListener(watcher);
//         etPassword.addTextChangedListener(watcher);
//     }

//     private boolean areAllFieldsFilled() {
//         return !etName.getText().toString().trim().isEmpty()
//                 && !etEmail.getText().toString().trim().isEmpty()
//                 && !etPhone.getText().toString().trim().isEmpty()
//                 && !etFather.getText().toString().trim().isEmpty()
//                 && !etMother.getText().toString().trim().isEmpty()
//                 && !etAddress.getText().toString().trim().isEmpty()
//                 && !etDob.getText().toString().trim().isEmpty()
//                 && !etPassword.getText().toString().trim().isEmpty();
//     }
// }



// package com.example.stuadminlogin.activities;

// import android.Manifest;
// import android.animation.ObjectAnimator;
// import android.animation.ValueAnimator;
// import android.app.Activity;
// import android.app.AlertDialog;
// import android.content.ContentValues;
// import android.content.Intent;
// import android.content.pm.PackageManager;
// import android.database.Cursor;
// import android.graphics.Bitmap;
// import android.graphics.BitmapFactory;
// import android.net.Uri;
// import android.os.Bundle;
// import android.os.Handler;
// import android.provider.MediaStore;
// import android.text.Editable;
// import android.text.TextWatcher;
// import android.view.GestureDetector;
// import android.view.MotionEvent;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.*;
// import androidx.core.app.ActivityCompat;
// import androidx.core.content.ContextCompat;
// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.InputStream;

// public class StudentProfileActivity extends Activity {

//     // Header Views
//     TextView tvNameHeader, tvRollNo, tvRegNo, tvClassSection, tvAdmissionDate;
    
//     // Profile Panel Views
//     ImageView ivProfile, cameraIcon, pencilIcon;
//     EditText etName, etEmail, etPhone, etFather, etMother, etAddress, etDob, etPassword;
//     Button btnUpdate;
    
//     // Layouts
//     LinearLayout profilePanel;
//     View overlayBackground;
    
//     DatabaseHelper db;
//     int studentId;
//     boolean isEditMode = false;
//     String profilePhotoUri;
    
//     // Image handling constants
//     private static final int PICK_IMAGE = 100;
//     private static final int TAKE_PHOTO = 101;
//     private Uri imageUri;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_student_profile);

//         db = new DatabaseHelper(this);
//         studentId = getIntent().getIntExtra("student_id", -1);
//         if (studentId == -1) {
//             Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         initViews();
//         loadProfile();
//         slideInPanel();

//         // Set up click listeners
//         pencilIcon.setOnClickListener(v -> toggleEditMode(true));
//         btnUpdate.setOnClickListener(v -> {
//             updateProfile();
//             toggleEditMode(false);
//         });
//         overlayBackground.setOnClickListener(v -> slideOutPanel());
//         cameraIcon.setOnClickListener(v -> showImagePickerDialog());

//         // Swipe gesture to hide panel
//         profilePanel.setOnTouchListener(new SwipeGestureListener());
//     }

//     private void initViews() {
//         // Header views
//         tvNameHeader = findViewById(R.id.tvNameHeader);
//         tvRollNo = findViewById(R.id.tvRollNo);
//         tvRegNo = findViewById(R.id.tvRegNo);
//         tvClassSection = findViewById(R.id.tvClassSection);
//         tvAdmissionDate = findViewById(R.id.tvAdmissionDate);

//         // Profile panel views
//         ivProfile = findViewById(R.id.ivProfile);
//         cameraIcon = findViewById(R.id.cameraIcon);
//         pencilIcon = findViewById(R.id.pencilIcon);
        
//         etName = findViewById(R.id.etName);
//         etEmail = findViewById(R.id.etEmail);
//         etPhone = findViewById(R.id.etPhone);
//         etFather = findViewById(R.id.etFather);
//         etMother = findViewById(R.id.etMother);
//         etAddress = findViewById(R.id.etAddress);
//         etDob = findViewById(R.id.etDob);
//         etPassword = findViewById(R.id.etPassword);
        
//         btnUpdate = findViewById(R.id.btnUpdate);
//         profilePanel = findViewById(R.id.profilePanel);
//         overlayBackground = findViewById(R.id.overlayBackground);

//         // Initially hide update button
//         btnUpdate.setVisibility(View.GONE);
//     }

//     private void loadProfile() {
//         Cursor cursor = db.getReadableDatabase().rawQuery(
//                 "SELECT s.*, c.class_name, sec.section_name FROM students s " +
//                         "JOIN classes c ON s.class_id = c.class_id " +
//                         "JOIN sections sec ON s.section_id = sec.section_id WHERE student_id = ?",
//                 new String[]{String.valueOf(studentId)});

//         if (cursor.moveToFirst()) {
//             // Set header info
//             String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//             tvNameHeader.setText(name);
//             tvRollNo.setText("Roll No: " + cursor.getString(cursor.getColumnIndexOrThrow("roll_no")));
//             tvRegNo.setText("Reg No: " + cursor.getString(cursor.getColumnIndexOrThrow("registration_no")));
//             tvClassSection.setText("Class: " + cursor.getString(cursor.getColumnIndexOrThrow("class_name")) + 
//                     " | Section: " + cursor.getString(cursor.getColumnIndexOrThrow("section_name")));
//             tvAdmissionDate.setText("Admission Date: " + cursor.getString(cursor.getColumnIndexOrThrow("admission_date")));

//             // Set editable fields
//             etName.setText(name);
//             etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
//             etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone_no")));
//             etFather.setText(cursor.getString(cursor.getColumnIndexOrThrow("fathername")));
//             etMother.setText(cursor.getString(cursor.getColumnIndexOrThrow("mothername")));
//             etAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
//             etDob.setText(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
//             etPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));

//             // Load profile photo
//             profilePhotoUri = cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"));
//             loadProfileImage();
//         }
//         cursor.close();
//     }

//     private void loadProfileImage() {
//         if (profilePhotoUri != null && !profilePhotoUri.isEmpty()) {
//             try {
//                 Uri uri = Uri.parse(profilePhotoUri);
//                 Bitmap bitmap;

//                 if ("file".equals(uri.getScheme())) {
//                     // Load from internal storage
//                     File file = new File(uri.getPath());
//                     if (file.exists()) {
//                         bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                         ivProfile.setImageBitmap(bitmap);
//                     } else {
//                         throw new Exception("Image file not found");
//                     }
//                 } else {
//                     // Load from content URI
//                     InputStream inputStream = getContentResolver().openInputStream(uri);
//                     bitmap = BitmapFactory.decodeStream(inputStream);
//                     ivProfile.setImageBitmap(bitmap);
//                     inputStream.close();
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 ivProfile.setImageResource(R.drawable.ic_profile); // Fallback image
//             }
//         } else {
//             ivProfile.setImageResource(R.drawable.ic_profile); // Default image
//         }
//     }

//     private void toggleEditMode(boolean enable) {
//         isEditMode = enable;
//         etName.setEnabled(enable);
//         etEmail.setEnabled(enable);
//         etPhone.setEnabled(enable);
//         etFather.setEnabled(enable);
//         etMother.setEnabled(enable);
//         etAddress.setEnabled(enable);
//         etDob.setEnabled(enable);
//         etPassword.setEnabled(enable);
//         btnUpdate.setVisibility(enable ? View.VISIBLE : View.GONE);
//     }

//     private void updateProfile() {
//         db.getWritableDatabase().execSQL(
//                 "UPDATE students SET name=?, email=?, phone_no=?, fathername=?, mothername=?, " +
//                         "address=?, dob=?, password=?, profile_photo_uri=? WHERE student_id=?",
//                 new Object[]{
//                         etName.getText().toString().trim(),
//                         etEmail.getText().toString().trim(),
//                         etPhone.getText().toString().trim(),
//                         etFather.getText().toString().trim(),
//                         etMother.getText().toString().trim(),
//                         etAddress.getText().toString().trim(),
//                         etDob.getText().toString().trim(),
//                         etPassword.getText().toString().trim(),
//                         profilePhotoUri,
//                         studentId
//                 });
//         Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//         tvNameHeader.setText(etName.getText().toString());
//     }

//     private void slideInPanel() {
//         int screenWidth = getResources().getDisplayMetrics().widthPixels;
//         int targetWidth = (int) (screenWidth * 0.6); // 3/5 of screen width

//         ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
//         animator.setDuration(300);
//         animator.addUpdateListener(animation -> {
//             ViewGroup.LayoutParams params = profilePanel.getLayoutParams();
//             params.width = (int) animation.getAnimatedValue();
//             profilePanel.setLayoutParams(params);
//         });
//         animator.start();

//         overlayBackground.setVisibility(View.VISIBLE);
//     }

//     private void slideOutPanel() {
//         int screenWidth = getResources().getDisplayMetrics().widthPixels;
//         int startWidth = (int) (screenWidth * 0.6);

//         ValueAnimator animator = ValueAnimator.ofInt(startWidth, 0);
//         animator.setDuration(300);
//         animator.addUpdateListener(animation -> {
//             ViewGroup.LayoutParams params = profilePanel.getLayoutParams();
//             params.width = (int) animation.getAnimatedValue();
//             profilePanel.setLayoutParams(params);
//         });

//         animator.start();
//         animator.addListener(new android.animation.AnimatorListenerAdapter() {
//             @Override
//             public void onAnimationEnd(android.animation.Animator animation) {
//                 overlayBackground.setVisibility(View.GONE);
//                 finish();
//             }
//         });
//     }

//     private class SwipeGestureListener implements View.OnTouchListener {
//         private final GestureDetector gestureDetector = new GestureDetector(StudentProfileActivity.this,
//                 new GestureDetector.SimpleOnGestureListener() {
//                     @Override
//                     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                         if (e1.getX() - e2.getX() > 100) { // Left swipe
//                             slideOutPanel();
//                             return true;
//                         }
//                         return false;
//                     }
//                 });

//         @Override
//         public boolean onTouch(View v, MotionEvent event) {
//             return gestureDetector.onTouchEvent(event);
//         }
//     }

//     private void showImagePickerDialog() {
//         String[] options = {"Take Photo", "Choose from Gallery"};
//         new AlertDialog.Builder(this)
//                 .setTitle("Set Profile Photo")
//                 .setItems(options, (dialog, which) -> {
//                     if (which == 0) {
//                         if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
//                         } else {
//                             takePhoto();
//                         }
//                     } else {
//                         pickImageFromGallery();
//                     }
//                 })
//                 .show();
//     }

//     private void takePhoto() {
//         ContentValues values = new ContentValues();
//         values.put(MediaStore.Images.Media.TITLE, "StudentProfile_" + studentId);
//         imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//         Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//         startActivityForResult(cameraIntent, TAKE_PHOTO);
//     }

//     private void pickImageFromGallery() {
//         Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//         startActivityForResult(pickIntent, PICK_IMAGE);
//     }

//     @Override
//     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//         super.onActivityResult(requestCode, resultCode, data);

//         if (resultCode == RESULT_OK) {
//             Uri selectedImageUri = null;
//             if (requestCode == PICK_IMAGE) {
//                 selectedImageUri = data.getData();
//             } else if (requestCode == TAKE_PHOTO) {
//                 selectedImageUri = imageUri;
//             }

//             if (selectedImageUri != null) {
//                 try {
//                     // Save to internal storage
//                     String filename = "student_profile_" + studentId + ".jpg";
//                     File file = new File(getFilesDir(), filename);
                    
//                     // Compress and save the image
//                     InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//                     Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                     FileOutputStream out = new FileOutputStream(file);
//                     bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out); // 80% quality
//                     out.flush();
//                     out.close();
//                     inputStream.close();

//                     // Save the URI
//                     profilePhotoUri = Uri.fromFile(file).toString();
                    
//                     // Update the image view
//                     ivProfile.setImageBitmap(bitmap);
                    
//                     // Immediately update in database
//                     db.getWritableDatabase().execSQL(
//                         "UPDATE students SET profile_photo_uri = ? WHERE student_id = ?",
//                         new Object[]{profilePhotoUri, studentId}
//                     );

//                 } catch (Exception e) {
//                     e.printStackTrace();
//                     Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
//                 }
//             }
//         }
//     }
// }


package com.example.stuadminlogin.activities;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.text.InputType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class StudentProfileActivity extends Activity {

    // Header Views
    TextView tvNameHeader, tvRollNo, tvRegNo, tvClassSection, tvAdmissionDate;
    
    // Profile Views
    ImageView ivProfile, cameraIcon, pencilIcon;
    EditText etName, etEmail, etPhone, etFather, etMother, etAddress, etDob, etPassword;
    TextView tvRollNoView, tvRegNoView, tvClassView, tvSectionView;
    Button btnUpdate;
    
    // Layouts
    LinearLayout profilePanel, editableFieldsLayout;
    View overlayBackground;
    
    DatabaseHelper db;
    int studentId;
    boolean isEditMode = false;
    String profilePhotoUri;
    
    // Image handling
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_PHOTO = 101;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        db = new DatabaseHelper(this);
        studentId = getIntent().getIntExtra("student_id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadProfile();
        slideInPanel();

        pencilIcon.setOnClickListener(v -> toggleEditMode(true));
        btnUpdate.setOnClickListener(v -> {
            updateProfile();
            toggleEditMode(false);
        });
        overlayBackground.setOnClickListener(v -> slideOutPanel());
        cameraIcon.setOnClickListener(v -> showImagePickerDialog());

        // Swipe gesture to hide panel
        profilePanel.setOnTouchListener(new SwipeGestureListener());
        // Set initial position off-screen
    profilePanel.post(() -> {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        profilePanel.setTranslationX(-screenWidth);
    });
    }

    private void initViews() {
        // Header views
        tvNameHeader = findViewById(R.id.tvNameHeader);
        tvRollNo = findViewById(R.id.tvRollNo);
        tvRegNo = findViewById(R.id.tvRegNo);
        tvClassSection = findViewById(R.id.tvClassSection);
        tvAdmissionDate = findViewById(R.id.tvAdmissionDate);

        // Profile panel views
        ivProfile = findViewById(R.id.ivProfile);
        cameraIcon = findViewById(R.id.cameraIcon);
        pencilIcon = findViewById(R.id.pencilIcon);
        
        // Non-editable fields
        tvRollNoView = findViewById(R.id.tvRollNoView);
        tvRegNoView = findViewById(R.id.tvRegNoView);
        tvClassView = findViewById(R.id.tvClassView);
        tvSectionView = findViewById(R.id.tvSectionView);
        
        // Editable fields
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etFather = findViewById(R.id.etFather);
        etMother = findViewById(R.id.etMother);
        etAddress = findViewById(R.id.etAddress);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        
        btnUpdate = findViewById(R.id.btnUpdate);
        profilePanel = findViewById(R.id.profilePanel);
        editableFieldsLayout = findViewById(R.id.editableFieldsLayout);
        overlayBackground = findViewById(R.id.overlayBackground);

        // Initially hide camera and update button
        cameraIcon.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
    }

    private void loadProfile() {
    Cursor cursor = null;
    try {
        cursor = db.getReadableDatabase().rawQuery(
                "SELECT s.*, c.class_name, sec.section_name FROM students s " +
                "JOIN classes c ON s.class_id = c.class_id " +
                "JOIN sections sec ON s.section_id = sec.section_id WHERE student_id = ?",
                new String[]{String.valueOf(studentId)});

        if (cursor.moveToFirst()) {
            // Get all data from cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String rollNo = cursor.getString(cursor.getColumnIndexOrThrow("roll_no"));
            String regNo = cursor.getString(cursor.getColumnIndexOrThrow("registration_no"));
            String className = cursor.getString(cursor.getColumnIndexOrThrow("class_name"));
            String sectionName = cursor.getString(cursor.getColumnIndexOrThrow("section_name"));
            String admissionDate = cursor.getString(cursor.getColumnIndexOrThrow("admission_date"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
            String fatherName = cursor.getString(cursor.getColumnIndexOrThrow("fathername"));
            String motherName = cursor.getString(cursor.getColumnIndexOrThrow("mothername"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            profilePhotoUri = cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"));

            // Set header info (top section)
            tvNameHeader.setText(name);
            tvRollNo.setText(getString(R.string.roll_no_format, rollNo));
            tvRegNo.setText(getString(R.string.reg_no_format, regNo));
            tvClassSection.setText(getString(R.string.class_section_format, className, sectionName));
            tvAdmissionDate.setText(getString(R.string.admission_date_format, admissionDate));

            // Set display name below profile photo
            TextView tvDisplayName = findViewById(R.id.tvDisplayName);
            tvDisplayName.setText(name);

            // Set view-only fields in panel
            tvRollNoView.setText(rollNo);
            tvRegNoView.setText(regNo);
            tvClassView.setText(className);
            tvSectionView.setText(sectionName);

            // Set editable fields
            etName.setText(name);
            etEmail.setText(email);
            etPhone.setText(phone);
            etFather.setText(fatherName);
            etMother.setText(motherName);
            etAddress.setText(address);
            etDob.setText(dob);
            etPassword.setText(password);

            // Load profile photo
            loadProfileImage();
        } else {
            Toast.makeText(this, "Student data not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Error loading profile", Toast.LENGTH_SHORT).show();
        finish();
    } finally {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}

    private void loadProfileImage() {
        if (profilePhotoUri != null && !profilePhotoUri.isEmpty()) {
            try {
                Uri uri = Uri.parse(profilePhotoUri);
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                ivProfile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                ivProfile.setImageResource(R.drawable.ic_profile);
            }
        } else {
            ivProfile.setImageResource(R.drawable.ic_profile);
        }
    }

    private void toggleEditMode(boolean enable) {
        isEditMode = enable;
        etName.setEnabled(enable);
        etEmail.setEnabled(enable);
        etPhone.setEnabled(enable);
        etFather.setEnabled(enable);
        etMother.setEnabled(enable);
        etAddress.setEnabled(enable);
        etDob.setEnabled(enable);
        etPassword.setEnabled(enable);

        if (enable) {
        // Use textPassword input type but with a visibility toggle
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        // Add a show/hide toggle button if using TextInputLayout
    } else {
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    etPassword.setSelection(etPassword.getText().length());
        
        cameraIcon.setVisibility(enable ? View.VISIBLE : View.GONE);
        btnUpdate.setVisibility(enable ? View.VISIBLE : View.GONE);
        pencilIcon.setVisibility(enable ? View.GONE : View.VISIBLE);
        
        // Change background to indicate edit mode
        editableFieldsLayout.setBackgroundResource(enable ? R.drawable.edit_mode_background : android.R.color.transparent);
    }

    private void updateProfile() {
        db.getWritableDatabase().execSQL(
                "UPDATE students SET name=?, email=?, phone_no=?, fathername=?, mothername=?, " +
                        "address=?, dob=?, password=?, profile_photo_uri=? WHERE student_id=?",
                new Object[]{
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etFather.getText().toString().trim(),
                        etMother.getText().toString().trim(),
                        etAddress.getText().toString().trim(),
                        etDob.getText().toString().trim(),
                        etPassword.getText().toString().trim(),
                        profilePhotoUri,
                        studentId
                });
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        tvNameHeader.setText(etName.getText().toString());
    }

    private void slideInPanel() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        profilePanel.getLayoutParams().width = screenWidth;
        profilePanel.requestLayout();

        ObjectAnimator.ofFloat(profilePanel, "translationX", -screenWidth, 0)
                .setDuration(300)
                .start();

        overlayBackground.setVisibility(View.VISIBLE);
    }

    private void slideOutPanel() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        
        ObjectAnimator.ofFloat(profilePanel, "translationX", 0, -screenWidth)
                .setDuration(300)
                .start();

        overlayBackground.setVisibility(View.GONE);
        new Handler().postDelayed(this::finish, 300);
    }

    private class SwipeGestureListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector = new GestureDetector(StudentProfileActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        if (e1.getX() - e2.getX() > 100) {
                            slideOutPanel();
                            return true;
                        }
                        return false;
                    }
                });

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }

    private void showImagePickerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Update Profile Photo")
                .setItems(new String[]{"Take Photo", "Choose from Gallery"}, (dialog, which) -> {
                    if (which == 0) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                        } else {
                            takePhoto();
                        }
                    } else {
                        pickImageFromGallery();
                    }
                })
                .show();
    }

    private void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile_" + studentId);
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, TAKE_PHOTO);
    }

    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = requestCode == PICK_IMAGE ? data.getData() : imageUri;
            
            if (selectedImageUri != null) {
                try {
                    // Save to internal storage
                    String filename = "profile_" + studentId + ".jpg";
                    File file = new File(getFilesDir(), filename);
                    
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    out.close();
                    inputStream.close();

                    profilePhotoUri = Uri.fromFile(file).toString();
                    ivProfile.setImageBitmap(bitmap);
                    
                    db.getWritableDatabase().execSQL(
                        "UPDATE students SET profile_photo_uri = ? WHERE student_id = ?",
                        new Object[]{profilePhotoUri, studentId}
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}