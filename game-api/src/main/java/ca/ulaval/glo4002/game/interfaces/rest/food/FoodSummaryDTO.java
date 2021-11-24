package ca.ulaval.glo4002.game.interfaces.rest.food;

public class FoodSummaryDTO {

    public final FoodDTO fresh;
    public final FoodDTO expired;
    public final FoodDTO consumed;

    public FoodSummaryDTO(FoodDTO fresh, FoodDTO expired, FoodDTO consumed) {
        this.fresh = fresh;
        this.expired = expired;
        this.consumed = consumed;
    }
}
