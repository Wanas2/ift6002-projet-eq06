package ca.ulaval.glo4002.game.domain.dinosaur;

public enum Species {
    Ankylosaurus(FoodConsumption.HERBIVOROUS,"Ankylosaurus"),
    Spinosaurus(FoodConsumption.CARNIVOROUS,"Spinosaurus");

    private final FoodConsumption consumptionType;
    private final String name;

    Species(FoodConsumption consumptionType, String name) {
        this.consumptionType = consumptionType;
        this.name = name;
    }
}
