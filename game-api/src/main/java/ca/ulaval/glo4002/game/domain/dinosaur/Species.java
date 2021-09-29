package ca.ulaval.glo4002.game.domain.dinosaur;

public enum Species {
    Ankylosaurus(FoodConsumption.HERBIVOROUS,"Ankylosaurus"),
    Brachiosaurus(FoodConsumption.HERBIVOROUS,"Brachiosaurus"),
    Diplodocus(FoodConsumption.HERBIVOROUS,"Diplodocus"),
    Stegosaurus(FoodConsumption.HERBIVOROUS,"Stegosaurus"),
    Triceratops(FoodConsumption.HERBIVOROUS,"Triceratops"),
    Allosaurus(FoodConsumption.CARNIVOROUS,"Allosaurus"),
    Megalosaurus(FoodConsumption.CARNIVOROUS,"Megalosaurus"),
    Spinosaurus(FoodConsumption.CARNIVOROUS,"Spinosaurus"),
    TyrannosaurusRex(FoodConsumption.CARNIVOROUS,"Tyrannosaurus Rex"),
    Velociraptor(FoodConsumption.CARNIVOROUS,"Velociraptor");

    private final FoodConsumption consumptionType;
    private final String name;

    Species(FoodConsumption consumptionType, String name) {
        this.consumptionType = consumptionType;
        this.name = name;
    }
}
