package codebind.example.sqliteapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.*;

import utils.DrawableUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mateusz Kornakiewicz on 26.05.2017.
 */

public class CalendarActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView date,time,name;
    ArrayList<Integer> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dates=new ArrayList<>();
        setContentView(R.layout.calendar_activity);
        date=findViewById(R.id.date);
        time=findViewById(R.id.timeset);
        name=findViewById(R.id.name);
        myDb = new DatabaseHelper(this);

        List<EventDay> events = new ArrayList<>();
try {


    Cursor res = myDb.getAllDate();

    StringBuffer buffer = new StringBuffer();
    while (res.moveToNext()) {
        int x = Integer.parseInt(res.getString(1)) - Calendar.getInstance().getTime().getDate();
        Calendar calendar = Calendar.getInstance();
        //  calendar.add(Calendar.DAY_OF_MONTH,x);
        int d = Calendar.DAY_OF_MONTH;
        calendar.add(Calendar.DAY_OF_MONTH, x);
        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, "d")));
        dates.add(Integer.parseInt(res.getString(1)));
    }
}catch (Exception e)
{

}

                            // Show all data
                          //  showMessage("Data",buffer.toString());



        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(1, 11);
        events.add(new EventDay(calendar1, R.drawable.sample_circle));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 10);
        events.add(new EventDay(calendar2, R.drawable.sample_four_icons, Color.parseColor("#228B22")));

        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_MONTH, 7);
        events.add(new EventDay(calendar3, R.drawable.sample_four_icons));

        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_MONTH, 13);
        events.add(new EventDay(calendar4, DrawableUtils.getThreeDots(this)));

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -2);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 2);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setEvents(events);

        calendarView.setDisabledDays(getDisabledDays());
         calendarView.setOnDayClickListener(new OnDayClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
             @Override
             public void onDayClick(EventDay eventDay) {
                 int flag = 0;
                 for (int i : dates
                 ) {
                     if (eventDay.getCalendar().getTime().getDate() == i) {
                         Toast.makeText(CalendarActivity.this, "date already selected", Toast.LENGTH_LONG).show();
                         flag = 1;
                     }
                 }
                 if (flag != 1) {
                     Toast.makeText(getApplicationContext(),
                             eventDay.getCalendar().getTime().getDate() + " fff"
                                     + eventDay.isEnabled(),
                             Toast.LENGTH_SHORT).show();
                     myDb.insertDate(eventDay.getCalendar().getTime().getDate(), User.getEmail(), (eventDay.getCalendar().getTime().getMonth()));

//                     if (eventDay.getCalendar().getTime().getDay()==7) {
//                         int randomNum = ThreadLocalRandom.current().nextInt(1, 8 + 1);
//                         time.setText("time "+randomNum);
//                     }

                 }
             }
         });



        Button setDateButton = (Button) findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener(v -> {
            try {
                Calendar randomCalendar = getRandomCalendar();
                String text = randomCalendar.getTime().toString();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                calendarView.setDate(randomCalendar);
            } catch (OutOfDateRangeException exception) {
                exception.printStackTrace();

                Toast.makeText(getApplicationContext(),
                        "Date is out of range",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}
