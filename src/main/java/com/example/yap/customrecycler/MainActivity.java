package com.example.yap.customrecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    RecyclerAdapter recyclerAdapter;
    ItemTouchHelper itemTouchHelper;

    ArrayList<People> people;
    ArrayList<Integer> positionList;
    ArrayList<String> peopleFirstNames;

    int parentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        people = getList();
        positionList = getLastPositions();

        recyclerAdapter = new RecyclerAdapter(people);
        LinearLayoutManager ll = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(recyclerAdapter);

        HeaderDecoration headerDecoration = new HeaderDecoration(positionList,0,true, new HeaderDecoration.SectionCallback() {
            @Override
            public boolean inSection(int position) {

                if (position == 0)
                    return true;

                for (int j=0; j<positionList.size(); j++)
                {
                    if (position == positionList.get(j))
                    {
                        Timber.d("inSection position = " + position);

                        return true;
                    }
                }

                return false;
            }

            @Override
            public String getSectionHeader(int position) {

                return peopleFirstNames.get(position);
            }
        });

        recyclerView.addItemDecoration(headerDecoration);
    }

   /* private ArrayList<People> getList()
    {
        ArrayList<People> list = new ArrayList<>();

        for (int i=0; i<10; i++)
        {
            People people = new People();
            people.setFirstName("성 " + i);

            ArrayList<People.LastNames> lastNames = new ArrayList<>();

            for (int j=0; j<5; j++)
            {
                People.LastNames lastName = new People.LastNames();

                Timber.d("people.getFirstName() = " + people.getFirstName());

                lastName.setLastName(people.getFirstName() + " / 이름 " + j);
                lastName.setAge("나이 " + j + "세");
                lastNames.add(lastName);
            }

            people.setLastNames(lastNames);

            list.add(people);
        }

        return list;
    }*/

    private ArrayList<People> getList()
    {
        ArrayList<People> list = new ArrayList<>();

        for (int i=0; i<5; i++)
        {
            People people = new People();
            people.setFirstName("성 : " + i);

            if (i==0)
                peopleFirstNames = new ArrayList<>();

            peopleFirstNames.add(people.getFirstName());

            ArrayList<People.LastNames> names = new ArrayList<>();

            for (int j = 0; j < 7; j++) {
                People.LastNames name = new People.LastNames();
                name.setLastName("이름 : " + i);
                name.setAge("나이 : " + i);

                peopleFirstNames.add(people.getFirstName());

                names.add(name);
            }

            people.setLastNames(names);
            list.add(people);
        }

        return list;
    }

    private ArrayList<Integer> getLastPositions()
    {
        ArrayList<Integer> list = new ArrayList<>();
        int position = 0;

        for (int i=0; i<people.size(); i++)
        {
            if (i==0)
                position = 0;
            else
                position += people.get(i).getLastNames().size()+1;

            Timber.d("position = " + position);

            list.add(position);
        }

        return list;
    }
}
