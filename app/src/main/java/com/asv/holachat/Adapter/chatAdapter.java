package com.asv.holachat.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asv.holachat.Models.messageModel;
import com.asv.holachat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class chatAdapter extends  RecyclerView.Adapter {

    ArrayList<messageModel> messageModels;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public chatAdapter(ArrayList<messageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public chatAdapter(ArrayList<messageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
            //return null;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new RecieverViewHolder(view);
            //return null;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        messageModel messageModel = messageModels.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                        String recieverRoom = recId + FirebaseAuth.getInstance().getUid();
                        database.getReference().child("chats").child(senderRoom).child(messageModel.getMessageId()).setValue(null);
                        database.getReference().child("chats").child(recieverRoom).child("messages").child(messageModel.getMessageId()).setValue(null);
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            return false;
            }
        });

        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMessage.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
            }
            else {
                simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            }
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            String strDate = simpleDateFormat.format(date);
            ((SenderViewHolder) holder).senderTime.setText(strDate.toString());
            //((SenderViewHolder) holder).senderTime.setText(messageModel.getTimestamp().toString());
        }
        else {
            ((RecieverViewHolder) holder).recevierMessage.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
            }
            else {
                simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            }
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            String strDate = simpleDateFormat.format(date);
            ((RecieverViewHolder) holder).recevierTime.setText(strDate.toString());
            //((RecieverViewHolder) holder).recevierTime.setText(messageModel.getTimestamp().toString());
        }
    }

        @Override
        public int getItemCount() {
            return messageModels.size();
        }

        class RecieverViewHolder extends RecyclerView.ViewHolder {
            TextView recevierMessage, recevierTime;

            public RecieverViewHolder(@NonNull View itemView) {
                super(itemView);
                recevierMessage = itemView.findViewById(R.id.receiverText);
                recevierTime = itemView.findViewById(R.id.receiverTime);
            }
        }

        class SenderViewHolder extends RecyclerView.ViewHolder {
            TextView senderMessage, senderTime;

            public SenderViewHolder(@NonNull View itemView) {
                super(itemView);
                senderMessage = itemView.findViewById(R.id.senderText);
                senderTime = itemView.findViewById(R.id.senderTime);
            }
        }
    }
