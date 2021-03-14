package com.gabrielamihalachioaie.petagram.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.contact.ContactMethod;
import com.gabrielamihalachioaie.petagram.logic.contact.ContactMethodFactory;

public class ContactActivity extends AppCompatActivity {
    private static final String APP_EMAIL = "petagram.official@gmail.com";

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText messageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.contactToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEdit = findViewById(R.id.nameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        messageEdit = findViewById(R.id.messageEdit);

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener((v) -> sendMessage());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sendMessage() {
        ContactMethod contactMethod = ContactMethodFactory.buildDefaultMethod();

        String name = nameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String message = messageEdit.getText().toString();

        String finalMessage = String.format("%s%n%s%n%n%s", name, email, message);
        String subject = String.format("%s %s", name, getString(R.string.contact_mail_subject));
        contactMethod.sendMessage(email, APP_EMAIL, subject, finalMessage);

        String replySubject = getString(R.string.contact_mail_reply_subject);
        String replyMessage = getString(R.string.contact_mail_reply_message);
        contactMethod.sendMessage(APP_EMAIL, email, replySubject, replyMessage);

        Toast.makeText(this, R.string.contact_sending, Toast.LENGTH_SHORT).show();
    }
}