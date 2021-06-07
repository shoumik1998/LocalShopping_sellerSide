package com.example.hand_shoping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hand_shoping.R.drawable.not_orderable_24;
import static com.example.hand_shoping.R.drawable.orderable_24;

public class MainActivity extends AppCompatActivity {

    private TextView textView,orderable_status_txtView;
    private EditText titleEditxt,priceEditxt;
    private ImageView imageView,orderable_img;
    private Button cambtn,galbtn,uploadBtn;
    private  LogIn_Activity logIn_activity;
    private Activity activity;
    public SharedPreferences sharedPreferences;
    public static  MainActivity mainActivity;
    String cam_permission[];
    String storage_permission[];
    private Uri image_uri;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private  int orderable_status=1;


    private  static final  int IMG_PICK_CAM_CODE=3;
    private  static  final int IMG_PICK_GALLERY_CODE=4;
    private  static  final  int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=5;
    private  static  final  int  MY_PERMISSIONS_REQUEST_CAMERA=6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(MainActivity.this);


        sharedPreferences=getSharedPreferences("myFile", Context.MODE_PRIVATE);
        mainActivity=this;
        if (!rlogstatus()) {
            startActivity(new Intent(MainActivity.this, LogIn_Activity.class));
            finish();
        }
        toolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.display_shop_nameID);
        imageView=findViewById(R.id.mainImgID);
        cambtn=findViewById(R.id.camID);
        galbtn=findViewById(R.id.galaryID);
        uploadBtn=findViewById(R.id.uploadbtnID);
        orderable_img = findViewById(R.id.orderable_status_imgID);
        orderable_status_txtView = findViewById(R.id.orderable_status_txtID);

        uploadBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_up_btn_disable));
        uploadBtn.setEnabled(false);
        titleEditxt=findViewById(R.id.titleeditID);
        priceEditxt=findViewById(R.id.priceeditID);
        textView.setText(rName());
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (orderable_status == 1) {
            orderable_status_txtView.setText("Orderable");
        } else if (orderable_status == 0) {
            orderable_status_txtView.setText("Not Orderable");
        }

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(titleEditxt.getText().toString()) || TextUtils.isEmpty(priceEditxt.getText().toString())){
                    Toast.makeText(MainActivity.this, "Title and Price  must be needed", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                }


            }
        });

        orderable_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderable_status==0) {
                    orderable_img.setImageResource(R.drawable.orderable_24);
                    orderable_status=1;
                    Toast.makeText(MainActivity.this, ""+orderable_status, Toast.LENGTH_SHORT).show();
                    orderable_status_txtView.setText("Orderable");
                } else if (orderable_status==1) {
                    orderable_img.setImageResource(R.drawable.not_orderable_24);
                    orderable_status=0;
                    Toast.makeText(MainActivity.this, ""+orderable_status, Toast.LENGTH_SHORT).show();
                    orderable_status_txtView.setText("Not Orderable");

                }
            }
        });
        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cam_picker();
            }
        });

        galbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    gallery_picker();
            }
        });
        logIn_activity = new LogIn_Activity();

        getting_Storage_permission();
        getting_camera_permission();
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(titleEditxt.getVisibility()==View.VISIBLE){
            InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCurrentFocus() != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case  R.id.logOutID:
                wlogStatus(false);
                wName("","");
                startActivity(new Intent(MainActivity.this, LogIn_Activity.class));
                finish();
                return true;
            case R.id.myAccountID:
                Intent intent=new Intent(MainActivity.this,AllContents.class);
                startActivity(intent);
                return  true;

            case R.id.order_menuID:
                Intent intent1 = new Intent(MainActivity.this, Order_Activity.class);
                startActivity(intent1);

        }


        return super.onOptionsItemSelected(item);

    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mai_menu,menu);
        return true;
    }

    public  static    MainActivity getInstance(){
        return mainActivity;
    }

    public   void  wlogStatus(boolean status){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("preflogstatus",status);
        editor.commit();
    }

    public  boolean rlogstatus(){
        return  sharedPreferences.getBoolean("preflogstatus",false);
    }

    public  void  wName(String name,String user_name){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("prefName",name);
        editor.putString("pref_user_name",user_name);
        editor.commit();
    }

    public  String r_user_name(){
        return  sharedPreferences.getString("pref_user_name","Not Found");
    }

    public  String rName(){
        return sharedPreferences.getString("prefName","Not found ");
    }

    private  void  cam_picker(){
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Pic ");
        values.put(MediaStore.Images.Media.DESCRIPTION,"HandShopping");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent,IMG_PICK_CAM_CODE);
    }

    private  void  gallery_picker(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_PICK_GALLERY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == IMG_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);

            }  else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    File file1=new File(result.getUri().getPath());

                    Uri result_uri = Uri.fromFile(file1);

                    try {
                        bitmap =new Compressor(this).compressToBitmap(file1);
                        imageView.setImageURI(result_uri);
                        imageView.setVisibility(View.VISIBLE);
                        uploadBtn.setEnabled(true);
                        uploadBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_up_btn));
                        titleEditxt.setVisibility(View.VISIBLE);
                        priceEditxt.setVisibility(View.VISIBLE);
                        cambtn.setEnabled(false);
                        cambtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_cam_btn_disable));
                        galbtn.setEnabled(false);
                        galbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_gal_btn_disable));
                        titleEditxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (!hasFocus){
                                    if (activity!=null) {
                                        activity.getWindow()
                                                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                    }
                                }else {
                                    if (activity!=null) {
                                        activity.getWindow()
                                                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                    }

                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.

            case  MY_PERMISSIONS_REQUEST_CAMERA:

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }




    private  void  uploadImage(){
        class  UploadImage extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                String Image=imageToString();

                @SuppressLint("WrongThread") String Title=titleEditxt.getText().toString();
                @SuppressLint("WrongThread") String Pricestring=priceEditxt.getText().toString();

                   int   Price=Integer.parseInt(Pricestring);

                HashMap<String ,Object> upload_map=new HashMap<>();
                upload_map.put("title",Title);
                upload_map.put("images",Image);
                upload_map.put("price",String.valueOf(Price));
                upload_map.put("user_name",r_user_name());
                upload_map.put("orderable_status", orderable_status);



                ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
                Call<ImageClass> call=apiInterface.uploadImage(upload_map);

                call.enqueue(new Callback<ImageClass>() {
                    @Override
                    public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                        if (response.body().getResponse().equals("uploaded")) {
                            ImageClass imageClass = response.body();
                            Toast.makeText(MainActivity.this, " Image uploaded successfully ", Toast.LENGTH_SHORT).show();
                            cambtn.setEnabled(true);
                            cambtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_cam_btn));
                            galbtn.setEnabled(true);
                            galbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_gal_btn));
                            uploadBtn.setEnabled(false);
                           uploadBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_up_btn_disable));
                            priceEditxt.setText("");
                            priceEditxt.setVisibility(View.GONE);
                            titleEditxt.setText("");
                            titleEditxt.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);
                        }else if(response.body().getResponse().contains("Exist")) {
                            Toast.makeText(MainActivity.this, "Already Exists This Name", Toast.LENGTH_SHORT).show();
                        }else if(response.body().getResponse().contains("Invalid")) {
                            Toast.makeText(MainActivity.this, "Invalid title use letters and numbers ", Toast.LENGTH_SHORT).show();
                        }else if(response.body().getResponse().contains("Invalid_high")){
                        Toast.makeText(MainActivity.this, "Maximum length of title is 50", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<ImageClass> call, Throwable t) {
                        if (t instanceof NetworkErrorException){
                            Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "An Error Occurred , " +
                                    "Please try Again "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                return  null;
            }
        }
        new UploadImage().execute();




    }
    private  String imageToString(){
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
                byte[] imgByte=byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

    private  void  getting_Storage_permission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    private  void  getting_camera_permission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }





}
