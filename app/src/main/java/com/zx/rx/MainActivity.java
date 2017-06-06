package com.zx.rx;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private TextView tvText;
    private ImageView tvGo;
    private TextView tvCancel;
    private EditText et;
    private Button button;
    private TextToSpeech textToSpeech;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = (TextView) findViewById(R.id.tv_text);
        tvGo = (ImageView) findViewById(R.id.request);
        tvCancel = (TextView) findViewById(R.id.cancel);
        button = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv_share);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,iv,"share").toBundle());
            }
        });
        textToSpeech = new TextToSpeech(this,this);
        button.setOnClickListener(this);
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRetrofit();
                startAnimation();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.cancel();
            }
        });
    }

    private void startAnimation() {
        Drawable drawable = tvGo.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }

    Call<User> call;
    private void initRetrofit() {

        DataService dataService = new DataService();
        dataService.getDataApi().getString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this,"回调",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(User userResult) {
                        tvText.setText(userResult.getId()+"");
                        Toast.makeText(MainActivity.this,"onNext",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            textToSpeech.setPitch(0.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.speak(et.getText().toString(),
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}
