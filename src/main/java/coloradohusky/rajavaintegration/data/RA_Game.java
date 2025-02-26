package coloradohusky.rajavaintegration.data;

import com.google.gson.annotations.SerializedName;

public class RA_Game {
    /** ID of the game */
    @SerializedName("id")
    private int id;

    /** Name of the game */
    @SerializedName("name")
    private String name;

    /** IDs of the sets associated with this game */
    @SerializedName("sets")
    private int[] sets;

    // Constructor
    public RA_Game(int id, String name, int[] sets) {
        this.id = id;
        this.name = name;
        this.sets = sets;
    }

    // Default constructor for Gson
    public RA_Game() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int[] getSets() { return sets; }
    public void setSets(int[] sets) { this.sets = sets; }
}

