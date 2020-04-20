package com.example.hand_shoping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText titleEditxt,priceEditxt;
    private ImageView imageView;
    private Button cambtn,galbtn,uploadBtn;
    private  LogIn_Activity logIn_activity;
    private Activity activity;
    public SharedPreferences sharedPreferences;
    public static  MainActivity mainActivity;
    String cam_permission[];
    String storage_permission[];
    private Uri image_uri;
    private Bitmap bitmap;

    private  static  final int   CAM_REQ_CODE=1;
    private  static  final int  STORAGE_REQ_CODE=2;
    private  static final  int IMG_PICK_CAM_CODE=3;
    private  static  final int IMG_PICK_GALLERY_CODE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("myFile", Context.MODE_PRIVATE);
        mainActivity=this;
        if (!rlogstatus()) {
            startActivity(new Intent(MainActivity.this, LogIn_Activity.class));
            finish();
        }
        textView = findViewById(R.id.mainTextID);
        imageView=findViewById(R.id.mainImgID);
        cambtn=findViewById(R.id.camID);
        galbtn=findViewById(R.id.galaryID);
        titleEditxt=findViewById(R.id.titleeditID);
        priceEditxt=findViewById(R.id.priceeditID);
        uploadBtn=findViewById(R.id.uploadbtnID);
        textView.setText(rName());
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(titleEditxt.getText().toString())|| TextUtils.isEmpty(priceEditxt.getText().toString())){
                    Toast.makeText(MainActivity.this, "Title and price  must needed", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                }


            }
        });
        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cam_permission_checker()){
                    cam_permission_request();
                }else {
                    cam_picker();
                }

            }
        });

        galbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!storage_permission_checker()){
                    storage_permission_request();
                }else {
                    gallery_picker();
                }

            }
        });
        logIn_activity = new LogIn_Activity();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case  R.id.logOutID:
                wlogStatus(false);
                wName("user","user_name");
                startActivity(new Intent(MainActivity.this, LogIn_Activity.class));
                finish();
                return true;
            case R.id.myAccountID:
                Intent intent=new Intent(MainActivity.this,AllContents.class);
                startActivity(intent);
                return  true;

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


    private  boolean cam_permission_checker(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return  result && result1;

    }

    private  boolean storage_permission_checker(){
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return  result1;

    }

    private void cam_permission_request(){
        ActivityCompat.requestPermissions(this,cam_permission,CAM_REQ_CODE);
    }

    private  void  storage_permission_request(){
        ActivityCompat.requestPermissions(this,storage_permission,STORAGE_REQ_CODE);
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
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMG_PICK_GALLERY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==IMG_PICK_GALLERY_CODE){
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
            if(requestCode==IMG_PICK_CAM_CODE){
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);

            }
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri result_uri=result.getUri();
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),result_uri);
                    imageView.setImageURI(result_uri);
                    imageView.setVisibility(View.VISIBLE);
                    uploadBtn.setEnabled(true);
                    titleEditxt.setVisibility(View.VISIBLE);
                    priceEditxt.setVisibility(View.VISIBLE);
                    cambtn.setEnabled(false);
                    galbtn.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  CAM_REQ_CODE:
                if (grantResults.length>0){
                    boolean cam_accepted=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    boolean wrt_strg_acptd=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    if (cam_accepted && wrt_strg_acptd){
                        cam_picker();
                    }else {
                        Toast.makeText(this, "Permission denied..", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            case STORAGE_REQ_CODE:
                if (grantResults.length>0){
                    boolean wrt_strg_acptd=grantResults[0]==
                            PackageManager.PERMISSION_GRANTED;
                    if (wrt_strg_acptd){
                        Toast.makeText(this, "Permission denied..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private  void  uploadImage(){
        String Image=imageToString();
        String Title=titleEditxt.getText().toString();
        String Pricestring=priceEditxt.getText().toString();
        int  Price=Integer.parseInt(Pricestring);
        ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClass> call=apiInterface.uploadImage(Title,Image,Price,r_user_name());

        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass imageClass=response.body();
                Toast.makeText(MainActivity.this, "Server Response : "+imageClass.getResponse(), Toast.LENGTH_SHORT).show();
                cambtn.setEnabled(true);
                galbtn.setEnabled(true);
                uploadBtn.setEnabled(false);
                priceEditxt.setText("");
                titleEditxt.setText("");

            }


            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {

            }
        });



    }
    private  String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

}
