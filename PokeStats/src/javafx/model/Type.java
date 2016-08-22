package javafx.model;

interface Effectiveness {
	public Type[] superEffective();

	public Type[] notEffective();

	public Type[] immune();
}

public enum Type implements Effectiveness {
	NORMAL("Normal") {
		public Type[] superEffective() {
			return new Type[] { Type.FIGHTING, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] {};
		}

		public Type[] immune() {
			return new Type[] { Type.GHOST };
		}
	},
	FIGHTING("Fighting") {
		public Type[] superEffective() {
			return new Type[] { Type.FLYING, Type.PSYCHIC, Type.FAIRY, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.DARK, Type.ROCK };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	FLYING("Flying") {
		public Type[] superEffective() {
			return new Type[] { Type.ELECTRIC, Type.ICE, Type.ROCK, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.FIGHTING, Type.GRASS };
		}

		public Type[] immune() {
			return new Type[] { Type.GROUND };
		}
	},
	POISON("Poison") {
		public Type[] superEffective() {
			return new Type[] { Type.GROUND, Type.PSYCHIC, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.FIGHTING, Type.GRASS, Type.POISON, Type.FAIRY };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	GROUND("Ground") {
		public Type[] superEffective() {
			return new Type[] { Type.GRASS, Type.ICE, Type.WATER, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.POISON, Type.ROCK };
		}

		public Type[] immune() {
			return new Type[] { Type.ELECTRIC };
		}
	},
	ROCK("Rock") {
		public Type[] superEffective() {
			return new Type[] { Type.FIGHTING, Type.GRASS, Type.GROUND, Type.STEEL, Type.WATER, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.FIRE, Type.FLYING, Type.NORMAL, Type.POISON };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	BUG("Bug") {
		public Type[] superEffective() {
			return new Type[] { Type.FIRE, Type.FLYING, Type.ROCK, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.FIGHTING, Type.GRASS, Type.GROUND };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	GHOST("Ghost") {
		public Type[] superEffective() {
			return new Type[] { Type.DARK, Type.GHOST, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.POISON };
		}

		public Type[] immune() {
			return new Type[] { Type.FIGHTING, Type.NORMAL };
		}
	},
	STEEL("Steel") {
		public Type[] superEffective() {
			return new Type[] { Type.FIGHTING, Type.FIRE, Type.FIGHTING };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.DRAGON, Type.FLYING, Type.GRASS, Type.ICE, Type.PSYCHIC, Type.ROCK,
					Type.STEEL, Type.FAIRY, Type.NUCLEAR };
		}

		public Type[] immune() {
			return new Type[] { Type.POISON };
		}
	},
	FIRE("Fire") {
		public Type[] superEffective() {
			return new Type[] { Type.GROUND, Type.ROCK, Type.WATER, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.FIRE, Type.GRASS, Type.ICE, Type.STEEL, Type.FAIRY };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	WATER("Water") {
		public Type[] superEffective() {
			return new Type[] { Type.ELECTRIC, Type.GRASS, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.FIRE, Type.ICE, Type.STEEL, Type.WATER };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	GRASS("Grass") {
		public Type[] superEffective() {
			return new Type[] { Type.BUG, Type.FIRE, Type.FLYING, Type.ICE, Type.POISON, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.ELECTRIC, Type.GRASS, Type.GROUND, Type.WATER };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	ELECTRIC("Electric") {
		public Type[] superEffective() {
			return new Type[] { Type.GROUND, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.ELECTRIC, Type.FLYING, Type.STEEL };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	PSYCHIC("Psychic") {
		public Type[] superEffective() {
			return new Type[] { Type.BUG, Type.DARK, Type.GHOST, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.FIGHTING, Type.PSYCHIC };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	ICE("Ice") {
		public Type[] superEffective() {
			return new Type[] { Type.FIGHTING, Type.FIRE, Type.ROCK, Type.STEEL, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.ICE };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	DRAGON("Dragon") {
		public Type[] superEffective() {
			return new Type[] { Type.DRAGON, Type.ICE, Type.FAIRY, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.ELECTRIC, Type.FIRE, Type.GRASS, Type.WATER };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	},
	DARK("Dark") {
		public Type[] superEffective() {
			return new Type[] { Type.BUG, Type.FIGHTING, Type.FAIRY, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.DARK, Type.GHOST };
		}

		public Type[] immune() {
			return new Type[] { Type.PSYCHIC };
		}
	},
	FAIRY("Fairy") {
		public Type[] superEffective() {
			return new Type[] { Type.POISON, Type.STEEL, Type.NUCLEAR };
		}

		public Type[] notEffective() {
			return new Type[] { Type.BUG, Type.DARK, Type.FIGHTING };
		}

		public Type[] immune() {
			return new Type[] { Type.DRAGON };
		}
	},
	NUCLEAR("Nuclear") {
		public Type[] superEffective() {
			return new Type[] { Type.NORMAL, Type.FIRE, Type.FIGHTING, Type.WATER, Type.FLYING, Type.GRASS,
					Type.ELECTRIC, Type.GROUND, Type.PSYCHIC, Type.ROCK, Type.ICE, Type.BUG, Type.DRAGON, Type.GHOST,
					Type.DARK, Type.STEEL, Type.FAIRY };
		}

		public Type[] notEffective() {
			return new Type[] { Type.NUCLEAR };
		}

		public Type[] immune() {
			return new Type[] {};
		}
	};

	private final String name;

	private Type(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static Type getType(String s) {
		switch(s) {
		case "Normal":
			return Type.NORMAL;
		case "Fighting":
			return Type.FIGHTING;
		case "Flying":
			return Type.FLYING;
		case "Poison":
			return Type.POISON;
		case "Ground":
			return Type.GROUND;
		case "Rock":
			return Type.ROCK;
		case "Bug":
			return Type.BUG;
		case "Ghost":
			return Type.GHOST;
		case "Steel":
			return Type.STEEL;
		case "Fire":
			return Type.FIRE;
		case "Water":
			return Type.WATER;
		case "Grass":
			return Type.GRASS;
		case "Electric":
			return Type.ELECTRIC;
		case "Psychic":
			return Type.PSYCHIC;
		case "Ice":
			return Type.ICE;
		case "Dragon":
			return Type.DRAGON;
		case "Dark":
			return Type.DARK;
		case "Fairy":
			return Type.FAIRY;
		case "Nuclear":
			return Type.NUCLEAR;
		}
		return null;
	}
}