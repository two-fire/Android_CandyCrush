package com.xample.candycrush;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.xample.candycrush.activity.AboutActivity;
import com.xample.candycrush.activity.HelpActivity;
import com.xample.candycrush.activity.VolumeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    int[] candies = {
            R.drawable.bluecandy,
            R.drawable.greencandy,
            R.drawable.orangecandy,
            R.drawable.purplecandy,
            R.drawable.yellowcandy,
            R.drawable.redcandy,
    };
    int widthOfBlock, noOfBlocks = 8, widthOfScreen;
    ArrayList<ImageView> candy = new ArrayList<>();
    int candyToBeDragged,candyToBeReplaced;
    int notCandy = R.drawable.transparent;
    Handler mHandler;
    int interval = 200;
    TextView scoreResult;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        scoreResult = findViewById(R.id.score);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        widthOfBlock = widthOfScreen / noOfBlocks;
        createBoard();
        for(final ImageView imageView : candy) {
            imageView.setOnTouchListener(new OnSwipeListener(this)
            {
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
//                    Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - 1;
                    candyInterchange();
                }
                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
//                    Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + 1;
                    candyInterchange();
                }
                @Override
                public void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - noOfBlocks;
                    candyInterchange();
                }
                @Override
                public void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + noOfBlocks;
                    candyInterchange();
                }

            });
        }
        mHandler = new Handler();
        startRepeat();

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreResult.setText("0");
                score = 0;
            }
        });
    }

    private void checkRawForThree() {
        for (int i = 0; i < 62; i++) {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int)candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)) {
                int x = i;
                if ((int)candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int)candy.get(x++).getTag() == chosedCandy &&
                        (int)candy.get(x).getTag() == chosedCandy) {
                    score = score + 3;
                    scoreResult.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                }
            }
        }
        moveDownCandies();
    }

    private void checkColumnForThree() {
        for (int i = 0; i < 47; i++) {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int)candy.get(i).getTag() == notCandy;
            int x = i;
            if ((int)candy.get(x).getTag() == chosedCandy && !isBlank &&
                    (int)candy.get(x+noOfBlocks).getTag() == chosedCandy &&
                    (int)candy.get(x+2*noOfBlocks).getTag() == chosedCandy) {
                score = score + 3;
                scoreResult.setText(String.valueOf(score));
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
            }
        }
        moveDownCandies();
    }

    private void moveDownCandies() {
        Integer[] firstRow = {0,1,2,3,4,5,6,7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--) {
            if ((int)candy.get(i + noOfBlocks).getTag() == notCandy) {
                candy.get(i + noOfBlocks).setImageResource((int)candy.get(i).getTag());
                candy.get(i + noOfBlocks).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                if (list.contains(i) && (int)candy.get(i).getTag() == notCandy) {
                    int randomColor = (int)Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if ((int)candy.get(i).getTag() == notCandy) {
                int randomColor = (int)Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);
            }
        }

    }
    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkRawForThree();
                checkColumnForThree();
                moveDownCandies();
            } finally {
                mHandler.postDelayed(repeatChecker, interval);
            }
        }
    };
    void startRepeat() {
        repeatChecker.run();
    }
    private void candyInterchange() {
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background1 = (int) candy.get(candyToBeDragged).getTag();
        candy.get(candyToBeDragged).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background1);
        candy.get(candyToBeDragged).setTag(background);
        candy.get(candyToBeReplaced).setTag(background1);
    }

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.board);
        // 设置8x8的网格块数
        gridLayout.setRowCount(noOfBlocks);
        gridLayout.setColumnCount(noOfBlocks);
        // 正方形布局
        gridLayout.getLayoutParams().width = widthOfScreen;
        gridLayout.getLayoutParams().height = widthOfScreen;
        for (int i = 0; i < noOfBlocks * noOfBlocks; i++) {
            ImageView imageView = new ImageView(this);

            imageView.setId(i);
            imageView.setLayoutParams(new
                    android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock));
            // 最大的图像视图的高度和宽带
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);
            // 产生一个介于0和candies.length的长度的随机索引
            int randonCandy = (int) Math.floor(Math.random() * candies.length);
            // 使用随机索引来随机设置图像资源
            imageView.setImageResource(candies[randonCandy]);
            // 将标记获取为整数，然后将其存储在整数中
            imageView.setTag(candies[randonCandy]);
            candy.add(imageView);
            // 添加视图图像显示
            gridLayout.addView(imageView);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            // 设置
            Intent in = new Intent(this, VolumeActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_help) {
            //帮助页面
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            // 关于
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            // 分享
            // 生成分享的文字信息
            String s = String.valueOf(scoreResult);

            // 将文字信息分享到其他应用
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_TEXT,s);
            startActivity(in);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}