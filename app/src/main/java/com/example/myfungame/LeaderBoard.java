package com.example.myfungame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

interface NetResponse{
    void netResult(Integer code, JSONArray json);
}

public class LeaderBoard extends AppCompatActivity implements NetResponse  {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<UserInfo>userRank;
    String updateString;
    Button webButton;
    NetTask netTask;
    LeaderBoard handle;
    URL url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        Button clearButton = findViewById(R.id.clearButton);
        webButton = findViewById(R.id.webButton);
        //userRank = new ArrayList<>();

        loadData();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.scrollToPosition(userRank.size()-1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecycleAdapter(userRank);
        recyclerView.setAdapter(myAdapter);

         handle = this;
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked the web button");

                String tempString = "http://cs.binghamton.edu/~pmadden/courses/441score/postscore.php?player="+userRank.get(userRank.size()-1).getName()+"&game=AppleGo&score=";
                int temp = userRank.get(userRank.size()-1).getPoint();
                String request = tempString.concat(Integer.toString(temp));
                try {
                    url = new URL(request);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                netTask = new NetTask(request
, request, handle);

                netTask.execute((Void) null);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRank.clear();
                myAdapter.notifyDataSetChanged();
            }
        });


        if (getIntent().hasExtra("userName")) {
            System.out.println("new user"+ userRank.size());
            String userName = getIntent().getExtras().getString("userName");


            //Creating some UserInfos for test
            for(int i = 0;i<20;i++) {
                UserInfo info = new UserInfo(userName+i, "Apple go"+i, (0+i)%5);
                userRank = showTopScore(info);
                myAdapter.notifyDataSetChanged();
            }






        }

    }
    public void notifyDataChanged(){
        myAdapter.notifyDataSetChanged();
    }


    public void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userRank);
        editor.putString("Input list", json);
        editor.apply();
    }

    public ArrayList<UserInfo> showTopScore (UserInfo temp){

        if (userRank.size()>20){
            userRank.remove(20);
            userRank.add(temp);
        }
        else{
            userRank.add(temp);
        }
        LeaderBoardShorter leaderBoardShorter = new LeaderBoardShorter(userRank);
        return leaderBoardShorter.getSortedLeaderBoard();



    }



    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String  json = sharedPreferences.getString("Input list", null);
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        userRank = gson.fromJson(json, type);

        if (userRank == null) {
            userRank = new ArrayList<>();
        }
    }
    public void netResult(Integer code, JSONArray json)
    {
        System.out.println("Got a result from the web");
        updateString = "";

        for (int i = 0; i < json.length(); ++i)
        {
            System.out.println("Looping");
            try {
                JSONObject item = json.getJSONObject(i);

                if (item.getString("result") != null) {
                    System.out.println("Found a match");
                    System.out.println(item.getString("result"));
                    updateString = item.getString("result");


                }
            }
            catch (JSONException e)
            {
                updateString = "JSON Error!";
            }

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    System.out.println("String connet is now : "+updateString);

                }
            });
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public class NetTask extends AsyncTask<Void, Void, Boolean> {
        private final String urlString;
        private final String reqString;
        private NetResponse changeListener;

        NetTask(String url, String request, NetResponse responseListener) {
            urlString = url; reqString = request; changeListener = responseListener;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                System.out.println("JSON Query: " + reqString);
                // JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
                // JSONObject json = readJsonFromUrl("https://cnn.com");
                // System.out.println(reqString);
                JSONArray json = readJsonFromUrl(reqString);
                System.out.println("Finished getting json.");
                if (json != null)
                    System.out.println(json.toString());

                if (changeListener != null)
                    changeListener.netResult(0, json);

                //System.out.println("Notify that JSON has come in");
                // if (noteConnector != null)
                //    noteConnector.ncnotify(0, "");

            } catch (IOException e) {
                System.out.println("IO exception");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(1, null);
            } catch (JSONException e) {
                System.out.println("JSON Didn't work");
                //System.out.println(e);
                if (changeListener != null)
                    changeListener.netResult(2, null);
            }
            return true;
        }

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            System.out.println("Read from the URL");
            System.out.println(sb.toString());
            System.out.println("Going to try to turn it into json");
            return sb.toString();
        }

        public JSONArray readJsonFromUrl(String request) throws IOException, JSONException {
            URL nurl = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) nurl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.out.println("Network request to " + urlString + " with request " + reqString);
            OutputStream urlout = connection.getOutputStream();
            System.out.println("here");

            //String s = "id=3452&second=fjfjfjfj";
            urlout.write(request.getBytes());
            urlout.close();
            InputStream is = connection.getInputStream();

            System.out.println("Waiting for network stream");
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String jsonText = readAll(rd);
                System.out.println("JSON is " + jsonText);

                JSONArray jarray = new JSONArray(jsonText);


                System.out.println("Got the object");
                return jarray;
            } finally {
                is.close();
                // System.out.println("Did not get the object.");
            }


        }
    }
}
