package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;

public enum Species {
    Ankylosaurus(FoodConsumption.HERBIVOROUS, "Ankylosaurus", 1.0f),
    Brachiosaurus(FoodConsumption.HERBIVOROUS, "Brachiosaurus", 1.0f),
    Diplodocus(FoodConsumption.HERBIVOROUS, "Diplodocus", 1.0f),
    Stegosaurus(FoodConsumption.HERBIVOROUS, "Stegosaurus", 1.0f),
    Triceratops(FoodConsumption.HERBIVOROUS, "Triceratops", 1.0f),
    Allosaurus(FoodConsumption.CARNIVOROUS, "Allosaurus", 1.5f),
    Megalosaurus(FoodConsumption.CARNIVOROUS, "Megalosaurus", 1.5f),
    Spinosaurus(FoodConsumption.CARNIVOROUS, "Spinosaurus", 1.5f),
    TyrannosaurusRex(FoodConsumption.CARNIVOROUS, "Tyrannosaurus Rex", 1.5f),
    Velociraptor(FoodConsumption.CARNIVOROUS, "Velociraptor", 1.5f),
    Eoraptor(FoodConsumption.OMNIVOROUS, "Eoraptor", 1.5f),
    Gigantoraptor(FoodConsumption.OMNIVOROUS, "Gigantoraptor", 1.5f),
    Heterodontosaurus(FoodConsumption.OMNIVOROUS, "Heterodontosaurus", 1.5f),
    Ornithomimus(FoodConsumption.OMNIVOROUS, "Ornithomimus", 1.5f),
    Struthiomimus(FoodConsumption.OMNIVOROUS, "Struthiomimus", 1.5f);

    private final FoodConsumption consumptionType;
    private final String name;
    private final float strength;

    Species(FoodConsumption consumptionType, String name, float strength) {
        this.consumptionType = consumptionType;
        this.name = name;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public FoodConsumption getConsumptionType() {
        return consumptionType;
    }

    public float getStrength() {
        return strength;
    }
}
