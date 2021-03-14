package com.gabrielamihalachioaie.petagram.logic.contact;

public interface ContactMethod {
    void sendMessage(String from, String to, String subject, String message);
}
