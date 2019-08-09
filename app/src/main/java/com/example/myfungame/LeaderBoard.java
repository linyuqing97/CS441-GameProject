package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.net.URL;
import java.util.ArrayList;

interface NetResponse{
    ArrayList<UserInfo> netResult(Integer code, JSONArray json);
}

public class LeaderBoard extends AppCompatActivity implements NetResponse {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<UserInfo> userRank;
    String updateString;
    Button webButton;
    Button getWebButton;
    NetTask netTask;
    LeaderBoard handle;
    URL url;
    String gameNameFromWeb,playerNameFromWeb;
    int scoreFromWeb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        //inital variable
        Button clearButton = findViewById(R.id.clearButton);
        webButton = findViewById(R.id.webButton);
        getWebButton = findViewById(R.id.getWebResult);

        //restore data
        loadData();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.scrollToPosition(userRank.size() - 1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecycleAdapter(userRank);
        recyclerView.setAdapter(myAdapter);

        handle = this;
        webButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("Clicked the web button");
                String userName = userRank.get(userRank.size() - 1).getName();

                String tempString = "http://cs.binghamton.edu/~pmadden/courses/441score/postscore.php?player=".concat(userName).concat("&game=AppleGo&score=");
                int temp = userRank.get(userRank.size() - 1).getPoint();
                String request = tempString.concat(Integer.toString(temp));
                System.out.println(request);


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(request));
                startActivity(browserIntent);


            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRank.clear();
                myAdapter.notifyDataSetChanged();
            }
        });


        if (getIntent().hasExtra("userName") && getIntent().hasExtra("score")) {
            System.out.println("new user" + userRank.size());
            String userName = getIntent().getExtras().getString("userName");
            int score = Integer.parseInt(getIntent().getExtras().getString("score"));
            System.out.println("Score: " + score);
            UserInfo info = new UserInfo(userName, "Apple go", score);
            userRank = showTopScore(info);
            myAdapter.notifyDataSetChanged();

        }

        getWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRank.clear();
                netTask = new NetTask("http://cs.binghamton.edu/~pmadden/courses/441score/getscores.php", null, handle);
                netTask.execute((Void) null);
                System.out.println("Finished Net Tesk");
                for(int i = 0; i < userRank.size();i++){
                    System.out.println(userRank.get(i).getGame()+" "+userRank.get(i).getName());
                }

                myAdapter.notifyDataSetChanged();
            }
        });


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

    public ArrayList<UserInfo> showTopScore(UserInfo temp) {

        if (userRank.size() > 20) {
            userRank.remove(20);
            userRank.add(temp);
        } else {
            userRank.add(temp);
        }
        LeaderBoardShorter leaderBoardShorter = new LeaderBoardShorter(userRank);
        return leaderBoardShorter.getSortedLeaderBoard();


    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Input list", null);
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        userRank = gson.fromJson(json, type);

        if (userRank == null) {
            userRank = new ArrayList<>();
        }
    }

    //
    public ArrayList<UserInfo> netResult(Integer code, JSONArray json) {
        System.out.println("Got a result from the web");
        System.out.println(json.length());
        updateString = "";


        for (int i = 0; i < json.length(); ++i) {
            System.out.println("Looping");
            try {
                JSONObject item = json.getJSONObject(i);

                if (item.getString("player") != null) {
                    System.out.println("Found a match");

//                    System.out.println(item.getString("player"));
//                    updateString = item.getString("player");
                    playerNameFromWeb = item.getString("player");
                    gameNameFromWeb = item.getString("game");
                    scoreFromWeb = Integer.parseInt(item.getString("score"));

                    System.out.println("Player is "+playerNameFromWeb+"The game name is : "+gameNameFromWeb + "score: "+scoreFromWeb);
                    UserInfo info = new UserInfo(playerNameFromWeb, gameNameFromWeb,scoreFromWeb);
                    userRank.add(info);

                }
            } catch (JSONException e) {
                System.out.println(e);
                updateString = "JSON Error!";
            }

            this.runOnUiThread(new Runnable() {
                @Override
               public void run() {

//                    UserInfo info = new UserInfo(playerNameFromWeb, gameNameFromWeb,scoreFromWeb);
//                    userRank.add(info);
                    System.out.println("Updating Leaderboard");
                    myAdapter.notifyDataSetChanged();


                }
            });
        }
       System.out.println("here");
        return userRank;

    }
    class NetTask extends AsyncTask<Void, Void, ArrayList<UserInfo>> {
            private final String urlString;
            private final String reqString;
            private NetResponse changeListener;

            NetTask(String url, String request, NetResponse responseListener) {
                urlString = url;
                reqString = request;
                changeListener = responseListener;
            }

            @Override
            protected ArrayList<UserInfo> doInBackground(Void... params) {
                try {
                    System.out.println("JSON Query: " + reqString);
                    JSONArray json = readJsonFromUrl(reqString);
                    System.out.println("Finished getting json.");
                    if (json != null)
                        System.out.println(json.toString());

                    if (changeListener != null)
                        userRank = changeListener.netResult(0, json);;

                } catch (IOException e) {
                    System.out.println("IO exception");
                    System.out.println(e);
                    if (changeListener != null)
                        changeListener.netResult(1, null);
                } catch (JSONException e) {
                    System.out.println("JSON Didn't work");
                    //System.out.println(e);
                    if (changeListener != null)
                        changeListener.netResult(2, null);
                }

                System.out.println("Finished do in back gorund");
                return userRank;
            }

        //@Override
//        protected void onPostExecute(ArrayList<UserInfo>userInfo) {
//            super.onPostExecute(userInfo);
//            myAdapter.notifyDataSetChanged();
//            for(int i = 0;i<userRank.size();i++) {
//                System.out.println("Player is " + userRank.get(i).getName() + "The game name is : " + userRank.get(i).getGame() + "score: " + scoreFromWeb);
//            }
//
//            System.out.println("On post Execute");
//        }

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
