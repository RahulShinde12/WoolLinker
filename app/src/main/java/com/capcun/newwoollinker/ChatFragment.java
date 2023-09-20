package com.capcun.newwoollinker;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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

    ImageView send_msg;
    EditText msg;
    final String sharedPreferencesFileTitle = "wool_linker";

    List<chats_datalead>data_list = new ArrayList<>();
    RecyclerView recycler;
    LinearLayoutManager layoutManager;
    messagesAdpter adpter;

    private final Handler messageHandler = new Handler(Looper.getMainLooper());
    private static final long MESSAGE_FETCH_INTERVAL = 2000; // 2 seconds

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(sharedPreferencesFileTitle, MODE_PRIVATE);

        String s = sharedPreferences.getString("number","");

        recycler = view.findViewById(R.id.msgadpter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        startFetchingMessages(s);

        send_msg = view.findViewById(R.id.send);
        msg = view.findViewById(R.id.textmsg);

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg.getText().toString().isEmpty())
                    msg.setError("Enter msg first");
                String m = msg.getText().toString();

                Call<String> call = ApiControlller
                        .getInstance()
                        .getAddNewUserApiSet()
                        .sendMsg(s,m);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String s = response.body();

                        if(s.equals("success"))
                        {
                            msg.setText("");
                            Toast.makeText(getContext(), "Send..!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }


    private void startFetchingMessages(String s) {
        // Define a Runnable task to fetch messages
        Runnable fetchMessagesTask = new Runnable() {
            @Override
            public void run() {
                // Fetch messages
                fetchMessages(s);

                // Schedule the task to run again after MESSAGE_FETCH_INTERVAL
                messageHandler.postDelayed(this, MESSAGE_FETCH_INTERVAL);
            }
        };

        // Start the initial fetch
        messageHandler.post(fetchMessagesTask);
    }

    // ...

    @Override
    public void onDestroyView() {
        // Remove any pending fetch messages callbacks when the fragment is destroyed
        messageHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    private void fetchMessages(String s) {

        Call<List<msgModelclass>> call2 = ApiControlller
                .getInstance()
                .getAddNewUserApiSet()
                .fetchChat(s);

        call2.enqueue(new Callback<List<msgModelclass>>() {
            @Override
            public void onResponse(Call<List<msgModelclass>> call, Response<List<msgModelclass>> response) {
                List<msgModelclass> modelclass = response.body();
                data_list.clear();

                if (modelclass!=null)
                {
                    for(msgModelclass data : modelclass)
                    {
                        data_list.add(new chats_datalead(data.getNumber(),data.getMessage(),data.getDate()));
                    }
                    adpter = new messagesAdpter(data_list,getContext());
                    recycler.setAdapter(adpter);
                }
            }

            @Override
            public void onFailure(Call<List<msgModelclass>> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
}