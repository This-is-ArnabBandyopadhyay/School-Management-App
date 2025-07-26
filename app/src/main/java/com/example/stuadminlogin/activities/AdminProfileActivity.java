// package com.example.stuadminlogin.activities;

// import android.animation.ValueAnimator;
// import android.app.Activity;
// import android.database.Cursor;
// import android.os.Bundle;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.*;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// public class AdminProfileActivity extends Activity {

//     ImageView profileImage;
//     TextView tvAdminName, tvAdminId, tvDoj;
//     EditText etFullName, etEmail, etPhone, etAddress, etDob, etPassword;
//     Button btnEditProfile, btnUpdate;
//     LinearLayout profilePanel;
//     View overlayBackground;

//     DatabaseHelper db;
//     int adminId;
//     boolean isPanelVisible = false;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_admin_profile);

//         db = new DatabaseHelper(this);
//         adminId = getIntent().getIntExtra("admin_id", -1);
//         if (adminId == -1) {
//             Toast.makeText(this, "Admin ID not found", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         profileImage = findViewById(R.id.ivProfile);
//         tvAdminName = findViewById(R.id.tvAdminName);
//         tvAdminId = findViewById(R.id.tvAdminId);
//         tvDoj = findViewById(R.id.tvDoj);
//         etFullName = findViewById(R.id.etFullName);
//         etEmail = findViewById(R.id.etEmail);
//         etPhone = findViewById(R.id.etPhone);
//         etAddress = findViewById(R.id.etAddress);
//         etDob = findViewById(R.id.etDob);
//         etPassword = findViewById(R.id.etPassword);
//         btnEditProfile = findViewById(R.id.btnEditProfile);
//         btnUpdate = findViewById(R.id.btnUpdate);
//         profilePanel = findViewById(R.id.profilePanel);
//         overlayBackground = findViewById(R.id.overlayBackground);

//         toggleFields(false);
//         loadProfile();

//         // Start slide-in animation
//         profilePanel.post(this::slideInPanel);

//         btnEditProfile.setOnClickListener(v -> {
//             toggleFields(true);
//             btnUpdate.setVisibility(View.VISIBLE);
//         });

//         btnUpdate.setOnClickListener(v -> {
//             updateProfile();
//             toggleFields(false);
//             btnUpdate.setVisibility(View.GONE);
//         });

//         overlayBackground.setOnClickListener(v -> {
//             slideOutPanel();
//         });
//     }

//     private void toggleFields(boolean editable) {
//         etFullName.setEnabled(editable);
//         etEmail.setEnabled(editable);
//         etPhone.setEnabled(editable);
//         etAddress.setEnabled(editable);
//         etDob.setEnabled(editable);
//         etPassword.setEnabled(editable);
//     }

//     private void loadProfile() {
//         Cursor cursor = db.getReadableDatabase().rawQuery(
//                 "SELECT * FROM admins WHERE admin_id = ?",
//                 new String[]{String.valueOf(adminId)}
//         );

//         if (cursor.moveToFirst()) {
//             String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
//             String email = cursor.getString(cursor.getColumnIndexOrThrow("email_id"));
//             String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
//             String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
//             String dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
//             String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
//             String doj = cursor.getString(cursor.getColumnIndexOrThrow("date_of_joining"));

//             etFullName.setText(fullName);
//             etEmail.setText(email);
//             etPhone.setText(phone);
//             etAddress.setText(address);
//             etDob.setText(dob);
//             etPassword.setText(password);

//             tvAdminName.setText(fullName);
//             tvAdminId.setText("Admin ID: " + adminId);
//             tvDoj.setText("Joined: " + doj);

//             profileImage.setImageResource(R.drawable.ic_profile);
//         }
//         cursor.close();
//     }

//     private void updateProfile() {
//         db.getWritableDatabase().execSQL(
//                 "UPDATE admins SET full_name=?, email_id=?, phone_no=?, address=?, dob=?, password=? WHERE admin_id=?",
//                 new Object[]{
//                         etFullName.getText().toString(),
//                         etEmail.getText().toString(),
//                         etPhone.getText().toString(),
//                         etAddress.getText().toString(),
//                         etDob.getText().toString(),
//                         etPassword.getText().toString(),
//                         adminId
//                 }
//         );
//         Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
//         loadProfile();
//     }

//     private void slideInPanel() {
//         int screenWidth = getResources().getDisplayMetrics().widthPixels;
//         int targetWidth = (int) (screenWidth * 0.75);

