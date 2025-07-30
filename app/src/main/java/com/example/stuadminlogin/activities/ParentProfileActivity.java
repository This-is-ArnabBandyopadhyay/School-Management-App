package com.example.stuadminlogin.activities; // Adjust package name as per your project structure

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

import com.example.stuadminlogin.R; // Ensure this points to your R file
import com.example.stuadminlogin.database.DatabaseHelper; // Ensure this points to your DatabaseHelper

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ParentProfileActivity extends Activity {

    // Header Views (for the main screen header if you have one, similar to Admin)
    TextView tvParentNameHeader, tvParentIdHeader, tvCreatedAtHeader;

    // Profile Views (for the sliding panel)
    ImageView profileImage, cameraIcon, pencilIcon;
    EditText etFullName, etEmail, etetPhone, etPassword;
    Button btnUpdate;

    // Layouts
    LinearLayout profilePanel, editableFieldsLayout;
    View overlayBackground;
    TextView tvParentIdPanel, tvCreatedAtPanel, tvDisplayName; // Moved here from loadProfile()

    DatabaseHelper db;
    int parentId;
    boolean isEditMode = false;
    String profilePhotoUri; // Stores the URI of the profile photo

    // Image handling
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_PHOTO = 101;
    private Uri imageUri; // URI for camera capture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile); // You'll need to create this layout XML

        db = new DatabaseHelper(this);
        parentId = getIntent().getIntExtra("parent_id", -1);
        if (parentId == -1) {
            Toast.makeText(this, "Parent ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadProfile();
        slideInPanel(); // Panel slides in on activity start

        pencilIcon.setOnClickListener(v -> toggleEditMode(true));
        btnUpdate.setOnClickListener(v -> {
            updateProfile();
            toggleEditMode(false);
        });
        overlayBackground.setOnClickListener(v -> slideOutPanel()); // Tap outside to hide
        cameraIcon.setOnClickListener(v -> showImagePickerDialog()); // Tap camera icon to change photo

        // Swipe gesture to hide panel
        profilePanel.setOnTouchListener(new SwipeGestureListener());
    }

    private void initViews() {
        // Header views (for the main activity background, if used)
        tvParentNameHeader = findViewById(R.id.tvParentNameHeader);
        tvParentIdHeader = findViewById(R.id.tvParentIdHeader);
        tvCreatedAtHeader = findViewById(R.id.tvCreatedAtHeader);

        // Profile panel views
        profileImage = findViewById(R.id.ivProfile);
        cameraIcon = findViewById(R.id.cameraIcon);
        pencilIcon = findViewById(R.id.pencilIcon);

        // Editable fields
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etetPhone = findViewById(R.id.etetPhone); // Corrected ID usage based on XML
        etPassword = findViewById(R.id.etPassword);

        btnUpdate = findViewById(R.id.btnUpdate);
        profilePanel = findViewById(R.id.profilePanel);
        editableFieldsLayout = findViewById(R.id.editableFieldsLayout);
        overlayBackground = findViewById(R.id.overlayBackground);

        // Panel information TextViews (Moved from loadProfile to initViews)
        tvParentIdPanel = findViewById(R.id.tvParentIdPanel);
        tvCreatedAtPanel = findViewById(R.id.tvCreatedAtPanel);
        tvDisplayName = findViewById(R.id.tvDisplayName); // Display name below profile photo

        // Initially hide camera and update button
        cameraIcon.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
    }

    private void loadProfile() {
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM parents WHERE parent_id = ?",
                new String[]{String.valueOf(parentId)});

        if (cursor.moveToFirst()) {
            // Get all data from cursor based on parent schema
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_no"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

            // IMPORTANT: Check if 'profile_photo_uri' column exists before trying to get its value
            int profilePhotoUriColumnIndex = cursor.getColumnIndex("profile_photo_uri");
            profilePhotoUri = (profilePhotoUriColumnIndex != -1) ? cursor.getString(profilePhotoUriColumnIndex) : null;


            // Set header info (top section of main activity, if used)
            tvParentNameHeader.setText(fullName);
            tvParentIdHeader.setText("Parent ID: " + parentId);
            tvCreatedAtHeader.setText("Member Since: " + createdAt);

            // Set panel info (sliding panel)
            tvParentIdPanel.setText(String.valueOf(parentId));
            tvCreatedAtPanel.setText(createdAt);
            tvDisplayName.setText(fullName); // Set display name below profile photo

            // Set editable fields
            etFullName.setText(fullName);
            etEmail.setText(email);
            etetPhone.setText(phone); // Using etetPhone for consistency with XML
            etPassword.setText(password);

            // Load profile photo
            loadProfileImage();
        } else {
            Toast.makeText(this, "Failed to load parent profile", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private void loadProfileImage() {
        if (profilePhotoUri != null && !profilePhotoUri.isEmpty()) {
            try {
                Uri uri = Uri.parse(profilePhotoUri);
                // The most robust way to load an image from any URI (content://, file://)
                InputStream inputStream = getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    profileImage.setImageBitmap(bitmap);
                    inputStream.close();
                } else {
                    profileImage.setImageResource(R.drawable.ic_profile); // Default image if stream is null
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                profileImage.setImageResource(R.drawable.ic_profile); // Default image on error
            }
        } else {
            profileImage.setImageResource(R.drawable.ic_profile); // Default image if URI is null/empty
        }
    }

    private void toggleEditMode(boolean enable) {
        isEditMode = enable;
        etFullName.setEnabled(enable);
        etEmail.setEnabled(enable);
        etetPhone.setEnabled(enable);
        etPassword.setEnabled(enable);

        // Change password visibility
        if (enable) {
            // Make password visible for editing
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // Hide password
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassword.setSelection(etPassword.getText().length()); // Keep cursor at end

        cameraIcon.setVisibility(enable ? View.VISIBLE : View.GONE);
        btnUpdate.setVisibility(enable ? View.VISIBLE : View.GONE);
        pencilIcon.setVisibility(enable ? View.GONE : View.VISIBLE); // Pencil hidden when in edit mode

        // Change background to indicate edit mode
        editableFieldsLayout.setBackgroundResource(enable ? R.drawable.edit_mode_background : android.R.color.transparent);
    }

    private void updateProfile() {
        // Updated SQL to match parent table schema, including profile_photo_uri
        ContentValues cv = new ContentValues();
        cv.put("name", etFullName.getText().toString());
        cv.put("email", etEmail.getText().toString());
        cv.put("phone_no", etetPhone.getText().toString());
        cv.put("password", etPassword.getText().toString());
        cv.put("profile_photo_uri", profilePhotoUri); // This will be null if no photo is set/selected

        int rowsAffected = db.getWritableDatabase().update("parents", cv, "parent_id = ?", new String[]{String.valueOf(parentId)});

        if (rowsAffected > 0) {
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            loadProfile(); // Reload profile to reflect changes, especially the display name
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void slideInPanel() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        // Set initial position outside the screen on the right
        profilePanel.setTranslationX(screenWidth); // Changed to slide from right
        profilePanel.getLayoutParams().width = screenWidth; // Make panel full width
        profilePanel.requestLayout();

        ObjectAnimator.ofFloat(profilePanel, "translationX", screenWidth, 0) // Changed animation values
                .setDuration(300)
                .start();

        overlayBackground.setVisibility(View.VISIBLE);
    }

    private void slideOutPanel() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        ObjectAnimator.ofFloat(profilePanel, "translationX", 0, screenWidth) // Changed animation values
                .setDuration(300)
                .start();

        overlayBackground.setVisibility(View.GONE);
        // Finish activity after animation
        new Handler().postDelayed(this::finish, 300);
    }

    private class SwipeGestureListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector = new GestureDetector(ParentProfileActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    private static final int SWIPE_THRESHOLD = 100; // Minimum distance for a swipe
                    private static final int SWIPE_VELOCITY_THRESHOLD = 100; // Minimum velocity for a swipe

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        float diffX = e2.getX() - e1.getX();
                        // Detect left to right swipe (to close panel from right)
                        if (diffX > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) { // Changed condition
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
                        // Request CAMERA permission
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                        } else {
                            takePhoto();
                        }
                    } else {
                        // Request READ_EXTERNAL_STORAGE permission (or READ_MEDIA_IMAGES for API 33+)
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) { // Added for API 33+
                            // Request relevant permissions based on API level
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) { // API 33+
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PICK_IMAGE);
                            } else { // Older APIs
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);
                            }
                        } else {
                            pickImageFromGallery();
                        }
                    }
                })
                .show();
    }

    private void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "ParentProfile_" + parentId);
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera"); // Added description
        // Use MediaStore.Images.Media.EXTERNAL_CONTENT_URI for API 29+ or filesDir for older devices
        // This example uses MediaStore, which is generally preferred for camera captures
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TAKE_PHOTO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_IMAGE) {
            // Check for both old and new storage permissions
            boolean granted = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    granted = true;
                    break;
                }
            }
            if (granted) {
                pickImageFromGallery();
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = null;
            if (requestCode == PICK_IMAGE && data != null) {
                selectedImageUri = data.getData();
            } else if (requestCode == TAKE_PHOTO) {
                selectedImageUri = imageUri; // Use the URI created for camera capture
            }

            if (selectedImageUri != null) {
                try {
                    // Save the image to internal app storage to ensure consistent URI storage
                    // This is robust as internal storage URIs don't change with external media changes
                    String filename = "parent_profile_" + parentId + ".jpg";
                    File file = new File(getFilesDir(), filename); // getFilesDir() is internal app storage

                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    if (inputStream != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                        out.close();
                        inputStream.close(); // Close inputStream here

                        // Store the internal URI
                        profilePhotoUri = Uri.fromFile(file).toString();
                        profileImage.setImageBitmap(bitmap);

                        // Update the database with the new profile photo URI
                        ContentValues cv = new ContentValues();
                        cv.put("profile_photo_uri", profilePhotoUri);
                        db.getWritableDatabase().update("parents", cv, "parent_id = ?", new String[]{String.valueOf(parentId)});

                        Toast.makeText(this, "Profile photo updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to get image stream", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}