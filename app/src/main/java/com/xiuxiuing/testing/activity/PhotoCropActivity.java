package com.xiuxiuing.testing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.photocropper.BitmapUtil;
import com.xiuxiuing.testing.photocropper.CropHandler;
import com.xiuxiuing.testing.photocropper.CropHelper;
import com.xiuxiuing.testing.photocropper.CropParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with Android Studio. User: ryan@xisue.com Date: 10/3/14 Time: 11:44 AM Desc:
 * PhotoCropActivity
 */
public class PhotoCropActivity extends AppCompatActivity implements CropHandler, View.OnClickListener {

    public static final String TAG = "PhotoCropActivity";

    ImageView mImageView;
    private Uri imageUri;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
    CropParams mCropParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mCropParams = new CropParams(this);
        mImageView = (ImageView) findViewById(R.id.image);

        imageUri = Uri.parse(IMAGE_FILE_LOCATION);

        findViewById(R.id.bt_crop_capture).setOnClickListener(this);
        findViewById(R.id.bt_crop_gallery).setOnClickListener(this);
        findViewById(R.id.bt_capture).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCropParams.refreshUri();
        switch (v.getId()) {
            case R.id.bt_crop_capture: {
                mCropParams.enable = true;
                mCropParams.compress = false;
                Intent intent = CropHelper.buildCameraIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
                break;
            case R.id.bt_crop_gallery: {
                // mCropParams.enable = false;
                // mCropParams.compress = false;
                // mCropParams.crop = "true";
                // mCropParams.aspectX = 5;
                // mCropParams.aspectY = 1;
                // mCropParams.outputX = 480;
                // mCropParams.outputY = 85;
                // mCropParams.scale = true;
                // mCropParams.returnData = true;
                // Intent intent = CropHelper.buildGalleryIntent(mCropParams);


                // Intent intent = new Intent("com.android.camera.action.CROP");
                // intent.setDataAndType(mCropParams.uri, mCropParams.type);
                // intent.putExtra("crop", "true");// 发送裁剪信号
                // intent.putExtra("outputX", 480);// 裁剪区的宽
                // intent.putExtra("outputY", 85);// 裁剪区的高
                // intent.putExtra("aspectX", 5);// X方向上的比例
                // intent.putExtra("aspectY", 1);// Y方向上的比例
                // intent.putExtra("scale", true);// 是否保留比例
                // // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//直接输出文件
                // intent.putExtra("return-data", true); // 是否返回数据
                // // intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                // intent.putExtra("noFaceDetection", true); // 关闭人脸检测



                // startActivityForResult(intent, CropHelper.REQUEST_CROP);
                // Intent.ACTION_GET_CONTENT
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent2, 1);


                // // 这段代码使用ACTION_GET_CONTENT和ACTION_PICK效果相同
                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                // // Intent intent = new Intent(Intent.ACTION_PICK, null);
                //
                // // 如果使用com.android.camera.action.CROP 则直接打开裁剪照片的activity 那么可以用自己的图片浏览器选择图片
                // 传入参数并使用之
                // // Intent intent = new Intent("com.android.camera.action.CROP");
                //
                // // 如果不设置type，则 ACTION_GET_CONTENT 会弹出异常FATAL EXCEPTION:main
                // android.content.ActivityNotFoundException
                // // 而 ACTION_PICK 会弹出可用程序列表 但没有打开图片相关的程序（在我的两个设备上是这样）
                // intent.setType("image/*");
                //
                // // 设置在开启的Intent中设置显示的view可裁剪
                // // 这段代码里设置成false也能裁剪啊。。。这是为什么？懂的给我讲讲了
                // // 这段注释掉就不会跳转到裁剪的activity
                // intent.putExtra("crop", "true");
                //
                // // 设置x,y的比例，截图方框就按照这个比例来截 若设置为0,0，或者不设置 则自由比例截图
                // intent.putExtra("aspectX", 2);
                // intent.putExtra("aspectY", 1);
                //
                // // 裁剪区的宽和高 其实就是裁剪后的显示区域 若裁剪的比例不是显示的比例，则自动压缩图片填满显示区域。若设置为0,0 就不显示。若不设置，则按原始大小显示
                // intent.putExtra("outputX", 200);
                // intent.putExtra("outputY", 100);
                //
                // // 不知道有啥用。。可能会保存一个比例值 需要相关文档啊
                // intent.putExtra("scale", true);
                //
                // // true的话直接返回bitmap，可能会很占内存 不建议
                // intent.putExtra("return-data", false);
                // // 上面设为false的时候将MediaStore.EXTRA_OUTPUT即"output"关联一个Uri
                //
                // intent.putExtra("output", imageUri);
                // // 看参数即可知道是输出格式
                // intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                // // 面部识别 这里用不上
                // intent.putExtra("noFaceDetection", false);
                //
                // // 想从Activity中获得返回数据，在启动Activity时候使用startActivityForResult方法
                // // 1为请求代码，可以是任意值，个人感觉用资源id会比较清楚，而且不会重复 比如当前控件的R.id.button
                // startActivityForResult(intent, 4);


            }
                break;
            case R.id.bt_capture: {
                mCropParams.enable = false;
                mCropParams.compress = true;
                Intent intent = CropHelper.buildCameraIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
            }
                break;
            case R.id.bt_gallery: {
                mCropParams.enable = false;
                mCropParams.compress = true;
                Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_PICK);
            }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // CropHelper.handleResult(this, requestCode, resultCode, data);
        // if (requestCode == 1) {
        // Log.e(TAG, "");

        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }

                break;
            // 如果是调用相机拍照时
            case 2:

                File temp = new File(getExternalFilesDir(null) + "/img.jpg");
                startPhotoZoom(Uri.fromFile(temp));



                break;
            // 取得裁剪后的图片
            case 3:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case 4:
                if (imageUri != null) {
                    Bitmap bitmap = decodeUriAsBitmap(imageUri);
                    // 把解析到的位图显示出来
                    mImageView.setImageBitmap(bitmap);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        // Original or Cropped uri
        Log.d(TAG, "onPhotoCropped Uri in path: " + uri.getPath());
        if (!mCropParams.compress)
            mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCompressed(Uri uri) {
        // Compressed uri
        Log.d(TAG, "onCompressed Uri in path: " + uri.getPath());
        mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "onCancel canceled!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, "onFailed failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        Log.d(TAG, "handleIntent Uri in path: ");
        startActivityForResult(intent, requestCode);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 85);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 4);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            mImageView.setImageBitmap(photo);
            // BitmapUtil bu = new BitmapUtil();
            //
            // ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            // byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来
            // String data=new String(Base64Coder.encodeLines(b));
            // System.out.println("data+++++++++"+data);
            // head_string=data;
            /*
             * FileUtils w=new FileUtils(); w.writeText(getExternalFilesDir(null) + "/12.txt",
             * data);
             */

            /*
             * Bitmap dBitmap = BitmapFactory.decodeFile(tp); Drawable drawable = new
             * BitmapDrawable(dBitmap);
             */


        }
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