//         ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
//         animator.setDuration(300);
//         animator.addUpdateListener(animation -> {
//             ViewGroup.LayoutParams params = profilePanel.getLayoutParams();
//             params.width = (int) animation.getAnimatedValue();
//             profilePanel.setLayoutParams(params);
//         });
//         animator.start();

//         overlayBackground.setVisibility(View.VISIBLE);
//         isPanelVisible = true;
//     }

//     private void slideOutPanel() {
//         int screenWidth = getResources().getDisplayMetrics().widthPixels;
//         int startWidth = (int) (screenWidth * 0.75);

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
//                 finish(); // Optionally close activity
//             }
//         });

//         isPanelVisible = false;
//     }
// }




// package com.example.stuadminlogin.activities;

// import android.Manifest;
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
// import android.provider.MediaStore;
// import android.view.GestureDetector;
// import android.view.MotionEvent;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.*;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.FileNotFoundException;



// import androidx.core.app.ActivityCompat;
// import androidx.core.content.ContextCompat;

// import com.example.stuadminlogin.R;
// import com.example.stuadminlogin.database.DatabaseHelper;

// import java.io.InputStream;

// public class AdminProfileActivity extends Activity {

//     ImageView profileImage, cameraIcon, pencilIcon;
//     TextView tvAdminName, tvAdminId, tvDoj;
//     EditText etFullName, etEmail, etPhone, etAddress, etDob, etPassword;
//     Button btnUpdate;
//     LinearLayout profilePanel;
//     View overlayBackground;
//     DatabaseHelper db;
//     int adminId;
//     String profilePhotoUri;
//     boolean isEditMode = false;
//     private static final int PICK_IMAGE = 100;
//     private static final int TAKE_PHOTO = 101;
//     private Uri imageUri;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_admin_profile);

//         db = new DatabaseHelper(this);
//         adminId = getIntent().getIntExtra("admin_id", -1);

//         if (adminId == -1) {
//             Toast.makeText(this, "Admin ID not found", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         initViews();
//         loadProfile();
//         slideInPanel();

//         pencilIcon.setOnClickListener(v -> toggleEditMode(true));
//         btnUpdate.setOnClickListener(v -> {
//             updateProfile();
//             toggleEditMode(false);
//         });

//         overlayBackground.setOnClickListener(v -> slideOutPanel());

//         cameraIcon.setOnClickListener(v -> showImagePickerDialog());

//         // Swipe gesture to hide
//         profilePanel.setOnTouchListener(new SwipeGestureListener());
//     }

//     private void initViews() {
//         profileImage = findViewById(R.id.ivProfile);
//         tvAdminName = findViewById(R.id.tvAdminName);
//         tvAdminId = findViewById(R.id.tvAdminId);
//         tvDoj = findViewById(R.id.tvDoj);

//         etFullName = findViewById(R.id.etFullName);
//         etEmail = findViewById(R.id.etEmail);
//         etPhone = findViewById(R.id.etPhone);
//         etAddress = findViewById(R.id.etAddress);
//         etDob = findViewById(R.id.etDob);
//         etPassword = findViewById(R.id.etPassword);

//         btnUpdate = findViewById(R.id.btnUpdate);
//         profilePanel = findViewById(R.id.profilePanel);
//         overlayBackground = findViewById(R.id.overlayBackground);
//         cameraIcon = findViewById(R.id.cameraIcon);
//         pencilIcon = findViewById(R.id.pencilIcon);

//         toggleEditMode(false);
//     }

//     private void toggleEditMode(boolean enable) {
//         isEditMode = enable;
//         etFullName.setEnabled(enable);
//         etEmail.setEnabled(enable);
//         etPhone.setEnabled(enable);
//         etAddress.setEnabled(enable);
//         etDob.setEnabled(enable);
//         etPassword.setEnabled(enable);
//         btnUpdate.setVisibility(enable ? View.VISIBLE : View.GONE);
//     }

//     private void loadProfile() {
//     Cursor cursor = db.getReadableDatabase().rawQuery(
//             "SELECT * FROM admins WHERE admin_id = ?",
//             new String[]{String.valueOf(adminId)}
//     );

//     if (cursor.moveToFirst()) {
//         String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
//         String email = cursor.getString(cursor.getColumnIndexOrThrow("email_id"));
//         String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
//         String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
//         String dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
//         String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
//         profilePhotoUri = cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"));
//         String doj = cursor.getString(cursor.getColumnIndexOrThrow("date_of_joining"));

