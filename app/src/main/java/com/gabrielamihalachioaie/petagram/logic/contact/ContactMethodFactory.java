package com.gabrielamihalachioaie.petagram.logic.contact;

public final class ContactMethodFactory {
    public static ContactMethod buildDefaultMethod() {
        ContactMethod contactMethod = new JavaMailContactMethod();

        return contactMethod;
    }
}
