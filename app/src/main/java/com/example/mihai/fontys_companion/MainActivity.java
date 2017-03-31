package com.example.mihai.fontys_companion;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener {

    private String token;
    private FragmentTransaction fmanager;

    private JSONTask detailsThread;
    private JSONTaskGrades gradesThread;
    private JSONTaskSchedule scheduleThread;

    private AsyncTask lastTask;

    private Fragment homeFragment;
    private Fragment gradesFragment;
    private Fragment scheduleFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    lastTask.cancel(true);
                    changeFragment(homeFragment);
                    detailsThread = new JSONTask();
                    detailsThread.execute(token);
                    lastTask = detailsThread;
                    break;
                case R.id.navigation_grades:
                    lastTask.cancel(true);
                    changeFragment(gradesFragment);
                    gradesThread = new JSONTaskGrades();
                    gradesThread.execute(token);
                    lastTask = gradesThread;
                    break;
                case R.id.navigation_schedule:
                    lastTask.cancel(true);
                    changeFragment(scheduleFragment);
                    scheduleThread = new JSONTaskSchedule();
                    scheduleThread.execute(token);
                    lastTask = scheduleThread;
                    break;
            }
            return true;
        }

    };
    private void changeFragment(Fragment f)
    {
        fmanager = getFragmentManager().beginTransaction();
        fmanager.replace(R.id.content, f);
        fmanager.commit();
    }

    @Override
    public void onFragmentInteraction(String token) {
        this.token=token;
        changeFragment(homeFragment);
        detailsThread = new JSONTask();
        detailsThread.execute(token);
        lastTask = detailsThread;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeActivity();
        gradesFragment = new GradesActivity();
        scheduleFragment = new ScheduleActivity();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public class JSONTask extends AsyncTask<String, Void, MyAccount> {
        TextView tv;
        MyAccount myAccount;

        @Override
        protected MyAccount doInBackground(String... params) {
            try {
                URL url = new URL("https://api.fhict.nl/people/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);

                myAccount = new MyAccount();

                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                JsonReader jsonReader = new JsonReader(isr);

                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String name = jsonReader.nextName();
                        if (name.equals("givenName")) {
                            myAccount.firstName = jsonReader.nextString();
                        } else if (name.equals("surName")) {
                            myAccount.lastName = jsonReader.nextString();
                        } else if (name.equals("mail")) {
                            myAccount.mail = jsonReader.nextString();
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                }

                jsonReader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return myAccount;
        }

        protected void onPostExecute(MyAccount result) {
            tv = (TextView) findViewById(R.id.textView_firstname);
            tv.setText("Firstname: "+myAccount.firstName);

            tv = (TextView) findViewById(R.id.textView2_lastname);
            tv.setText("Lastname: "+myAccount.lastName);

            tv = (TextView) findViewById(R.id.textView3_mail);
            tv.setText("Email: "+myAccount.mail);
        }
    }
    public class JSONTaskGrades extends AsyncTask<String, Void, List<Grades>> {

        @Override
        protected List<Grades> doInBackground(String... params) {
            List<Grades> grades = new ArrayList<Grades>();

            try {
                URL url = new URL("https://api.fhict.nl/grades/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                JsonReader jr = new JsonReader(isr);

                if (jr.peek() == JsonToken.BEGIN_ARRAY) {
                    jr.beginArray();
                    while (jr.hasNext()) {
                        if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                            jr.beginObject();

                            String course = new String();
                            Double grade = new Double(4);
                            String date = new String();
                            Grades grades1;

                            while (jr.hasNext()) {
                                String temporary;
                                temporary = jr.nextName();
                                if (temporary.equals("item")) {
                                    course = jr.nextString();
                                } else if (temporary.equals("grade")) {
                                    grade = Double.parseDouble(jr.nextString());
                                } else if (temporary.equals("date")) {
                                    date = (jr.nextString()).substring(0, 10);
                                } else jr.skipValue();
                            }

                            grades1 = new Grades(date, course, grade);
                            grades.add(grades1);

                            jr.endObject();
                        }
                    }
                    jr.endArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return grades;
        }

        protected void onPostExecute(List<Grades> gradeslist) {
            ListView lv = (ListView) findViewById(R.id.listViewGrades);
            lv.setAdapter(new GradesAdapter(MainActivity.this, gradeslist));
        }
    }
    public class JSONTaskSchedule extends AsyncTask<String, Void, List<Schedule>> {

        @Override
        protected List<Schedule> doInBackground(String... params) {
            ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
            try {
                URL url = new URL("https://api.fhict.nl/schedule/me");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                JsonReader jr = new JsonReader(isr);

                if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                    jr.beginObject();
                    String temporary;
                    while (jr.hasNext()) {
                        temporary = jr.nextName();
                        if (temporary.equals("data") && jr.peek() == JsonToken.BEGIN_ARRAY) {
                            jr.beginArray();
                            while (jr.hasNext()) {
                                if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                                    jr.beginObject();
                                    String room = new String();
                                    String course = new String();
                                    String teacher = new String();
                                    String start = new String();
                                    String end = new String();
                                    while (jr.hasNext()) {
                                        temporary = jr.nextName();
                                        if (temporary.equals("room")) {
                                            room = jr.nextString();
                                        } else if (temporary.equals("subject")) {
                                            course = jr.nextString();
                                        } else if (temporary.equals("teacherAbbreviation")) {
                                            teacher = jr.nextString();
                                        } else if (temporary.equals("start")) {
                                            start = jr.nextString().substring(11, 16);
                                        } else if (temporary.equals("end")) {
                                            end = jr.nextString().substring(11, 16);
                                        } else {
                                            jr.skipValue();
                                        }
                                    }
                                    jr.endObject();
                                    Schedule scheduleTemp = new Schedule(course, room, teacher, start, end);
                                    scheduleList.add(scheduleTemp);
                                }
                            }
                            jr.endArray();
                        }
                        else jr.skipValue();
                    }
                    jr.endObject();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return scheduleList;
        }
        protected void onPostExecute(List<Schedule> scheduleList) {
            ListView lv = (ListView) findViewById(R.id.listViewSchedule);
            lv.setAdapter(new ScheduleAdapter(MainActivity.this, scheduleList));
        }
    }
}