//         // Set text fields
//         etFullName.setText(fullName);
//         etEmail.setText(email);
//         etPhone.setText(phone);
//         etAddress.setText(address);
//         etDob.setText(dob);
//         etPassword.setText(password);

//         // Set top details
//         tvAdminName.setText(fullName);
//         tvAdminId.setText("Admin ID: " + adminId);
//         tvDoj.setText("Joined: " + doj);

//         // Load profile image
//         if (profilePhotoUri != null && !profilePhotoUri.isEmpty()) {
//             try {
//                 Uri uri = Uri.parse(profilePhotoUri);
//                 Bitmap bitmap;

//                 if ("file".equals(uri.getScheme())) {
//                     File file = new File(uri.getPath());
//                     if (file.exists()) {
//                         bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                     } else {
//                         throw new FileNotFoundException("Image file not found at path: " + uri.getPath());
//                     }
//                 } else {
//                     InputStream inputStream = getContentResolver().openInputStream(uri);
//                     bitmap = BitmapFactory.decodeStream(inputStream);
//                     inputStream.close();
//                 }

//                 profileImage.setImageBitmap(bitmap);

//             } catch (Exception e) {
//                 e.printStackTrace();
//                 profileImage.setImageResource(R.drawable.ic_profile); // fallback
//             }
//         } else {
//             profileImage.setImageResource(R.drawable.ic_profile); // fallback if null or empty
//         }
//     }

//     cursor.close();
// }


//     private void updateProfile() {
//         db.getWritableDatabase().execSQL(
//                 "UPDATE admins SET full_name=?, email_id=?, phone_no=?, address=?, dob=?, password=?, profile_photo_uri=? WHERE admin_id=?",
//                 new Object[]{
//                         etFullName.getText().toString(),
//                         etEmail.getText().toString(),
//                         etPhone.getText().toString(),
//                         etAddress.getText().toString(),
//                         etDob.getText().toString(),
//                         etPassword.getText().toString(),
//                         profilePhotoUri,
//                         adminId
//                 }
//         );
//         Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
//         loadProfile();
//     }

//     private void slideInPanel() {
//         int screenWidth = getResources().getDisplayMetrics().widthPixels;
//         int targetWidth = (int) (screenWidth * 0.6); // 3/5 width

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
//         private final GestureDetector gestureDetector = new GestureDetector(AdminProfileActivity.this,
//                 new GestureDetector.SimpleOnGestureListener() {
//                     @Override
//                     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                         if (e1.getX() - e2.getX() > 100) {
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
//         values.put(MediaStore.Images.Media.TITLE, "NewPic");
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
// protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//     super.onActivityResult(requestCode, resultCode, data);

//     if (resultCode == RESULT_OK) {
//         Uri selectedImageUri;

//         if (requestCode == PICK_IMAGE) {
//             selectedImageUri = data.getData();
//         } else if (requestCode == TAKE_PHOTO) {
//             selectedImageUri = imageUri;
//         } else {
//             return;
//         }

//         try {
//             // Load bitmap from the selected image URI
//             InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//             Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

//             // Save to internal storage
//             String filename = "admin_profile_" + adminId + ".jpg";
//             File file = new File(getFilesDir(), filename);
//             FileOutputStream out = new FileOutputStream(file);
//             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//             out.flush();
//             out.close();

//             // ✅ Save URI for the file
//             profilePhotoUri = Uri.fromFile(file).toString();

//             // Show the image
//             profileImage.setImageBitmap(bitmap);

//             // ✅ Persist it immediately in the database
//             db.getWritableDatabase().execSQL(
//                 "UPDATE admins SET profile_photo_uri = ? WHERE admin_id = ?",
//                 new Object[]{profilePhotoUri, adminId}
//             );

//         } catch (Exception e) {
//             e.printStackTrace();
//             Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
//         }
//     }
// }

// }




package com.example.stuadminlogin.activities;

