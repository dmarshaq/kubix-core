package graphics;

public enum Anim {
    IDLE (0, "idle"),
    WALK (1, "walk"),
    RUN (2, "run"),
    JUMP (3, "jump"),
    FALL (4, "fall"),
    ROLL (5, "roll");


    private int id;
    private String name;

    Anim(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static int getID(Anim anim) {
        return anim.id;
    }

    public static Anim getAnim(String name) {
        for (Anim anim : Anim.values()) {
            if (anim.name == name) return anim;
        }
        return null;
    }

}
