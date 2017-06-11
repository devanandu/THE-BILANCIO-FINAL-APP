package hitha.ken.deva.abin.bilancio;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class image_pro extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pro);
        Bundle bundle = getIntent().getExtras();
        byte[] blob=bundle.getByteArray("image_url");
        Bitmap bmp=Utils.getImage(blob);
        img=(ImageView)findViewById(R.id.imageView5);
        img.setImageBitmap(bmp);
    }
}
