package xiaofei.library.shellyutilstest;

import android.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import xiaofei.library.shelly.Shelly;
import xiaofei.library.shelly.function.Action1;
import xiaofei.library.shellyutils.AsyncRetrofitTask;
import xiaofei.library.shellyutils.RetrofitDominoConverter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Shelly.<String>createDomino(1)
                .beginTask(new AsyncRetrofitTask<String, String>() {
                    @Override
                    protected Call<String> getCall(String s) {
                        return null;
                    }
                })
                .convert(new RetrofitDominoConverter<String, String>())
                .onResult(new Action1<String>() {
                    @Override
                    public void call(String input) {
                        Toast.makeText(getApplicationContext(), "success" + input, Toast.LENGTH_SHORT).show();
                    }
                })
                .endTask();
    }
}
