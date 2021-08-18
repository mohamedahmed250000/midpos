package com.example.midpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Purchase_List extends AppCompatActivity {

    ListView list_user;
    TextView pname,pprice,ptprice;
    EditText pcode,pquntity;
    String proname,procode,proquntity,proprice,prodect_total_price;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__list);
        pname = findViewById(R.id.namep);
        pcode = findViewById(R.id.codep);
        pquntity = findViewById(R.id.quntityp);
        pprice = findViewById(R.id.pricep);
        ptprice=findViewById(R.id.myphoto);
        proname = pname.getText().toString();
        procode = pcode.getText().toString();
        proquntity = pquntity.getText().toString();
        proprice = pprice.getText().toString();
        prodect_total_price = ptprice.getText().toString();
        add = findViewById( R.id.add);
        list_user = findViewById(R.id.list_user);
        final ArrayList<Prodect> prodects = new ArrayList<>();
        final ListAdaptor myadaptor = new ListAdaptor(this,R.layout.item_user,prodects);
        list_user.setAdapter(myadaptor);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodects.add(new Prodect(proname,procode,proquntity,proprice,prodect_total_price));
                myadaptor.notifyDataSetChanged();
            }
        });
    }


}
