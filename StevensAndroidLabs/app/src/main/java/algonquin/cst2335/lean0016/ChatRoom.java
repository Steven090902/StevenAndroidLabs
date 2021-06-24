package algonquin.cst2335.lean0016;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    MyChatAdapter adapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    RecyclerView chatList = findViewById(R.id.myrecycler);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        MyOpenHelper opener = new MyOpenHelper();

        EditText typedMessage = findViewById(R.id.editMessage);
        Button send = findViewById(R.id.sendbutton);
        Button rec  = findViewById(R.id.receiveButton);

        

        chatList.setAdapter(adapter = new MyChatAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        chatList.setLayoutManager(layoutManager);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());


        send.setOnClickListener( click ->{
            String currentDataandTime = sdf.format(new Date());
            ChatMessage thisMessage = new ChatMessage( typedMessage.getText().toString() , 1, currentDataandTime );
            messages.add(thisMessage);
            //clear the edittext:
            typedMessage.setText("");
            //refresh the list:
            adapter.notifyItemInserted( messages.size() - 1 ); //just insert the new row:

        });

        rec.setOnClickListener( click ->{
            String currentDataandTime = sdf.format(new Date());
            ChatMessage thisMessage = new ChatMessage( typedMessage.getText().toString() , 2, currentDataandTime );
            messages.add(thisMessage);
            //clear the edittext:
            typedMessage.setText("");
            //refresh the list:
            adapter.notifyItemInserted( messages.size() - 1 ); //just insert the new row:

        });
    }
        private class MyRowViews extends RecyclerView.ViewHolder {

            TextView messageText;
            TextView timetext;
            int position = -1;

            public MyRowViews(View itemView) {
                super(itemView);


                itemView.setOnClickListener( click -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Do you want to delete the message: " + messageText.getText())
                            .setTitle("Question:")
                            .setNegativeButton("No", (dialog, cl) -> { })
                            .setPositiveButton("Yes", (dialog, cl) -> {

                                position = getAbsoluteAdapterPosition();

                                ChatMessage removedMessage = messages.get(position);
                                messages.remove(position);
                                adapter.notifyItemRemoved(position);

                                Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo", clk -> {
                                            messages.add(position, removedMessage);
                                            adapter.notifyItemInserted(position);

                                        })
                                        .show();
                            })
                            .create().show();
                });

                messageText = itemView.findViewById(R.id.message);
                timetext = itemView.findViewById(R.id.time);
            }

            public int getAbsoluteAdapterPosition() {
                return 0;
            }

            public void setPosition(int p){
                position = p;
            }

        }
        private class MyChatAdapter extends RecyclerView.Adapter {
            @Override
            public int getItemViewType(int position) {
                ChatMessage thisRow = messages.get(position);
                return thisRow.getSendOrReceive();
            }

            @Override
            public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                int layoutID;
                if(viewType == 1)
                    layoutID = R.layout.sent_message;
                else
                    layoutID = R.layout.receive_message;

                View loadedRow = inflater.inflate(layoutID, parent, false);

                return new MyRowViews(loadedRow);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyRowViews RowLayout = (MyRowViews)holder;
                RowLayout.messageText.setText(messages.get(position).getMessage());
                RowLayout.timetext.setText(messages.get(position).getTimeSent());
                RowLayout.setPosition(position);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        }


        private class ChatMessage {
           public String messages;
            int sendOrReceive;
            String timeSent;


            public ChatMessage(String messages, int sendOrReceive, String timeSent) {
                this.messages = messages;
                this.sendOrReceive = sendOrReceive;
                this.timeSent = timeSent;
            }

            public String getMessage() {
                return messages;
            }

            public int getSendOrReceive() {
                return sendOrReceive;
            }

            public String getTimeSent() {
                return timeSent;
            }
        }

    }
