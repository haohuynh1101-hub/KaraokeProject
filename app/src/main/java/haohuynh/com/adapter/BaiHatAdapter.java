package haohuynh.com.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import haohuynh.com.karaokeproject.MainActivity;
import haohuynh.com.karaokeproject.R;
import haohuynh.com.models.BaiHat;

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    Activity context;
    int resource;
    TextView txtMa,txtTen,txtCasi;
    ImageView imgLike,imgDislike;
    public BaiHatAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context= (Activity) context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View  view=inflater.inflate(this.resource,null);
        txtMa=view.findViewById(R.id.txtMa);
        txtTen=view.findViewById(R.id.txtTenBaiHat);
        txtCasi=view.findViewById(R.id.txtCaSi);
        imgLike=view.findViewById(R.id.imgLike);
        imgDislike=view.findViewById(R.id.imgDislike);
        final BaiHat baiHat=new BaiHat();
        txtCasi.setText(baiHat.getCaSi());
        if(baiHat.getThich()==0){
            imgLike.setVisibility(View.INVISIBLE);
            imgDislike.setVisibility(View.VISIBLE);
        }else {
            imgLike.setVisibility(View.VISIBLE);
            imgDislike.setVisibility(View.INVISIBLE);
        }
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgLike.setVisibility(View.INVISIBLE);
                imgDislike.setVisibility(View.VISIBLE);
                xuLyLike(baiHat);
            }
        });
        imgDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDislike.setVisibility(View.INVISIBLE);
                imgLike.setVisibility(View.VISIBLE);
                xuLyDislike(baiHat);
            }
        });
        return view;
    }

    private void xuLyDislike(BaiHat baiHat) {
        ContentValues values=new ContentValues();
        values.put("YEUTHICH",1);
        int kq= MainActivity.database.update(MainActivity.TableName_BaiHat,
                values,"MABH=?",new String[]{baiHat.getMa()});
        if(kq>0){
            Toast.makeText(context,"Đã xóa bài hát khỏi danh sách yêu thích",Toast.LENGTH_LONG).show();
        }
    }

    private void xuLyLike(BaiHat baiHat) {
        ContentValues values=new ContentValues();
        values.put("YEUTHICH",0);
        int kq= MainActivity.database.update(MainActivity.TableName_BaiHat,
                values,"MABH=?",new String[]{baiHat.getMa()});
        if(kq>0){
            Toast.makeText(context,"Đã thêm bài hát vào danh sách yêu thích",Toast.LENGTH_LONG).show();
        }
    }

}
