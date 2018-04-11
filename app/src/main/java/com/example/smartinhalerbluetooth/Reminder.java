package com.example.smartinhalerbluetooth;

/**
 * Created by Rahul on 11-04-2018.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Date;



public class Reminder extends AppCompatActivity {



    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);



    CardView card1,card2,card3,card4,card5;
    Button delete1,delete2,delete3,delete4,delete5;
    TextView alarm_time1,alarm_time2,alarm_time3,alarm_time4,alarm_time5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reminder);
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        card1 = (CardView)findViewById(R.id.cardview1);
        card2 = (CardView)findViewById(R.id.cardview2);
        card3 = (CardView)findViewById(R.id.cardview3);
        card4 = (CardView)findViewById(R.id.cardview4);
        card5 = (CardView)findViewById(R.id.cardview5);
        delete1 = (Button)findViewById(R.id.deleteButton1);
        delete2 = (Button)findViewById(R.id.deleteButton2);
        delete3 = (Button)findViewById(R.id.deleteButton3);
        delete4 = (Button)findViewById(R.id.deleteButton4);
        delete5 = (Button)findViewById(R.id.deleteButton5);
        alarm_time1 = (TextView)findViewById(R.id.alarm_time1);
        alarm_time2 = (TextView)findViewById(R.id.alarm_time2);
        alarm_time3 = (TextView)findViewById(R.id.alarm_time3);
        alarm_time4 = (TextView)findViewById(R.id.alarm_time4);
        alarm_time5 = (TextView)findViewById(R.id.alarm_time5);




        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog alarmtime = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker timePicker_view, int selected_hour, int selected_minute) {

                        String time = selected_hour+":"+selected_minute;
                        Date date =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            date = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = toshow.format(date);
                        Calendar mcalendar = Calendar.getInstance();
                        mcalendar.set(mcalendar.get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH),
                                timePicker_view.getHour(), timePicker_view.getMinute(), 0);
                        setAlarm(mcalendar.getTimeInMillis(),1);

                        alarm_time1.setText("Time " + formattedTime);
                        delete1.setVisibility(View.VISIBLE);
                    }

                }, hour, minute,false);
                alarmtime.setTitle("Inhaler Timing:");
                alarmtime.show();


            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Reminder.this);
                alert.setTitle("Smart Inhaler:");
                alert.setMessage("Do You Really Want To Delete This Reminder ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getting the alarm manager
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        //creating a new intent specifying the broadcast receiver
                        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);
                        // Log.d("position",position+"deleted");

                        int pendingIntentIdAlarm1 =1;
                        PendingIntent pi = PendingIntent.getBroadcast(Reminder.this,pendingIntentIdAlarm1, toBroad, 0);
                        pi.cancel();
                        am.cancel(pi);
                        delete1.setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.coordinator), "Reminder Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        alarm_time1.setText("Add Your Timing Here");
                        dialog.dismiss();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });


        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog alarmtime = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker timePicker_view, int selected_hour, int selected_minute) {

                        String time = selected_hour+":"+selected_minute;
                        Date date =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            date = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = toshow.format(date);
                        Calendar mcalendar2 = Calendar.getInstance();
                        mcalendar2.set(mcalendar2.get(Calendar.YEAR), mcalendar2.get(Calendar.MONTH), mcalendar2.get(Calendar.DAY_OF_MONTH),
                                timePicker_view.getHour(), timePicker_view.getMinute(), 0);
                        setAlarm(mcalendar2.getTimeInMillis(),2);

                        // AlarmList temp = new AlarmList();
                        //temp.setCal(mcalendar);
                        //alarmLists.add(temp);

                        alarm_time2.setText("Time " + formattedTime);
                        delete2.setVisibility(View.VISIBLE);
                    }

                }, hour, minute,false);
                alarmtime.setTitle("Inhaler Timing:");
                alarmtime.show();


            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Reminder.this);
                alert.setTitle("Smart Inhaler:");
                alert.setMessage("Do You Really Want To Delete This Reminder ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getting the alarm manager
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        //creating a new intent specifying the broadcast receiver
                        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);
                        // Log.d("position",position+"deleted");

                        int pendingIntentIdAlarm1 =2;
                        PendingIntent pi = PendingIntent.getBroadcast(Reminder.this,pendingIntentIdAlarm1, toBroad, 0);
                        pi.cancel();
                        am.cancel(pi);
                        delete2.setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.coordinator), "Reminder Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        alarm_time2.setText("Add Your Timing Here");
                        dialog.dismiss();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog alarmtime = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker timePicker_view, int selected_hour, int selected_minute) {

                        String time = selected_hour+":"+selected_minute;
                        Date date =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            date = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = toshow.format(date);
                        Calendar mcalendar = Calendar.getInstance();
                        mcalendar.set(mcalendar.get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH),
                                timePicker_view.getHour(), timePicker_view.getMinute(), 0);
                        setAlarm(mcalendar.getTimeInMillis(),3);

                        // AlarmList temp = new AlarmList();
                        //temp.setCal(mcalendar);
                        //alarmLists.add(temp);

                        alarm_time3.setText("Time " + formattedTime);
                        delete3.setVisibility(View.VISIBLE);
                    }

                }, hour, minute,false);
                alarmtime.setTitle("Inhaler Timing:");
                alarmtime.show();

            }
        });
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Reminder.this);
                alert.setTitle("Smart Inhaler:");
                alert.setMessage("Do You Really Want To Delete This Reminder ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getting the alarm manager
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        //creating a new intent specifying the broadcast receiver
                        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);
                        // Log.d("position",position+"deleted");

                        int pendingIntentIdAlarm1 =3;
                        PendingIntent pi = PendingIntent.getBroadcast(Reminder.this,pendingIntentIdAlarm1, toBroad, 0);
                        pi.cancel();
                        am.cancel(pi);
                        delete3.setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.coordinator), "Reminder Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        alarm_time3.setText("Add Your Timing Here");
                        dialog.dismiss();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog alarmtime = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker timePicker_view, int selected_hour, int selected_minute) {

                        String time = selected_hour+":"+selected_minute;
                        Date date =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            date = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = toshow.format(date);
                        Calendar mcalendar = Calendar.getInstance();
                        mcalendar.set(mcalendar.get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH),
                                timePicker_view.getHour(), timePicker_view.getMinute(), 0);
                        setAlarm(mcalendar.getTimeInMillis(),4);

                        // AlarmList temp = new AlarmList();
                        //temp.setCal(mcalendar);
                        //alarmLists.add(temp);

                        alarm_time4.setText("Time " + formattedTime);
                        delete4.setVisibility(View.VISIBLE);
                    }

                }, hour, minute,false);
                alarmtime.setTitle("Inhaler Timing:");
                alarmtime.show();

            }
        });
        delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Reminder.this);
                alert.setTitle("Smart Inhaler:");
                alert.setMessage("Do You Really Want To Delete This Reminder ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getting the alarm manager
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        //creating a new intent specifying the broadcast receiver
                        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);
                        // Log.d("position",position+"deleted");

                        int pendingIntentIdAlarm1 =4;
                        PendingIntent pi = PendingIntent.getBroadcast(Reminder.this,pendingIntentIdAlarm1, toBroad, 0);
                        pi.cancel();
                        am.cancel(pi);
                        delete4.setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.coordinator), "Reminder Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        alarm_time4.setText("Add Your Timing Here");
                        dialog.dismiss();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });


        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog alarmtime = new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker timePicker_view, int selected_hour, int selected_minute) {

                        String time = selected_hour+":"+selected_minute;
                        Date date =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        try {
                            date = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = toshow.format(date);
                        Calendar mcalendar = Calendar.getInstance();
                        mcalendar.set(mcalendar.get(Calendar.YEAR), mcalendar.get(Calendar.MONTH), mcalendar.get(Calendar.DAY_OF_MONTH),
                                timePicker_view.getHour(), timePicker_view.getMinute(), 0);
                        setAlarm(mcalendar.getTimeInMillis(),5);

                        // AlarmList temp = new AlarmList();
                        //temp.setCal(mcalendar);
                        //alarmLists.add(temp);

                        alarm_time5.setText("Time " + formattedTime);
                        delete5.setVisibility(View.VISIBLE);
                    }

                }, hour, minute,false);
                alarmtime.setTitle("Inhaler Timing:");
                alarmtime.show();

            }
        });
        delete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Reminder.this);
                alert.setTitle("Smart Inhaler:");
                alert.setMessage("Do You Really Want To Delete This Reminder ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getting the alarm manager
                        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        //creating a new intent specifying the broadcast receiver
                        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);
                        // Log.d("position",position+"deleted");

                        int pendingIntentIdAlarm1 =5;
                        PendingIntent pi = PendingIntent.getBroadcast(Reminder.this,pendingIntentIdAlarm1, toBroad, 0);
                        pi.cancel();
                        am.cancel(pi);
                        delete5.setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.coordinator), "Reminder Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        alarm_time5.setText("Add Your Timing Here");
                        dialog.dismiss();


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });





    }

    private void setAlarm(long time,int pendingIntentId) {


        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        //creating a new intent specifying the broadcast receiver
        Intent toBroad = new Intent(Reminder.this, AlarmBreceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, pendingIntentId, toBroad, 0);

        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Snackbar.make(findViewById(R.id.coordinator), "Reminder is set!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        delete1.setVisibility(View.VISIBLE);








    }

}
