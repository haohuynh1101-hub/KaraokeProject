package haohuynh.com.karaokeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import haohuynh.com.adapter.BaiHatAdapter;
import haohuynh.com.karaokeproject.databinding.ActivityMainBinding;
import haohuynh.com.models.BaiHat;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static String DATABASE_NAME="Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    public  static String TableName_BaiHat="ArirangSongList";
    BaiHatAdapter adapterAll;
    ListView lvAllSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addControls();
        processCopy();
        loadAllSongs();
    }

    private void loadAllSongs() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        //trong cursor đối số 1 là câu truy vấn
        //đối số 2 là điều kiện lọc, nếu lấy toàn bộ thì khai báo null
        Cursor cursor=database.rawQuery("select * from ArirangSongList ",null);
        adapterAll.clear();
        while (cursor.moveToNext()){
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String caSi=cursor.getString(3);
            int thich=cursor.getInt(5);
            BaiHat baiHat=new BaiHat(ma,ten,caSi,thich);
            adapterAll.add(baiHat);
        }
        cursor.close();
    }

    private void addControls() {
        binding.tabHost.setup();
        TabHost.TabSpec tab1=binding.tabHost.newTabSpec("t1");
        //thiết lập nội dung layout
        tab1.setContent(R.id.tab1);
        //thiết lập tiêu đề
        tab1.setIndicator("All Songs");
        binding.tabHost.addTab(tab1);
        TabHost.TabSpec tab2=binding.tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Love Songs");
        binding.tabHost.addTab(tab2);
        lvAllSongs=findViewById(R.id.lvAllSongs);
        adapterAll=new BaiHatAdapter(MainActivity.this,R.layout.item);
        lvAllSongs.setAdapter(adapterAll);
    }
    private void processCopy()
    {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this,
                        "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {

        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput=getAssets().open(DATABASE_NAME);
            String outFileName=getDatabasePath();
            File file=new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if (!file.exists()){
                // tạo mới thư mục tên DATABASE
                file.mkdir();
            }
            OutputStream outputStream=new FileOutputStream(outFileName);
            byte []buffer= new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            myInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
