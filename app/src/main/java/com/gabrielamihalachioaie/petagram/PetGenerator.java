package com.gabrielamihalachioaie.petagram;

import java.util.ArrayList;

public class PetGenerator {
    private static final int NAME_IDS = 100;
    private static final int MAX_INITIAL_BONES = 25;

    private static PetGenerator instance;

    private PetGenerator() {

    }

    public ArrayList<Mascota> generatePets(int quantity) {
        ArrayList<Mascota> pets = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            int profilePicture;

            if (Math.random() > 0.5) {
                profilePicture = R.drawable.big_doge;
            } else {
                profilePicture = R.drawable.small_doge;
            }

            Mascota pet = new Mascota(profilePicture, generateName(), generateBones());
            pets.add(pet);
        }

        return pets;
    }

    private String generateName() {
        return String.format("Doge %d", (int) (Math.random() * NAME_IDS));
    }

    private int generateBones() {
        return (int) (Math.random() * MAX_INITIAL_BONES);
    }

    public static PetGenerator getInstance() {
        if (instance == null) {
            instance = new PetGenerator();
        }

        return instance;
    }
}
