class Recipe {

    id;
    name;
    instructions;
    ingredients;

    constructor(id, name, instructions, ingredients) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }
}

class Instruction {

    instruction;

    constructor(instruction) {
        this.instruction = instruction;
    }
}

class Ingredient {

    name;
    amount;
    unit;

    constructor(name, amount, unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}

exports.Recipe = Recipe;
exports.Instruction = Instruction;
exports.Ingredient = Ingredient;