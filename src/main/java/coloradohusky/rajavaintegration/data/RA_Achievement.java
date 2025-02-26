package coloradohusky.rajavaintegration.data;

import com.google.gson.annotations.SerializedName;

public class RA_Achievement {
    /** Title of the achievement */
    @SerializedName("title")
    private String title;

    /** Description of the achievement */
    @SerializedName("description")
    private String description;

    /** Points of the achievement */
    @SerializedName("points")
    private int points;

    /** Type of the achievement */
    @SerializedName("type")
    private String type;

    /** Category of the achievement */
    @SerializedName("category")
    private int category;

    /** ID of the achievement */
    @SerializedName("id")
    private int id;

    /** Badge ID of the achievement */
    @SerializedName("badge")
    private String badge;

    /** Game ID of the set the achievement belongs to */
    @SerializedName("set")
    private int set;

    // Constructor
    public RA_Achievement(String title, String description, int points, String type, int category, int id, String badge, int set) {
        this.title = title;
        this.description = description;
        this.points = points;
        this.type = type;
        this.category = category;
        this.id = id;
        this.badge = badge;
        this.set = set;
    }

    // Default constructor for Gson
    public RA_Achievement() {}

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getCategory() { return category; }
    public void setCategory(int category) { this.category = category; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }

    public int getSet() { return set; }
    public void setSet(int set) { this.set = set; }
}

