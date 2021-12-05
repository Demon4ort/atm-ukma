package Storage;


import example.entity.Model;

public class CardInfo {
    private static Model.Card card;


    public static Model.Card getCard() {
        return card;
    }

    public static void setCard(Model.Card card) {
        CardInfo.card = card;
    }
}

