package com.example.comehere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private String dburl = "https://comehere-cd02d-default-rtdb.firebaseio.com/";
    private RecyclerView articleRecyclerView;
    private RecyclerView.Adapter articleAdapter;
    private RecyclerView.LayoutManager articleLayoutManager;
    private ArrayList<ArticleData> articleList;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get search data from HomeActivity
        Intent intent = getIntent();
        searchData = intent.getExtras().getString("productName");

        // search title
        TextView sTitle = (TextView)findViewById(R.id.resultText);
        sTitle.setText("\""+searchData+"\" 검색결과" );

        // RecyclerView setting
        articleRecyclerView = findViewById(R.id.rv_article_list);
        articleRecyclerView.setHasFixedSize(true);
        articleLayoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(articleLayoutManager);
        articleList = new ArrayList<>();

        // DB setting
        database = FirebaseDatabase.getInstance(dburl);
        reference = database.getReference("Article");

        // find the search data
        /*reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArticleData article = snapshot.getValue(ArticleData.class);

                    if (article.getProductName().equals(searchData)) {
                        articleList.add(article);
                    }
                }
                callBack(articleList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void callBack(ArrayList<ArticleData> atc) {
        //articleAdapter = new ArticleListAdapter(atc, this);
        articleRecyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();
    }
}