import android.Manifest;
import android.animation.ObjectAnimator;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.stuadminlogin.R;
import com.example.stuadminlogin.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AdminProfileActivity extends Activity {

    // Header Views
    TextView tvAdminName, tvAdminId, tvDoj;
    
    // Profile Views
    ImageView profileImage, cameraIcon, pencilIcon;
    EditText etFullName, etEmail, etPhone, etAddress, etDob, etPassword;
    Button btnUpdate;
    
    // Layouts
    LinearLayout profilePanel, editableFieldsLayout;
    View overlayBackground;
    
    DatabaseHelper db;
    int adminId;
    boolean isEditMode = false;
    String profilePhotoUri;
    
    // Image handling
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_PHOTO = 101;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        db = new DatabaseHelper(this);
        adminId = getIntent().getIntExtra("admin_id", -1);
        if (adminId == -1) {
            Toast.makeText(this, "Admin ID not found", Toast.LENGTH_SHORT).show();
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
    }

    private void initViews() {
        // Header views
        tvAdminName = findViewById(R.id.tvAdminName);
        tvAdminId = findViewById(R.id.tvAdminId);
        tvDoj = findViewById(R.id.tvDoj);

        // Profile panel views
        profileImage = findViewById(R.id.ivProfile);
        cameraIcon = findViewById(R.id.cameraIcon);
        pencilIcon = findViewById(R.id.pencilIcon);
        
        // Editable fields
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
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
    Cursor cursor = db.getReadableDatabase().rawQuery(
            "SELECT * FROM admins WHERE admin_id = ?",
            new String[]{String.valueOf(adminId)});

    if (cursor.moveToFirst()) {
        // Get all data from cursor
        String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email_id"));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        String dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        String doj = cursor.getString(cursor.getColumnIndexOrThrow("date_of_joining"));
        profilePhotoUri = cursor.getString(cursor.getColumnIndexOrThrow("profile_photo_uri"));

        // Set header info (top section)
        tvAdminName.setText(fullName);
        tvAdminId.setText("Admin ID: " + adminId);
        tvDoj.setText("Joined: " + doj);

        // Set panel info (sliding panel)
        TextView tvAdminIdPanel = findViewById(R.id.tvAdminIdPanel);
        TextView tvDojPanel = findViewById(R.id.tvDojPanel);
        TextView tvDisplayName = findViewById(R.id.tvDisplayName); // New display name view
        
        tvAdminIdPanel.setText(String.valueOf(adminId));
        tvDojPanel.setText(doj);
        tvDisplayName.setText(fullName); // Set display name below profile photo

        // Set editable fields
        etFullName.setText(fullName);
        etEmail.setText(email);
        etPhone.setText(phone);
        etAddress.setText(address);
        etDob.setText(dob);
        etPassword.setText(password);

        // Load profile photo
        loadProfileImage();
    } else {
        Toast.makeText(this, "Failed to load admin profile", Toast.LENGTH_SHORT).show();
    }
    
    if (cursor != null && !cursor.isClosed()) {
        cursor.close();
    }
}

    private void loadProfileImage() {
        if (profilePhotoUri != null && !profilePhotoUri.isEmpty()) {
            try {
                Uri uri = Uri.parse(profilePhotoUri);
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                profileImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                profileImage.setImageResource(R.drawable.ic_profile);
            }
        } else {
            profileImage.setImageResource(R.drawable.ic_profile);
        }
    }

    private void toggleEditMode(boolean enable) {
        isEditMode = enable;
        etFullName.setEnabled(enable);
        etEmail.setEnabled(enable);
        etPhone.setEnabled(enable);
        etAddress.setEnabled(enable);
        etDob.setEnabled(enable);
        etPassword.setEnabled(enable);
        
        // Change password visibility
        if (enable) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
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
                "UPDATE admins SET full_name=?, email_id=?, phone_no=?, address=?, dob=?, password=?, profile_photo_uri=? WHERE admin_id=?",
                new Object[]{
                        etFullName.getText().toString(),
                        etEmail.getText().toString(),
                        etPhone.getText().toString(),
                        etAddress.getText().toString(),
                        etDob.getText().toString(),
                        etPassword.getText().toString(),
                        profilePhotoUri,
                        adminId
                });
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
        loadProfile();
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
        private final GestureDetector gestureDetector = new GestureDetector(AdminProfileActivity.this,
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
        values.put(MediaStore.Images.Media.TITLE, "Profile_" + adminId);
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
                    String filename = "admin_profile_" + adminId + ".jpg";
                    File file = new File(getFilesDir(), filename);
                    
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    out.close();
                    inputStream.close();

                    profilePhotoUri = Uri.fromFile(file).toString();
                    profileImage.setImageBitmap(bitmap);
                    
                    db.getWritableDatabase().execSQL(
                        "UPDATE admins SET profile_photo_uri = ? WHERE admin_id = ?",
                        new Object[]{profilePhotoUri, adminId}
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}