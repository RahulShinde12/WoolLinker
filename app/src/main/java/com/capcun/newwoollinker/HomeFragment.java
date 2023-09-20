package com.capcun.newwoollinker;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    StageAdapter stageAdapter;
    List<StageModelClass> data_list = new ArrayList<>();
    RecyclerView recycler;
    TextView name1;

    LinearLayoutManager layoutManager;
    final String sharedPreferencesFileTitle = "wool_linker";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        name1 = view.findViewById(R.id.name);
        name1.setText(name);


        recycler = view.findViewById(R.id.recycler_for_stages);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        data_list.clear();
        data_list.add(new StageModelClass("Shearing", "Removing wool", "Shearing is the initial step in the process of extracting wool from sheep or other wool-bearing animals. It is a crucial and fundamental process in wool processing, and it involves the careful and humane removal of the fleece from the animal's body. Here's an overview of the shearing process", "STAGE 01 - Begins"));
        data_list.add(new StageModelClass("Scouring", "Washing the sheared hair", "Scouring is a crucial step in the wool processing industry that involves thoroughly cleaning the raw wool fibers that have been sheared from sheep or other wool-bearing animals. This process removes impurities, grease, dirt, and other contaminants from the wool, leaving it clean and ready for further processing", "STAGE 02"));
        data_list.add(new StageModelClass("Sorting", "separating the fleece", "separating the fleece of a sheep into sections according to the quality of woollen fibres ", "STAGE 03"));
        data_list.add(new StageModelClass("Combing", "Removal of short fibres ", "removes the short fibres and arranges the fibre in a flat bundle", "STAGE 04"));
        data_list.add(new StageModelClass("Carding", "brushing the wool fibres to organize them", "Carding is a vital process in the wool processing industry that involves brushing and aligning wool fibers to prepare them for spinning into yarn or further processing.", "STAGE 05"));
        data_list.add(new StageModelClass("Dyeing", "Adsorption of Dye Molecules on the Fibers: ", "Dyeing is a crucial process in the textile industry that involves imparting color to fibers, fabrics, or yarns by applying dye molecules to the material's surface. ", "STAGE 06"));
        data_list.add(new StageModelClass("Spinning", "Rolling Wool Threads Around", "Spinning is a fundamental process in the textile industry that transforms cleaned and carded wool fibers into yarn or thread.", "STAGE 07"));
        data_list.add(new StageModelClass("Quality Assurance", "Quality Assurance(QA) is a crucial stage in wool processing", "It ensures that the final wool products meet industry standards for cleanliness, purity, and overall quality. ", "STAGE 08 - Final "));

        stageAdapter = new StageAdapter(data_list, getContext());
        recycler.setAdapter(stageAdapter);

        return view;
    }
}