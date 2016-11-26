package com.sohba_travel.sohba;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SportCardsUtils {

    public static Collection<SportCardModel> generateSportCards() {
        List<SportCardModel> sportCardModels = new ArrayList<>(5);

        {
            sportCardModels.add(SportCardModel
                    .newBuilder()
                    .withSportTitle("Fayoum Trip")
                    .withSportSubtitle("Rayan Village")
                    .withSportRound("Featured")
                    .withImageResId(R.drawable.trip_fayoum)
                    .withTime("40")
                    .withDayPart("LE")
                    .withBackgroundColorResId(R.color.dark_orchid)
                    .build());

        }

        {
            sportCardModels.add(SportCardModel
                    .newBuilder()
                    .withSportTitle("Alexandria Trip")
                    .withSportSubtitle("Air & Beaches")
                    .withSportRound("Featured")
                    .withImageResId(R.drawable.trip_alex)
                    .withTime("55")
                    .withDayPart("LE")
                    .withBackgroundColorResId(R.color.mantis)
                    .build());

        }

        {
            sportCardModels.add(SportCardModel
                    .newBuilder()
                    .withSportTitle("Aswan")
                    .withSportSubtitle("High Dam & lakes")
                    .withSportRound("Featured")
                    .withImageResId(R.drawable.trip_aswan)
                    .withTime("65")
                    .withDayPart("LE")
                    .withBackgroundColorResId(R.color.usc_gold)
                    .build());

        }

        {
            sportCardModels.add(SportCardModel
                    .newBuilder()
                    .withSportTitle("Sinai")
                    .withSportSubtitle("Resorts and Mountains")
                    .withSportRound("Featured")
                    .withImageResId(R.drawable.trip_sinai)
                    .withTime("75")
                    .withDayPart("LE")
                    .withBackgroundColorResId(R.color.portland_orange)
                    .build());

        }




        return sportCardModels;
    }
}
